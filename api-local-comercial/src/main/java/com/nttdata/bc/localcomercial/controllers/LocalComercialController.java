package com.nttdata.bc.localcomercial.controllers;

import com.nttdata.bc.localcomercial.controllers.dto.LocalComercialDto;
import com.nttdata.bc.localcomercial.services.LocalComercialServices;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class LocalComercialController {

    private final LocalComercialServices localComercialServices;

    LocalComercialController(LocalComercialServices localComercialServices) {
        this.localComercialServices = localComercialServices;
    }

    @GetMapping()
    public Flux<LocalComercialDto.Response> getLocalComercial() {
        return localComercialServices.getAllLocalComercial();
    }

    @GetMapping("/{id}")
    public Mono<LocalComercialDto.Response> getLocalComercialById(@PathVariable String id) {
        return localComercialServices.getLocalComercialById(id);
    }

    @PostMapping("/crear")
    public Mono<LocalComercialDto.Response> createLocalComercial(@RequestBody LocalComercialDto.Request localComercia) {
        return localComercialServices.createLocalComercial(localComercia);
    }

    @PutMapping("/editar/{id}")
    public Mono<LocalComercialDto.Response> updateLocalComercial(
            @PathVariable String id,
            @RequestBody LocalComercialDto.Request localComercia
    ) {
        return localComercialServices.updateLocalComercial(id, localComercia);
    }

    @DeleteMapping("/eliminar/{id}")
    public Mono<Void> deleteLocalComercial(@PathVariable String id) {
        return localComercialServices.deleteLocalComercial(id);
    }

}
