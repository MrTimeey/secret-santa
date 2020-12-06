package de.mrtimeey.secretsanta.group.rest.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class PersonRequest {

    @NotEmpty
    private String name;

    @Email
    @NotEmpty
    private String mail;

    @NotEmpty
    private String secretSantaGroup;

}
