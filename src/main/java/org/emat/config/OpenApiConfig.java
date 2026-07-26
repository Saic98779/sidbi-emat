package org.emat.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        Server productionServer = new Server();
        productionServer.setUrl("https://api.emat.metaversedu.in/emat/v1");
        productionServer.setDescription("Production");

        Server localServer = new Server();
        localServer.setUrl("http://localhost:8086/emat/v1");
        localServer.setDescription("Local Development");

        return new OpenAPI()
                .servers(List.of(localServer, productionServer))
                .info(new Info()
                        .title("EMAT API")
                        .version("1.0")
                        .description("EMAT Application API Documentation"))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}