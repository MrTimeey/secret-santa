package de.mrtimeey.secretsanta.group.rest.response;

import de.mrtimeey.secretsanta.group.domain.entity.SecretSantaGroup;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class SecretSantaGroupTO extends RepresentationModel<SecretSantaGroupTO> {

    private String id;

    private String title;

    @Builder.Default
    private List<PersonTO> participants = new ArrayList<>();

    public static SecretSantaGroupTO fromBusinessModel(SecretSantaGroup secretSantaGroup) {
        return SecretSantaGroupTO.builder()
                .id(secretSantaGroup.getId())
                .title(secretSantaGroup.getTitle())
                .build();
    }
}
