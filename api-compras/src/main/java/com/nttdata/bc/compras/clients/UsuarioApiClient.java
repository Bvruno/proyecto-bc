package com.nttdata.bc.compras.clients;

import com.nttdata.bc.compras.clients.dto.UsuarioDto;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UsuarioApiClient {

    private final WebClient.Builder webClientBuilder;
    private final DiscoveryClient discoveryClient;

    public UsuarioApiClient(WebClient.Builder webClientBuilder, DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        this.webClientBuilder = webClientBuilder;
    }

    private String url() {
        ServiceInstance serviceInstance = discoveryClient.getInstances("api-usuarios")
                .stream().findFirst().orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        return serviceInstance.getUri().toString();
    }

    public Mono<UsuarioDto> getUsuarioById(String id) {
        return webClientBuilder.baseUrl(url())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build()
                .get()
                .uri("/api/usuarios/{id}", id)
                .retrieve()
                .bodyToMono(UsuarioDto.class);
    }

    public Mono<UsuarioDto> updateUsuario(UsuarioDto.Request usuario) {
        return webClientBuilder.baseUrl(url())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build()
                .put()
                .uri("/api/usuarios/editar")
                .body(Mono.just(usuario), UsuarioDto.class)
                .retrieve()
                .bodyToMono(UsuarioDto.class);
    }

}
