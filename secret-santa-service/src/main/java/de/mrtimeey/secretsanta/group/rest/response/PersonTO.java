package de.mrtimeey.secretsanta.group.rest.response;

import de.mrtimeey.secretsanta.group.domain.entity.Person;
import de.mrtimeey.secretsanta.group.domain.entity.SecretSantaGroup;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
public class PersonTO extends RepresentationModel<PersonTO> {

    private String id;
    private String name;
    private String mail;
    private String secretSantaGroupId;

    public static PersonTO fromBusinessModel(Person person) {
        return PersonTO.builder()
                .id(person.getId())
                .secretSantaGroupId(person.getSecretSantaGroupId())
                .name(person.getName())
                .mail(person.getMail())
                .build();
    }
}
