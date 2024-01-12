package com.nttdata.bc.localcomercial.services.impl;

import com.nttdata.bc.localcomercial.controllers.dto.LocalComercialDto;
import com.nttdata.bc.localcomercial.repositories.LocalComercialRepository;
import com.nttdata.bc.localcomercial.services.LocalComercialService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LocalComercialServiceImpl implements LocalComercialService {

    private final LocalComercialRepository localComercialRepository;

    LocalComercialServiceImpl(LocalComercialRepository localComercialRepository) {
        this.localComercialRepository = localComercialRepository;
    }

    @Override
    public Flux<LocalComercialDto.Response> getAllLocalComercial() {
        return localComercialRepository.findAll()
                .switchIfEmpty(Flux.empty())
                .map(LocalComercialDto::convertToResponse);
    }

    @Override
    public Mono<LocalComercialDto.Response> getLocalComercialById(String id) {
        return localComercialRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("No se encontro el local comercial")))
                .map(LocalComercialDto::convertToResponse);
    }

    @Override
    public Mono<LocalComercialDto.Response> createLocalComercial(LocalComercialDto.Request localComercial) {
        return localComercialRepository.save(LocalComercialDto.convertToEntity(localComercial))
                .switchIfEmpty(Mono.error(new Exception("No se pudo crear el local comercial")))
                .map(LocalComercialDto::convertToResponse);
    }

    @Override
    public Mono<LocalComercialDto.Response> updateLocalComercial(String id, LocalComercialDto.Request request) {
        return localComercialRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("No se encontro el local comercial")))
                .flatMap(localComercial -> {
                    localComercial.setNombre(request.getNombre());
                    localComercial.setDescripcion(request.getDescripcion());
                    return localComercialRepository.save(localComercial)
                            .switchIfEmpty(Mono.error(new Exception("No se pudo actualizar el local comercial")))
                            .map(LocalComercialDto::convertToResponse);
                });
    }

    @Override
    public Mono<Void> deleteLocalComercial(String id) {
        return localComercialRepository.deleteById(id);
    }

}
