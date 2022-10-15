package top.byteinfo.debug.component;

import io.debezium.config.Configuration;
import io.debezium.embedded.EmbeddedEngine;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executor;

@Component
public class Debezium implements Runnable{

    Configuration config = Configuration.create()
            /* begin engine properties */
            .with("name", "my-sql-connector")
            .with("connector.class", "io.debezium.connector.mysql.MySqlConnector")
            .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
            .with("offset.storage.file.filename", "/path/to/storage/offset.dat")
            .with("offset.flush.interval.ms", 60000)
            /* begin connector properties */
            .with("database.hostname", "localhost")
            .with("database.port", 3306)
            .with("database.user", "mysqluser")
            .with("database.password", "mysqlpw")
            .with("server.id", 85744)
            .with("server.name", "my-app-connector")
            .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
            .with("database.history.file.filename", "/path/to/storage/dbhistory.dat")
            .build();

    // Create the engine with this configuration ...


    private void handleEvent(List<SourceRecord> sourceRecords, DebeziumEngine.RecordCommitter<SourceRecord> sourceRecordRecordCommitter) {
    }

    // Run the engine asynchronously ...


    // At some later time ...

    @Override
    public void run() {

    }
}
