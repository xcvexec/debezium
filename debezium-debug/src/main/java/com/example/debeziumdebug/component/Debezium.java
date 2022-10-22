package com.example.debeziumdebug.component;

import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import io.debezium.engine.spi.OffsetCommitPolicy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Properties;
@Component
@Slf4j
public class Debezium implements Runnable{
    final Properties props = new Properties();

    private void old_config(){
        props.setProperty("name", "engine");
        props.setProperty("topic.prefix","debug");
        props.setProperty("connector.class", "io.debezium.connector.mysql.MySqlConnector");
        props.setProperty("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore");
        props.setProperty("offset.storage.file.filename", "src/main/resources/static/offsets.dat");
        props.setProperty("offset.flush.interval.ms", "60000");
        /*begin connector properties */
        props.setProperty("database.hostname", "localhost");
        props.setProperty("database.port", "3306");
        props.setProperty("database.user", "root");
        props.setProperty("database.password", "root");
        props.setProperty("database.server.id", "85743");
        props.setProperty("database.server.name", "my-app-connector");
        props.setProperty("database.history",
                "io.debezium.relational.history.FileDatabaseHistory");
        props.setProperty("database.history.file.filename",
                "src/main/resources/static/dbhistory.dat");
    }
    public void config() {
//        final Properties props = config.asProperties();
        props.setProperty("name", "engine");
        props.setProperty("connector.class", "io.debezium.connector.mysql.MySqlConnector");
        props.setProperty("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore");
        props.setProperty("offset.storage.file.filename", "debezium-debug/src/main/resources/static/offsets.dat");
        props.setProperty("offset.flush.interval.ms", "60000");
        /* begin connector properties */
        props.setProperty("database.hostname", "localhost");
        props.setProperty("database.port", "3306");
        props.setProperty("database.user", "root");
        props.setProperty("database.password", "root");
        props.setProperty("database.server.id", "85741");
        props.setProperty("topic.prefix", "my-app-connector");
        props.setProperty("schema.history.internal",
                "io.debezium.storage.file.history.FileSchemaHistory");
        props.setProperty("schema.history.internal.file.filename",
                "debezium-debug/src/main/resources/static/dbhistory.dat");



    }




    public void engine() {


        DebeziumEngine<ChangeEvent<String, String>> engine = DebeziumEngine.create(Json.class)
                .using(props)
                .notifying((records, committer) -> {
                            for (ChangeEvent<String,String> record : records) {
                                log.info("\n"+record.key()+"\n"+record.value());
                                committer.markProcessed(record);
                            }
                            committer.markBatchFinished();
                        }
                )
                .using(OffsetCommitPolicy.always())
                .build();
        engine.run();
//        debeziumEngine.run();
    }


    @Override
    public void run() {
        config();
        engine();
    }


    public static void main(String[] args) {
        Debezium debezium = new Debezium();

        debezium.run();
    }

}
