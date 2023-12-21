package com.nttdata.bc.compras.services;

import com.nttdata.bc.compras.controllers.dto.CompraDto;
import com.nttdata.bc.compras.repositories.CompraRepository;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
public class CompraServices {

    private final CompraRepository compraRepository;

    CompraServices(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    public Flux<CompraDto.Responce> getAllCompra() {
        return compraRepository.findAll()
                .switchIfEmpty(Flux.empty())
                .map(CompraDto::convertToResponce);
    }

    public Mono<CompraDto.Responce> getCompraById(String id) {
        return compraRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("No se encontro la compra")))
                .map(CompraDto::convertToResponce);
    }

    public Mono<CompraDto.Responce> createCompra(CompraDto.Request localComercial) {
        return compraRepository.save(CompraDto.convertToEntity(localComercial))
                .switchIfEmpty(Mono.error(new Exception("No se pudo crear la compra")))
                .map(CompraDto::convertToResponce);
    }

    public Mono<CompraDto.Responce> updateCompra(String id, CompraDto.Request request) {
        return compraRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("No se encontro la compra")))
                .flatMap(compra -> {
                    compra.setListaProductos(CompraDto.convertToEntity(request).getListaProductos());
                    compra.setMontoTotal(request.getMontoTotal());
                    return compraRepository.save(compra)
                            .switchIfEmpty(Mono.error(new Exception("No se pudo actualizar la compra")))
                            .map(CompraDto::convertToResponce);
                });
    }

    public Mono<Void> deleteCompra(String id) {
        return compraRepository.deleteById(id);
    }

}
