package com.nttdata.bc.localcomercial.services;

import com.nttdata.bc.localcomercial.controllers.dto.LocalComercialDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LocalComercialService {

    Flux<LocalComercialDto.Response> getAllLocalComercial();

    Mono<LocalComercialDto.Response> getLocalComercialById(String id);

    Mono<LocalComercialDto.Response> createLocalComercial(LocalComercialDto.Request localComercial);

    Mono<LocalComercialDto.Response> updateLocalComercial(String id, LocalComercialDto.Request request);

    Mono<Void> deleteLocalComercial(String id);
}
