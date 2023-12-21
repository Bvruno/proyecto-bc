package com.nttdata.bc.productos;

import com.nttdata.bc.productos.controllers.ProductoController;
import com.nttdata.bc.productos.controllers.dto.ProductoDto;
import com.nttdata.bc.productos.services.ProductoServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductosApplicationTests {

	private WebTestClient webTestClient;

	@InjectMocks
	private ProductoController productoController;

	@Mock
	private ProductoServices productoServices;

	@BeforeEach
	void setUp() {
		webTestClient = WebTestClient.bindToController(productoController).build();
	}

	@Test
	void findAll() {
		ProductoDto.Responce entidad1 = ProductoDto.Responce.builder()
				.id("1").nombre("carne").descripcion("carne de res").precio(100.0).idTienda("1").build();
		ProductoDto.Responce entidad2 = ProductoDto.Responce.builder()
				.id("2").nombre("leche").descripcion("leche deslactosada").precio(50.0).idTienda("1").build();
		when(productoServices.getAllProductos()).thenReturn(Flux.just(entidad1, entidad2));

		webTestClient.get()
				.uri("")
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(ProductoDto.Responce.class)
				.hasSize(2)
				.contains(entidad1, entidad2);
	}

	@Test
	void findById() {
		ProductoDto.Responce entidad1 = ProductoDto.Responce.builder()
				.id("1").nombre("carne").descripcion("carne de res").precio(100.0).idTienda("1").build();
		when(productoServices.getProductoById("1")).thenReturn(Mono.just(entidad1));

		webTestClient.get()
				.uri("/1")
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(ProductoDto.Responce.class)
				.hasSize(1)
				.contains(entidad1);
	}

	@Test
	void create() {
		ProductoDto.Request entidad = ProductoDto.Request.builder()
				.nombre("carne").descripcion("carne de res").precio(100.0).idTienda("1").build();
		ProductoDto.Responce entidadResponce = ProductoDto.Responce.builder()
				.id("1").nombre("carne").descripcion("carne de res").precio(100.0).idTienda("1").build();
		when(productoServices.createProducto(entidad)).thenReturn(Mono.just(entidadResponce));

		webTestClient.post()
				.uri("/crear")
				.body(Mono.just(entidad), ProductoDto.Request.class)
				.exchange()
				.expectStatus().isOk()
				.expectBody(ProductoDto.Responce.class)
				.isEqualTo(entidadResponce);
	}

	@Test
	void update() {
		ProductoDto.Request entidad = ProductoDto.Request.builder()
				.nombre("carne").descripcion("carne de res").precio(100.0).idTienda("1").build();
		ProductoDto.Responce entidadResponce = ProductoDto.Responce.builder()
				.id("1").nombre("carne").descripcion("carne de res").precio(100.0).idTienda("1").build();
		when(productoServices.updateProducto("1", entidad)).thenReturn(Mono.just(entidadResponce));

		webTestClient.put()
				.uri("/editar/1")
				.body(Mono.just(entidad), ProductoDto.Request.class)
				.exchange()
				.expectStatus().isOk()
				.expectBody(ProductoDto.Responce.class)
				.isEqualTo(entidadResponce);
	}

	@Test
	void delete() {
		when(productoServices.deleteProducto("1")).thenReturn(Mono.empty());

		webTestClient.delete()
				.uri("/eliminar/1")
				.exchange()
				.expectStatus().isOk();
	}

}
