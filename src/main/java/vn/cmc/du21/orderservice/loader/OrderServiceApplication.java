package vn.cmc.du21.orderservice.loader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"vn.cmc.du21.orderservice.presentation.external.controller",
		"vn.cmc.du21.orderservice.presentation.internal.controller",
		"vn.cmc.du21.orderservice.service"})
@EntityScan(basePackages = "vn.cmc.du21.orderservice.persistence.internal.entity")
@EnableJpaRepositories(basePackages = "vn.cmc.du21.orderservice.persistence.internal.repository")
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}
