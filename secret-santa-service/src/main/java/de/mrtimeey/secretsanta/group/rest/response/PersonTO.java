package de.mrtimeey.secretsanta.group.rest.response;

import de.mrtimeey.secretsanta.group.domain.entity.Person;
import de.mrtimeey.secretsanta.group.rest.request.OnCreate;
import de.mrtimeey.secretsanta.group.rest.request.OnUpdate;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import static de.mrtimeey.secretsanta.group.rest.utils.UpdateUtils.processIfNotNull;

@Data
@Builder
public class PersonTO extends RepresentationModel<PersonTO> {

    @NotNull(groups = OnUpdate.class)
    @Null(groups = OnCreate.class)
    private String id;

    @NotEmpty(groups = OnCreate.class)
    @Size(min = 3, max = 100)
    private String name;

    @Email(groups = OnCreate.class)
    @NotEmpty(groups = OnCreate.class)
    @Size(min = 3, max = 100)
    private String mail;

    @NotEmpty(groups = OnCreate.class)
    @Null(groups = OnUpdate.class)
    private String secretSantaGroupId;

    private boolean mailSend;

    public static PersonTO fromBusinessModel(Person person) {
        return PersonTO.builder()
                .id(person.getId())
                .secretSantaGroupId(person.getSecretSantaGroupId())
                .name(person.getName())
                .mail(person.getMail())
                .mailSend(person.isMailSend())
                .build();
    }

    public Person writeValuesToBusinessModel(Person personToModify) {
        processIfNotNull(name, personToModify::setName);
        processIfNotNull(mail, personToModify::setMail);
        return personToModify;
    }
}
