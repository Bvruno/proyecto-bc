package com.nttdata.bc.usuarios.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.springframework.beans.factory.annotation.Value;

//@Configuration
public class MongoConfiguration {
    @Value("${spring.data.mongodb.uri}")
    private String connString;

    //@Bean
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
