package com.nttdata.bc.localcomercial.webfux.controller;

import com.nttdata.bc.localcomercial.controllers.LocalComercialController;
import com.nttdata.bc.localcomercial.controllers.dto.LocalComercialDto;
import com.nttdata.bc.localcomercial.services.LocalComercialService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class LocalComercialControllerTest {

    private WebTestClient webTestClient;

    @InjectMocks
    LocalComercialController localComercialController;

    @Mock
    private LocalComercialService localComercialService;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(localComercialController).build();
    }

    @Test
    void testGetAllLocalComercial() {
        LocalComercialDto.Response localComercial1 = LocalComercialDto.Response.builder()
                .id("1").nombre("Tienda 1").descripcion("Descripción de la tienda 1")
                .direccion("Dirección de la tienda 1").horario("Horario de la tienda 1").idUsuario("1")
                .build();
        LocalComercialDto.Response localComercial2 = LocalComercialDto.Response.builder()
                .id("2").nombre("Tienda 1").descripcion("Descripción de la tienda 1")
                .direccion("Dirección de la tienda 1").horario("Horario de la tienda 1").idUsuario("1")
                .build();

        Flux<LocalComercialDto.Response> mockResponse = Flux.just(localComercial1, localComercial2);

        when(localComercialService.getAllLocalComercial()).thenReturn(mockResponse);

        webTestClient.get()
                .uri("")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(LocalComercialDto.Response.class)
                .hasSize(2)
                .contains(localComercial1, localComercial2);
    }

    @Test
    void testGetAllLocalComercialError() {
        Flux<LocalComercialDto.Response> mockResponse = Flux.just(null, null);

        when(localComercialService.getAllLocalComercial()).thenReturn(mockResponse);

        webTestClient.get().uri("")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody().isEmpty();
    }

    @Test
    public void testGetLocalComercialById() {
        String id = "1";
        LocalComercialDto.Response mockResponse = LocalComercialDto.Response.builder()
                .id("1").nombre("Tienda 1").descripcion("Descripción de la tienda 1")
                .direccion("Dirección de la tienda 1").horario("Horario de la tienda 1").idUsuario("1")
                .build();

        when(localComercialService.getLocalComercialById(id)).thenReturn(Mono.just(mockResponse));

        webTestClient.get()
                .uri("/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(LocalComercialDto.Response.class)
                .contains(mockResponse);
    }

    @Test
    public void testGetLocalComercialByIdError() {
        String id = "non_existent_id";
        String errorMessage = "No se encontro el local comercial";

        when(localComercialService.getLocalComercialById(id)).thenReturn(Mono.error(new Exception(errorMessage)));

        webTestClient.get()
                .uri("/{id}", id)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    public void testCreateLocalComercial() {
        LocalComercialDto.Request request = LocalComercialDto.Request.builder()
                .id("1").nombre("Tienda 1").descripcion("Descripción de la tienda 1")
                .direccion("Dirección de la tienda 1").horario("Horario de la tienda 1").idUsuario("1")
                .build();

        LocalComercialDto.Response mockResponse = LocalComercialDto.Response.builder()
                .id("1").nombre("Tienda 1").descripcion("Descripción de la tienda 1")
                .direccion("Dirección de la tienda 1").horario("Horario de la tienda 1").idUsuario("1")
                .build();

        when(localComercialService.createLocalComercial(request)).thenReturn(Mono.just(mockResponse));

        webTestClient.post()
                .uri("/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(LocalComercialDto.Response.class)
                .isEqualTo(mockResponse);
    }

    @Test
    public void testUpdateLocalComercial() {
        String id = "1";
        LocalComercialDto.Request request = LocalComercialDto.Request.builder()
                .id("1").nombre("Tienda 1").descripcion("Descripción de la tienda 1")
                .direccion("Dirección de la tienda 1").horario("Horario de la tienda 1").idUsuario("1")
                .build();
        LocalComercialDto.Response mockResponse = LocalComercialDto.Response.builder()
                .id("1").nombre("Tienda 1").descripcion("Descripción de la tienda 1")
                .direccion("Dirección de la tienda 1").horario("Horario de la tienda 1").idUsuario("1")
                .build();

        when(localComercialService.updateLocalComercial(eq(id), any())).thenReturn(Mono.just(mockResponse));

        webTestClient.put()
                .uri("/editar/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectBody(LocalComercialDto.Response.class);
    }

    @Test
    public void testDeleteLocalComercial() {
        String id = "1";

        when(localComercialService.deleteLocalComercial(id)).thenReturn(Mono.empty());

        Mono<Void> result = localComercialController.deleteLocalComercial(id);

        assertNull(result.block());
        webTestClient.delete()
                .uri("/eliminar/{id}", id)
                .exchange()
                .expectStatus().isOk();
    }
}
