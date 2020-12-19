package de.mrtimeey.secretsanta.group.rest.response;

import de.mrtimeey.secretsanta.group.domain.entity.SecretSantaGroup;
import de.mrtimeey.secretsanta.group.rest.request.OnCreate;
import de.mrtimeey.secretsanta.group.rest.request.OnUpdate;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class SecretSantaGroupTO extends RepresentationModel<SecretSantaGroupTO> {

    @NotNull(groups = OnUpdate.class)
    @Null(groups = OnCreate.class)
    private String id;

    @NotNull(groups = OnCreate.class)
    private String title;

    @NotNull(groups = OnUpdate.class)
    @Null(groups = OnCreate.class)
    private Boolean released;

    @Null(groups = OnCreate.class)
    @Builder.Default
    private List<PersonTO> participants = new ArrayList<>();

    public static SecretSantaGroupTO fromBusinessModel(SecretSantaGroup secretSantaGroup) {
        return SecretSantaGroupTO.builder()
                .id(secretSantaGroup.getId())
                .title(secretSantaGroup.getTitle())
                .released(secretSantaGroup.isReleased())
                .build();
    }
}
