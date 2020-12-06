package de.mrtimeey.secretsanta.group.domain.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
public class Person {

    @Id
    private String id;

    private String secretSantaGroupId;

    private String name;

    private String mail;
}
