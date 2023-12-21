package com.nttdata.bc.productos.controllers;

import com.nttdata.bc.productos.models.Producto;
import com.nttdata.bc.productos.services.ProductosServices;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ProductosController {

  private final ProductosServices productosServices;

    ProductosController(ProductosServices productosServices) {
        this.productosServices = productosServices;
    }

  @GetMapping()
  public Flux<Producto> getProductos() {
    return productosServices.getAllProductos();
  }

  @GetMapping("/{id}")
  public Mono<Producto> getProductoById(@PathVariable String id) {
    return productosServices.getProductoById(id);
  }

  @PostMapping("/crear")
  public Mono<Producto> createProducto(@RequestBody Producto producto) {
    return productosServices.createProducto(producto);
  }

  @PutMapping("/editar/{id}")
  public Mono<Producto> updateProducto(
    @PathVariable String id,
    @RequestBody Producto producto
  ) {
    return productosServices.updateProducto(id, producto);
  }

  @DeleteMapping("/eliminar/{id}")
  public Mono<Void> deleteProducto(@PathVariable String id) {
    return productosServices.deleteProducto(id);
  }

}
