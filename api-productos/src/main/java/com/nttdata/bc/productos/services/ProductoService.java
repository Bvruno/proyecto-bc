package com.nttdata.bc.productos.services;

import com.nttdata.bc.productos.controllers.dto.ProductoDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoService {

    Flux<ProductoDto.Response> getAllProductos();

    Mono<ProductoDto.Response> getProductoById(String id);

    Mono<ProductoDto.Response> createProducto(ProductoDto.Request producto);

    Mono<ProductoDto.Response> updateProducto(String id, ProductoDto.Request producto);

    Mono<Void> deleteProducto(String id);

    Flux<ProductoDto.Response> getAllProductoByIdLocalComercial(String id);
}
