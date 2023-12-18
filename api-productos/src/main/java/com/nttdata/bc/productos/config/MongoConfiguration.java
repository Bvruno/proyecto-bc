package com.nttdata.bc.productos.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfiguration {
    @Value("${spring.data.mongodb.uri}")
    private String connString;

    @Bean
    public MongoClientSettings mongoClientSettings() {
        return MongoClientSettings.builder()
                .applyToSslSettings(
                        builder -> builder
                                .enabled(true))
                .applyConnectionString(
                        new ConnectionString( connString)
                )
                .build();
    }
}
