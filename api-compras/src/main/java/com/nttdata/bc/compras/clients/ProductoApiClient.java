package com.nttdata.bc.compras.clients;

import com.nttdata.bc.compras.clients.dto.ProductoDto;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductoApiClient {

    private final WebClient.Builder webClientBuilder;
    private final DiscoveryClient discoveryClient;

    public ProductoApiClient(WebClient.Builder webClientBuilder, DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        this.webClientBuilder = webClientBuilder;
    }

    private String url() {
        ServiceInstance serviceInstance = discoveryClient.getInstances("api-productos")
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        return serviceInstance.getUri().toString();
    }

    public Mono<ProductoDto.Response> getProductoById(String id) {
        return webClientBuilder.baseUrl(url())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build().get()
                .uri("/api/productos/{id}", id)
                .retrieve()
                .bodyToMono(ProductoDto.Response.class);
    }

    public Mono<ProductoDto.Response> updateProducto(String id, ProductoDto.Request producto) {
        return webClientBuilder.baseUrl(url())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build().put()
                .uri("/api/productos/editar/{id}", id)
                .body(Mono.just(producto), ProductoDto.Request.class)
                .retrieve()
                .bodyToMono(ProductoDto.Response.class);
    }

}
