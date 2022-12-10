package top.byteinfo.debug.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.byteinfo.debug.component.Debezium;

import java.util.concurrent.TimeUnit;

@Component
public class Task {
    final
    Debezium debezium;

    @Autowired
    public Task(Debezium debezium) {
        this.debezium = debezium;
    }

    @Scheduled(fixedDelay = 1L,timeUnit = TimeUnit.DAYS)
    public void task(){
        debezium.run();
    }
}
