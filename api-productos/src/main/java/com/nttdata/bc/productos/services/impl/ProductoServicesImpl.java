package com.nttdata.bc.productos.services.impl;

import com.nttdata.bc.productos.controllers.dto.ProductoDto;
import com.nttdata.bc.productos.repositories.ProductoRepository;
import com.nttdata.bc.productos.services.ProductoService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoServicesImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    ProductoServicesImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public Flux<ProductoDto.Response> getAllProductos() {
        return productoRepository.findAll()
                .switchIfEmpty(Flux.empty())
                .map(ProductoDto::convertToResponce);
    }

    @Override
    public Mono<ProductoDto.Response> getProductoById(String id) {
        return productoRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("No se encontro el producto")))
                .map(ProductoDto::convertToResponce);
    }

    @Override
    public Mono<ProductoDto.Response> createProducto(ProductoDto.Request producto) {
        return productoRepository.save(ProductoDto.convertToEntity(producto))
                .switchIfEmpty(Mono.error(new Exception("No se pudo crear el producto")))
                .map(ProductoDto::convertToResponce);
    }

    @Override
    public Mono<ProductoDto.Response> updateProducto(String id, ProductoDto.Request producto) {
        return productoRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("No se encontro el producto")))
                .flatMap(existingProducto -> {
                    existingProducto.setNombre(producto.getNombre());
                    existingProducto.setPrecio(producto.getPrecio());
                    existingProducto.setUnidades(producto.getUnidades());
                    return productoRepository.save(existingProducto)
                            .switchIfEmpty(Mono.error(new Exception("No se pudo actualizar el producto")))
                            .map(ProductoDto::convertToResponce);
                });
    }

    @Override
    public Mono<Void> deleteProducto(String id) {
        return productoRepository.deleteById(id);
    }

    @Override
    public Flux<ProductoDto.Response> getAllProductoByIdLocalComercial(String id) {
        return productoRepository.findByIdLocalComercial(id).
                map(ProductoDto::convertToResponce);
    }
}
