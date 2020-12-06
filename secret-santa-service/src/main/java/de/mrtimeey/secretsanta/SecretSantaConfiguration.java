package de.mrtimeey.secretsanta;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecretSantaConfiguration {

    @Bean
    public OpenAPI customOpenAPI(BuildProperties buildProperties) {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title(buildProperties.getArtifact())
                        .description(buildProperties.get("project.description"))
                        .version(buildProperties.getVersion()));
    }

}
