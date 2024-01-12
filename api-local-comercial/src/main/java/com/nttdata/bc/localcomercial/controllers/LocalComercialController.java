package com.nttdata.bc.localcomercial.controllers;

import com.nttdata.bc.localcomercial.controllers.dto.LocalComercialDto;
import com.nttdata.bc.localcomercial.services.LocalComercialService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class LocalComercialController {

    private final LocalComercialService localComercialService;

    LocalComercialController(LocalComercialService localComercialService) {
        this.localComercialService = localComercialService;
    }

    @GetMapping()
    public Flux<LocalComercialDto.Response> getLocalComercial() {
        return localComercialService.getAllLocalComercial();
    }

    @GetMapping("/{id}")
    public Mono<LocalComercialDto.Response> getLocalComercialById(@PathVariable String id) {
        return localComercialService.getLocalComercialById(id);
    }

    @PostMapping("/crear")
    public Mono<LocalComercialDto.Response> createLocalComercial(@RequestBody LocalComercialDto.Request localComercia) {
        return localComercialService.createLocalComercial(localComercia);
    }

    @PutMapping("/editar/{id}")
    public Mono<LocalComercialDto.Response> updateLocalComercial(
            @PathVariable String id,
            @RequestBody LocalComercialDto.Request localComercia
    ) {
        return localComercialService.updateLocalComercial(id, localComercia);
    }

    @DeleteMapping("/eliminar/{id}")
    public Mono<Void> deleteLocalComercial(@PathVariable String id) {
        return localComercialService.deleteLocalComercial(id);
    }

}
