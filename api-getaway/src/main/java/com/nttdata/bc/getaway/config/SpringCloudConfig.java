package com.nttdata.bc.getaway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class SpringCloudConfig {

    //@Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("api-usuarios", r -> r.path("/api/usuarios/**")
                        .filters(f -> f.stripPrefix(2))
                        .uri("lb://api-usuarios"))
                .route("api-productos", r -> r.path("/api/productos/**")
                        .filters(f -> f.stripPrefix(2))
                        .uri("lb://api-productos"))
                .route("api-notificaciones", r -> r.path("/api/notificaciones/**")
                        .filters(f -> f.stripPrefix(2))
                        .uri("lb://api-notificaciones"))
                .route("api-pagos", r -> r.path("/api/pagos/**")
                        .filters(f -> f.stripPrefix(2))
                        .uri("lb://api-pagos"))
                .route("api-saldos", r -> r.path("/api/saldos/**")
                        .filters(f -> f.stripPrefix(2))
                        .uri("lb://api-saldos"))
                .build();
    }

}
