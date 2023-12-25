package com.nttdata.bc.compras.services;

import com.nttdata.bc.compras.clients.LocalComercialApiClient;
import com.nttdata.bc.compras.clients.ProductoApiClient;
import com.nttdata.bc.compras.clients.UserApiClient;
import com.nttdata.bc.compras.clients.dto.ProductoDto;
import com.nttdata.bc.compras.clients.dto.UsuarioDto;
import com.nttdata.bc.compras.controllers.dto.CompraDto;
import com.nttdata.bc.compras.exceptions.UsuarioNoEncontradoException;
import com.nttdata.bc.compras.repositories.CompraRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class CompraServices {

    private final CompraRepository compraRepository;
    private final ProductoApiClient productoApiClient;
    private final UserApiClient userApiClient;
    private final LocalComercialApiClient localComercialApiClient;

    CompraServices(CompraRepository compraRepository, ProductoApiClient productoApiClient, UserApiClient userApiClient, LocalComercialApiClient localComercialApiClient) {
        this.compraRepository = compraRepository;
        this.productoApiClient = productoApiClient;
        this.userApiClient = userApiClient;
        this.localComercialApiClient = localComercialApiClient;
    }

    public Flux<CompraDto.Responce> getAllCompra() {
        return compraRepository.findAll()
                .switchIfEmpty(Flux.empty())
                .map(CompraDto::convertToResponce);
    }

    public Mono<CompraDto.Responce> getCompraById(String id) {
        return compraRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("No se encontro la compra")))
                .map(CompraDto::convertToResponce);
    }

    public Mono<CompraDto.Responce> createCompraDemo(CompraDto.Request localComercial) {
        return compraRepository.save(CompraDto.convertToEntity(localComercial))
                .switchIfEmpty(Mono.error(new Exception("No se pudo crear la compra")))
                .map(CompraDto::convertToResponce);
    }

    public Mono<CompraDto.Responce> createCompra(CompraDto.Request request) {
        double montoTotal = calcularMontoTotal(request); // Calcular monto total
        log.info("Monto total: {}", montoTotal);
        request.setMontoTotal(montoTotal);
        Mono<UsuarioDto> usuarioMono = userApiClient.getUserById(request.getIdUsuario())
                .switchIfEmpty(Mono.error(new UsuarioNoEncontradoException())); // Obtener usuario
        log.info("Request: {}", request);
        return usuarioMono
                .flatMap(usuario -> validarSaldo(usuario, request) ? // Validar saldo
                        validarStockPorProducto(request) // Validar stock
                                .flatMap(productos -> {
                                    log.info("Usuario: {}", usuario);
                                    log.info("Productos encontrado: {}", productos);
                                    productos.forEach(producto -> {
                                        request.getListaProductos().forEach(requestProducto -> {
                                            if (requestProducto.getId().equals(producto.getId())) {
                                                log.info("Producto: {}", producto);
                                                producto.setUnidades(producto.getUnidades() - requestProducto.getUnidades());
                                                log.info("Producto final: {}", producto);
                                                productoApiClient.updateProducto(producto.getId(), ProductoDto.convertToRequest(producto)).subscribe(); // Actualizar producto;
                                            }
                                        });
                                    });
                                    usuario.setSaldo(usuario.getSaldo() - montoTotal); // Actualizar saldo
                                    log.info("Usuario final: {}", usuario);
                                    log.info("Request final: {}", request);
                                    return userApiClient.updateUser(UsuarioDto.convertToRequest(usuario))
                                            .switchIfEmpty(Mono.error(new Exception("No se pudo actualizar el usuario")))
                                            .flatMap(usuarioflap -> {
                                                        log.info("Usuario flap: {}", usuarioflap);
                                                        return compraRepository.save(CompraDto.convertToEntity(request))
                                                                .switchIfEmpty(Mono.error(new Exception("No se pudo crear la compra")))
                                                                .flatMap(usuar -> Mono.just(CompraDto.convertToResponce(usuar)));
                                                    }
                                            );
                                }) :
                        Mono.error(new Exception("EL usuario no tiene saldo suficiente")));
    }

    private double calcularMontoTotal(CompraDto.Request request) {
        double montoTotal = 0;
        for (CompraDto.ProductoDto producto : request.getListaProductos()) {
            montoTotal += producto.getPrecio() * producto.getUnidades();
        }
        return montoTotal;
    }

    private boolean validarSaldo(UsuarioDto usuario, CompraDto.Request request) {
        log.info("Usuario.saldo: {}", usuario.getSaldo());
        return usuario.getSaldo() >= request.getMontoTotal();
    }

    private Mono<List<ProductoDto.Responce>> validarStockPorProducto(CompraDto.Request request) {
        return Flux.fromIterable(request.getListaProductos())
                .flatMap(producto -> productoApiClient.getProductoById(producto.getId())
                        .switchIfEmpty(Mono.error(new Exception("No se encontro el producto"))))
                .collectList();
    }


    public Mono<CompraDto.Responce> updateCompra(String id, CompraDto.Request request) {
        return compraRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("No se encontro la compra")))
                .flatMap(compra -> {
                    compra.setListaProductos(CompraDto.convertToEntity(request).getListaProductos());
                    compra.setMontoTotal(request.getMontoTotal());
                    return compraRepository.save(compra)
                            .switchIfEmpty(Mono.error(new Exception("No se pudo actualizar la compra")))
                            .map(CompraDto::convertToResponce);
                });
    }

    public Mono<Void> deleteCompra(String id) {
        return compraRepository.deleteById(id);
    }

}
