package de.mrtimeey.secretsanta.group.domain.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@Document
public class Person {

    @Id
    private String id;

    @NotEmpty
    private String secretSantaGroupId;

    @NotEmpty
    private String name;

    @Email
    private String mail;

    private String targetPerson;
}
