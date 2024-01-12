package com.nttdata.bc.usuarios.services;

import com.nttdata.bc.usuarios.controllers.dto.response.DniResponse;
import reactor.core.publisher.Mono;

public interface ApisNetService {
    Mono<DniResponse> consultarDNI(String numeroDNI);
}
