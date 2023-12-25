package com.nttdata.bc.compras.controllers;

import com.nttdata.bc.compras.controllers.dto.CompraDto;
import com.nttdata.bc.compras.services.CompraServices;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CompraController {

    private final CompraServices compraServices;

    CompraController(CompraServices compraServices) {
        this.compraServices = compraServices;
    }

    @GetMapping()
    public Flux<CompraDto.Responce> getCompra() {
        return compraServices.getAllCompra();
    }

    @GetMapping("/{id}")
    public Mono<CompraDto.Responce> getCompraById(@PathVariable String id) {
        return compraServices.getCompraById(id);
    }

    @PostMapping("/crear")
    public Mono<CompraDto.Responce> createCompra(@RequestBody CompraDto.Request compra) {
        return compraServices.createCompra(compra);
    }

    @PutMapping("/editar/{id}")
    public Mono<CompraDto.Responce> updateCompra(@PathVariable String id, @RequestBody CompraDto.Request compra) {
        return compraServices.updateCompra(id, compra);
    }

    @DeleteMapping("/eliminar/{id}")
    public Mono<Void> deleteCompra(@PathVariable String id) {
        return compraServices.deleteCompra(id);
    }

}
