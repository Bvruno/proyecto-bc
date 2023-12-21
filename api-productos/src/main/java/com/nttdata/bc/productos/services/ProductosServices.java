package com.nttdata.bc.productos.services;

import com.nttdata.bc.productos.models.Producto;
import com.nttdata.bc.productos.repositories.ProductosRepository;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductosServices {

    private final ProductosRepository productoRepository;

    ProductosServices(ProductosRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Flux<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Mono<Producto> getProductoById(String id) {
        return productoRepository.findById(id);
    }

    public Mono<Producto> createProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Mono<Producto> updateProducto(String id, Producto producto) {
        return productoRepository.findById(id)
                .flatMap(existingProducto -> {
                    existingProducto.setNombre(producto.getNombre());
                    existingProducto.setPrecio(producto.getPrecio());
                    return productoRepository.save(existingProducto);
                });
    }

    public Mono<Void> deleteProducto(String id) {
        return productoRepository.deleteById(id);
    }

}
