package de.mrtimeey.secretsanta.group.rest;

import de.mrtimeey.secretsanta.group.domain.SecretSantaGroup;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
public class SecretSantaGroupTO extends RepresentationModel<SecretSantaGroupTO> {

    private String id;

    private String title;

    public static SecretSantaGroupTO fromBusinessModel(SecretSantaGroup secretSantaGroup) {
        return SecretSantaGroupTO.builder()
                .id(secretSantaGroup.getId())
                .title(secretSantaGroup.getTitle())
                .build();
    }
}
