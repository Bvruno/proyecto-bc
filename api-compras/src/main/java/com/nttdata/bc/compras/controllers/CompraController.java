package com.nttdata.bc.compras.controllers;

import com.nttdata.bc.compras.controllers.dto.CompraDto;
import com.nttdata.bc.compras.services.CompraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CompraController {

    private final CompraService compraServices;

    CompraController(CompraService compraServices1) {
        this.compraServices = compraServices1;
    }

    @GetMapping()
    public Flux<ResponseEntity<CompraDto.Response>> getCompra() {
        return compraServices.getAllCompra().map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CompraDto.Response>> getCompraById(@PathVariable String id) {
        return compraServices.getCompraById(id).map(ResponseEntity::ok);
    }

    @PostMapping("/crear")
    public Mono<ResponseEntity<CompraDto.Response>> createCompra(@RequestBody CompraDto.Request compra) {
        return compraServices.createCompra(compra).map(ResponseEntity::ok);
    }

    @PutMapping("/editar/{id}")
    public Mono<ResponseEntity<CompraDto.Response>> updateCompra(@PathVariable String id, @RequestBody CompraDto.Request compra) {
        return compraServices.updateCompra(id, compra).map(ResponseEntity::ok);
    }

    @DeleteMapping("/eliminar/{id}")
    public Mono<Void> deleteCompra(@PathVariable String id) {
        return compraServices.deleteCompra(id);
    }

}
