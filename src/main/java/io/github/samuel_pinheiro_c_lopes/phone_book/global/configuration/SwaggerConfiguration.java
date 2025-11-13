package io.github.samuel_pinheiro_c_lopes.phone_book.global.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfiguration {
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI().info(
						new Info()
							.title("Medical appointment API")
							.description("API to manage medical appointments")
							.version("1.0")
					);
	}
}