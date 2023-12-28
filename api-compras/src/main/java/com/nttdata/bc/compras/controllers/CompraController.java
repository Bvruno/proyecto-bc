package com.nttdata.bc.compras.controllers;

import com.nttdata.bc.compras.controllers.dto.CompraDto;
import com.nttdata.bc.compras.services.CompraServices;
import org.springframework.http.ResponseEntity;
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
    public Flux<ResponseEntity<CompraDto.Responce>> getCompra() {
        return compraServices.getAllCompra().map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CompraDto.Responce>> getCompraById(@PathVariable String id) {
        return compraServices.getCompraById(id).map(ResponseEntity::ok);
    }

    @PostMapping("/crear")
    public Mono<ResponseEntity<CompraDto.Responce>> createCompra(@RequestBody CompraDto.Request compra) {
        return compraServices.createCompra(compra).map(ResponseEntity::ok);
    }

    @PutMapping("/editar/{id}")
    public Mono<ResponseEntity<CompraDto.Responce>> updateCompra(@PathVariable String id, @RequestBody CompraDto.Request compra) {
        return compraServices.updateCompra(id, compra).map(ResponseEntity::ok);
    }

    @DeleteMapping("/eliminar/{id}")
    public Mono<Void> deleteCompra(@PathVariable String id) {
        return compraServices.deleteCompra(id);
    }

}
