package com.nttdata.bc.localcomercial.webfux.service;

import com.nttdata.bc.localcomercial.controllers.dto.LocalComercialDto;
import com.nttdata.bc.localcomercial.models.LocalComercial;
import com.nttdata.bc.localcomercial.repositories.LocalComercialRepository;
import com.nttdata.bc.localcomercial.services.LocalComercialServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocalComercialServicesTest {

    @InjectMocks
    LocalComercialServices localComercialServices;

    @Mock
    LocalComercialRepository localComercialRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testGetAllLocalComercial() {
        LocalComercial localComercial1 = LocalComercial.builder().id("1").nombre("Tienda 1").descripcion("Descripción 1").build();
        LocalComercial localComercial2 = LocalComercial.builder().id("2").nombre("Tienda 2").descripcion("Descripción 2").build();
        Flux<LocalComercial> mockResponse = Flux.just(localComercial1, localComercial2);

        when(localComercialRepository.findAll()).thenReturn(mockResponse);

        Flux<LocalComercialDto.Response> result = localComercialServices.getAllLocalComercial();

        StepVerifier.create(result)
                .expectNext(convertToLocalComercialDto(localComercial1))
                .expectNext(convertToLocalComercialDto(localComercial2))
                .expectComplete();
    }

    private LocalComercialDto.Response convertToLocalComercialDto(LocalComercial localComercial) {
        return LocalComercialDto.Response.builder()
                .id(localComercial.getId())
                .nombre(localComercial.getNombre())
                .descripcion(localComercial.getDescripcion())
                .build();
    }

    @Test
    public void testGetLocalComercialById() {
        String id = "1";
        LocalComercial localComercial = LocalComercial.builder().id("1").nombre("Tienda 1").descripcion("Descripción 1").build();

        when(localComercialRepository.findById(id)).thenReturn(Mono.just(localComercial));

        Mono<LocalComercialDto.Response> resultMono = localComercialServices.getLocalComercialById(id);

        LocalComercialDto.Response expectedResponse = LocalComercialDto.convertToResponse(localComercial);
        LocalComercialDto.Response actualResponse = resultMono.block();

        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getNombre(), actualResponse.getNombre());
    }

    @Test
    public void testCreateLocalComercial() {
        LocalComercialDto.Request request = LocalComercialDto.Request.builder()
                .id("3").nombre("Tienda 3").descripcion("Descripción 3").build();
        LocalComercial localComercial = LocalComercial.builder().id("3").nombre("Tienda 3").descripcion("Descripción 3").build();

        when(localComercialRepository.save(any())).thenReturn(Mono.just(localComercial));

        Mono<LocalComercialDto.Response> result = localComercialServices.createLocalComercial(request);

        LocalComercialDto.Response expectedResponse = LocalComercialDto.convertToResponse(localComercial);
        assertEquals(expectedResponse, result.block());
    }

    @Test
    public void testUpdateLocalComercial() {
        String id = "1";
        LocalComercial localComercial = LocalComercial.builder()
                .id("1").nombre("Tienda 1").descripcion("Descripción 1")
                .direccion("Dirección 1").horario("Horario 1").idUsuario("1").build();

        LocalComercialDto.Request request = LocalComercialDto.Request.builder()
                .id("1").nombre("Tienda 1").descripcion("Descripción 1")
                .direccion("Dirección 1").horario("Horario 1").idUsuario("1").build();

        when(localComercialRepository.findById(id)).thenReturn(Mono.just(localComercial));
        when(localComercialRepository.save(localComercial)).thenReturn(Mono.just(localComercial));

        Mono<LocalComercialDto.Response> result = localComercialServices.updateLocalComercial(id, request);

        StepVerifier.create(result)
                .expectNextMatches(response -> {
                    return response.getId().equals(localComercial.getId()) &&
                            response.getNombre().equals(localComercial.getNombre()) &&
                            response.getDescripcion().equals(localComercial.getDescripcion());
                })
                .verifyComplete();
    }

    @Test
    public void testDeleteLocalComercial() {
        String id = "1";

        when(localComercialRepository.deleteById(id)).thenReturn(Mono.empty());

        Mono<Void> result = localComercialServices.deleteLocalComercial(id);

        StepVerifier.create(result)
                .verifyComplete();
    }
}
