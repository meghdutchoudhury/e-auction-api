package com.fse.eauction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	//http://localhost:9083/seller/swagger-ui/index.html
	
	@Bean
    public OpenAPI sellerApi() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("E-Auction Query REST API").description("This is the Query API for the E-Auction Application"));
    }
}
