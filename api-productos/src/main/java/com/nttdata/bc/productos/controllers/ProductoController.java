package com.nttdata.bc.productos.controllers;

import com.nttdata.bc.productos.controllers.dto.ProductoDto;
import com.nttdata.bc.productos.services.ProductoService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ProductoController {

    private final ProductoService productoServices;

    public ProductoController(ProductoService productoServices) {
        this.productoServices = productoServices;
    }


    @GetMapping()
    public Flux<ProductoDto.Response> getProductos() {
        return productoServices.getAllProductos();
    }

    @GetMapping("/{id}")
    public Mono<ProductoDto.Response> getProductoById(@PathVariable String id) {
        return productoServices.getProductoById(id);
    }

    @GetMapping("/localComercial/{id}")
    public Flux<ProductoDto.Response> getAllProductoByIdLocalComercial(@PathVariable String id) {
        return productoServices.getAllProductoByIdLocalComercial(id);
    }

    @PostMapping("/crear")
    public Mono<ProductoDto.Response> createProducto(@RequestBody ProductoDto.Request producto) {
        return productoServices.createProducto(producto);
    }

    @PutMapping("/editar/{id}")
    public Mono<ProductoDto.Response> updateProducto(
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
