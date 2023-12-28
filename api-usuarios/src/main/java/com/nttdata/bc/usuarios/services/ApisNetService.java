package com.nttdata.bc.usuarios.services;

import com.google.gson.Gson;
import com.nttdata.bc.usuarios.controllers.dto.response.DniResponse;
import org.springframework.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
@Slf4j
public class ApisNetService {

    private final WebClient webClient;
    @Value("${apis-net.url}")
    private String urlDNI;
    @Value("${apis-net.token}")
    private String token;

    public ApisNetService() {
        this.webClient = WebClient.builder().build();
    }

    public Mono<DniResponse> consultarDNI(String numeroDNI) {
        URI uri = UriComponentsBuilder.fromUriString(urlDNI)
                .queryParam("numero", numeroDNI)
                .build()
                .toUri();

        return webClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(),
                        clientResponse -> {
                            log.error("La solicitud no fue exitosa. Código de respuesta: {}", clientResponse.statusCode().value());
                            return Mono.empty();
                        })
                .bodyToMono(String.class)
                .map(response -> {
                    Gson gson = new Gson();
                    return gson.fromJson(response, DniResponse.class);
                });
    }
}
