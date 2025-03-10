package com.website.blogapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
	
	private static final String SCHEME_NAME = "BearerScheme";
	
	@Bean
	public OpenAPI blogAppOpenAPI() {
		return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SCHEME_NAME, new SecurityScheme()
                                .name(SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
				
				.info(new Info()
						.title("Blog Management System")
						.description("This is Blog Management System RESTful API - developed by Suyog Palav")
						.version("v0.0.1")
						.contact(new Contact().name("Suyog").url("https://github.com/SuyogPalav"))
						.license(new License().name("Apache 2.0")))
				
				.externalDocs(new ExternalDocumentation()
						.description("Blog Management System RESTful API Documentation")
						.url("http://localhost:8080/swagger-ui/index.html"));
	}

}
