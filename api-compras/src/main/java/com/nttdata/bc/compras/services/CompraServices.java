package com.nttdata.bc.compras.services;

import com.nttdata.bc.compras.clients.LocalComercialApiClient;
import com.nttdata.bc.compras.clients.ProductoApiClient;
import com.nttdata.bc.compras.clients.UserApiClient;
import com.nttdata.bc.compras.clients.dto.ProductoDto;
import com.nttdata.bc.compras.clients.dto.UsuarioDto;
import com.nttdata.bc.compras.controllers.dto.CompraDto;
import com.nttdata.bc.compras.exceptions.*;
import com.nttdata.bc.compras.repositories.CompraRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

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
        return compraRepository.findAll().switchIfEmpty(Flux.empty()).map(CompraDto::convertToResponce);
    }

    public Mono<CompraDto.Responce> getCompraById(String id) {
        return compraRepository.findById(id).switchIfEmpty(Mono.error(new Exception("No se encontro la compra"))).map(CompraDto::convertToResponce);
    }

    public Mono<CompraDto.Responce> createCompra(CompraDto.Request request) {
        double montoTotal = calcularMontoTotal(request);
        request.setMontoTotal(montoTotal);

        return userApiClient.getUserById(request.getIdUsuario())
                .switchIfEmpty(Mono.error(new UsuarioException("No se pudo encontrar al usuario: " + request.getIdUsuario())))
                .flatMap(usuario ->
                        validarSaldo(usuario, request) ?
                                validarStockSuficiente(request).flatMap(productos -> {
                                    productos.forEach(producto -> {
                                        producto.setUnidades(
                                                producto.getUnidades() - request.getListaProductos().stream()
                                                        .filter(requestProducto -> requestProducto.getId().equals(producto.getId()))
                                                        .toList().get(0).getUnidades()
                                        );
                                        productoApiClient.updateProducto(producto.getId(), ProductoDto.convertToRequest(producto)).subscribe();
                                    });
                                    usuario.setSaldo(usuario.getSaldo() - montoTotal);
                                    return userApiClient.updateUser(UsuarioDto.convertToRequest(usuario))
                                            .switchIfEmpty(Mono.error(new UsuarioException("No se pudo actualizar el usuario")))
                                            .then(compraRepository.save(CompraDto.convertToEntity(request))
                                                    .switchIfEmpty(Mono.error(new CompraException("No se pudo crear la compra")))
                                                    .flatMap(usuarioSave -> Mono.just(CompraDto.convertToResponce(usuarioSave))));
                                }) :
                                Mono.error(new SaldoInsuficienteException(usuario.getApellidoPaterno(), usuario.getSaldo()))
                );
    }

    private boolean validarSaldo(UsuarioDto usuario, CompraDto.Request request) {
        return usuario.getSaldo() >= request.getMontoTotal();
    }

    private double calcularMontoTotal(CompraDto.Request request) {
        double montoTotal = 0;
        for (CompraDto.ProductoDto producto : request.getListaProductos()) {
            montoTotal += producto.getPrecio() * producto.getUnidades();
        }
        return montoTotal;
    }

    private Mono<List<ProductoDto.Responce>> validarStockSuficiente(CompraDto.Request request) {
        return Flux.fromIterable(request.getListaProductos())
                .flatMap(producto -> productoApiClient.getProductoById(producto.getId())
                        .switchIfEmpty(Mono.error(new ProductoNoEncontradoException(producto.getId())))
                        .flatMap(producto1 -> producto1.getUnidades() >= producto.getUnidades() ?
                                Mono.just(producto1) :
                                Mono.error(new StockInsuficienteException(producto1.getNombre()))))
                .collectList();
    }

    public Mono<CompraDto.Responce> updateCompra(String id, CompraDto.Request request) {
        return compraRepository.findById(id).switchIfEmpty(Mono.error(new CompraException("No se encontro la compra"))).flatMap(compra -> {
            compra.setListaProductos(CompraDto.convertToEntity(request).getListaProductos());
            compra.setMontoTotal(request.getMontoTotal());
            return compraRepository.save(compra).switchIfEmpty(Mono.error(new CompraException("No se pudo actualizar la compra"))).map(CompraDto::convertToResponce);
        });
    }

    public Mono<Void> deleteCompra(String id) {
        return compraRepository.deleteById(id);
    }

}
