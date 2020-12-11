package de.mrtimeey.secretsanta.group.rest.utils;

import de.mrtimeey.secretsanta.group.rest.controller.GroupController;
import de.mrtimeey.secretsanta.group.rest.controller.PersonController;
import de.mrtimeey.secretsanta.group.rest.response.PersonTO;
import de.mrtimeey.secretsanta.group.rest.response.Relation;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PersonHalUtils {

    public PersonTO addLinks(PersonTO person) {
        person.add(linkTo(methodOn(PersonController.class).getPerson(person.getId())).withSelfRel());
        person.add(linkTo(methodOn(GroupController.class).getGroup(person.getSecretSantaGroupId())).withRel(Relation.GROUP));
        return person;
    }
}
