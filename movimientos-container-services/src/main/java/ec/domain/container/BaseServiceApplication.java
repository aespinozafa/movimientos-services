package ec.domain.container;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableFeignClients(basePackages = "ec.domain")
@SpringBootApplication(scanBasePackages = "ec.domain")
@EnableJpaRepositories(basePackages = {"ec.domain"})
@EntityScan(basePackages = {"ec.domain"})
public class BaseServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(BaseServiceApplication.class, args);
  }
}
