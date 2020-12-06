package de.mrtimeey.secretsanta.group.rest;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateGroupRequest {

    @NotEmpty
    private String title;

}
