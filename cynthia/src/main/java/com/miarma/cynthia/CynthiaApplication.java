package com.miarma.cynthia;

import com.miarma.cynthia.config.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class CynthiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CynthiaApplication.class, args);
	}

}
