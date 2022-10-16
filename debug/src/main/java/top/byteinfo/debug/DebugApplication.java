package top.byteinfo.debug;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DebugApplication {

    public static void main(String[] args) {
        SpringApplication.run(DebugApplication.class, args);
    }

}
