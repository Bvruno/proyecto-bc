package com.nttdata.bc.productos.controllers;

import com.nttdata.bc.productos.controllers.dto.ProductoDto;
import com.nttdata.bc.productos.services.ProductoServices;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ProductoController {

    private final ProductoServices productoServices;

    ProductoController(ProductoServices productoServices) {
        this.productoServices = productoServices;
    }

    @GetMapping()
    public Flux<ProductoDto.Responce> getProductos() {
        return productoServices.getAllProductos();
    }

    @GetMapping("/{id}")
    public Mono<ProductoDto.Responce> getProductoById(@PathVariable String id) {
        return productoServices.getProductoById(id);
    }

    @PostMapping("/crear")
    public Mono<ProductoDto.Responce> createProducto(@RequestBody ProductoDto.Request producto) {
        return productoServices.createProducto(producto);
    }

    @PutMapping("/editar/{id}")
    public Mono<ProductoDto.Responce> updateProducto(
            @PathVariable String id,
            @RequestBody ProductoDto.Request producto
    ) {
        return productoServices.updateProducto(id, producto);
    }

    @DeleteMapping("/eliminar/{id}")
    public Mono<Void> deleteProducto(@PathVariable String id) {
        return productoServices.deleteProducto(id);
    }

}
