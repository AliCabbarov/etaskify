package etaskify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ETaskifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ETaskifyApplication.class, args);
    }

}
