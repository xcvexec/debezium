package com.example.debug.component;

import io.debezium.config.Configuration;
import io.debezium.embedded.EmbeddedEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Debezium implements Runnable{

    // Define the configuration for the embedded and MySQL connector ...
    Configuration config = Configuration.create()
            /* begin engine properties */
            .with("name", "my-sql-connector")
            .with("connector.class", "io.debezium.connector.mysql.MySqlConnector")
            .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
            .with("offset.storage.file.filename", "debug/src/main/resources/static/storage/offset.dat")
            .with("offset.flush.interval.ms", 60000)
            /* begin connector properties */
            .with("database.hostname", "192.168.1.101")
            .with("database.port", 3306)
            .with("database.user", "root")
            .with("database.password", "root")
            .with("server.id", 85744)
            .with("database.server.name", "my-app-connector")
            .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
            .with("database.history.file.filename", "debug/src/main/resources/static/storage/dbhistory.dat")
            .build();

    // Create the engine with this configuration ...
    EmbeddedEngine engine = EmbeddedEngine.create()
            .using(config)
            .notifying(record -> {
                log.info(record.value().toString());
            })
            .build();



    @Override
    public void run() {

        engine.run();
    }

    public static void main(String[] args) {

    }
}
