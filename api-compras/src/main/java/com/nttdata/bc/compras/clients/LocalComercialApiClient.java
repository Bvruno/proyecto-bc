package com.nttdata.bc.compras.clients;

import com.nttdata.bc.compras.clients.dto.LocalComercialDto;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class LocalComercialApiClient {

    private final WebClient.Builder webClientBuilder;
    private final DiscoveryClient discoveryClient;

    public LocalComercialApiClient(WebClient.Builder webClientBuilder, DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        this.webClientBuilder = webClientBuilder;
    }

    private String url() {
        ServiceInstance serviceInstance = discoveryClient.getInstances("api-local-comercial")
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        return serviceInstance.getUri().toString();
    }

    public Mono<LocalComercialDto.Responce> getLocalComercialById(String id) {
        return webClientBuilder.baseUrl(url())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build().get()
                .uri("/api/localComercial/{id}", id)
                .retrieve()
                .bodyToMono(LocalComercialDto.Responce.class);
    }
}
