package oit.is.z2722.kaizi.janken;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
class JankenApplication {

  public static void main(String[] args) {
    SpringApplication.run(JankenApplication.class, args);
  }

}
