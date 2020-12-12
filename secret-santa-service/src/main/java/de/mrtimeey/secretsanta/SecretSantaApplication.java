package de.mrtimeey.secretsanta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class SecretSantaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecretSantaApplication.class, args);
	}

}
