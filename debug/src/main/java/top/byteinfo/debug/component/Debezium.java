package top.byteinfo.debug.component;

import io.debezium.config.Configuration;
import io.debezium.embedded.Connect;
import io.debezium.embedded.EmbeddedEngine;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import io.debezium.engine.format.Json;
import io.debezium.engine.spi.OffsetCommitPolicy;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

@Slf4j
@Component
public class Debezium implements Runnable {


    final Properties props = new Properties();

    public void config() {
        props.setProperty("name", "engine");
        props.setProperty("connector.class", "io.debezium.connector.mysql.MySqlConnector");
        props.setProperty("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore");
        props.setProperty("offset.storage.file.filename", "debug/src/main/resources/static/offsets.dat");
        props.setProperty("offset.flush.interval.ms", "60000");
        /* begin connector properties */
        props.setProperty("database.hostname", "localhost");
        props.setProperty("database.port", "3306");
        props.setProperty("database.user", "root");
        props.setProperty("database.password", "root");
        props.setProperty("database.server.id", "85744");
        props.setProperty("database.server.name", "my-app-connector");
        props.setProperty("database.history",
                "io.debezium.relational.history.FileDatabaseHistory");
        props.setProperty("database.history.file.filename",
                "debug/src/main/resources/static/dbhistory.dat");
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
