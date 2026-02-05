package com.crowdsourced.wasteplatform.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme bearerScheme = new SecurityScheme()
            .name("bearerAuth")
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT");

        return new OpenAPI()
            .info(new Info().title("Crowdsourced Waste Collection & Recycling Platform API"))
            .components(new Components().addSecuritySchemes("bearerAuth", bearerScheme))
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .tags(List.of(
                new Tag().name("Auth"),
                new Tag().name("Admin"),
                new Tag().name("Citizen"),
                new Tag().name("Collector"),
                new Tag().name("Reports"),
                new Tag().name("Rewards"),
                new Tag().name("Complaints"),
                new Tag().name("Notifications")
            ));
    }
}
