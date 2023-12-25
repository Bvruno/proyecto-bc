package com.nttdata.bc.productos.services;

import com.nttdata.bc.productos.controllers.dto.ProductoDto;
import com.nttdata.bc.productos.repositories.ProductoRepository;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoServices {

    private final ProductoRepository productoRepository;

    ProductoServices(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Flux<ProductoDto.Responce> getAllProductos() {
        return productoRepository.findAll()
                .switchIfEmpty(Flux.empty())
                .map(ProductoDto::convertToResponce);
    }

    public Mono<ProductoDto.Responce> getProductoById(String id) {
        return productoRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("No se encontro el producto")))
                .map(ProductoDto::convertToResponce);
    }

    public Mono<ProductoDto.Responce> createProducto(ProductoDto.Request producto) {
        return productoRepository.save(ProductoDto.convertToEntity(producto))
                .switchIfEmpty(Mono.error(new Exception("No se pudo crear el producto")))
                .map(ProductoDto::convertToResponce);
    }

    public Mono<ProductoDto.Responce> updateProducto(String id, ProductoDto.Request producto) {
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

    public Mono<Void> deleteProducto(String id) {
        return productoRepository.deleteById(id);
    }

    public Flux<ProductoDto.Responce> getAllProductoByIdLocalComercial(String id) {
        return productoRepository.findByIdLocalComercial(id).
                map(ProductoDto::convertToResponce);
    }
}
