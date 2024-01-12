package com.nttdata.bc.compras.services;

import com.nttdata.bc.compras.controllers.dto.CompraDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CompraService {

    Flux<CompraDto.Response> getAllCompra();

    Mono<CompraDto.Response> getCompraById(String id);

    Mono<CompraDto.Response> createCompra(CompraDto.Request request);

    Mono<CompraDto.Response> updateCompra(String id, CompraDto.Request request);

    Mono<Void> deleteCompra(String id);
}
