package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableHypermediaSupport(type = {HypermediaType.HAL_FORMS, HypermediaType.HAL })
//@EnableHypermediaSupport(type = {HypermediaType.HAL })
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
