package com.example.debeziumdebug.schdule;

import com.example.debeziumdebug.component.Debezium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Task {
    final
    Debezium debezium;

    @Autowired
    public Task(Debezium debezium) {
        this.debezium = debezium;
    }

    @Scheduled(fixedDelay = 1L)
    public void task(){
        debezium.run();
    }
}
