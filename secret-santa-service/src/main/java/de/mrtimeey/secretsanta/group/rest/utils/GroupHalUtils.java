package de.mrtimeey.secretsanta.group.rest.utils;

import de.mrtimeey.secretsanta.group.rest.controller.GroupController;
import de.mrtimeey.secretsanta.group.rest.response.Relation;
import de.mrtimeey.secretsanta.group.rest.response.SecretSantaGroupTO;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GroupHalUtils {

    public SecretSantaGroupTO addHalLinks(SecretSantaGroupTO group) {
        group.add(linkTo(methodOn(GroupController.class).getGroup(group.getId())).withSelfRel());
        group.add(linkTo(methodOn(GroupController.class).getPairsFromGroup(group.getId())).withRel(Relation.GET_PAIRS));
        group.add(linkTo(methodOn(GroupController.class).deleteGroup(group.getId())).withRel(Relation.DELETE_GROUP));
        group.add(linkTo(methodOn(GroupController.class).releaseGroup(group.getId())).withRel(Relation.RELEASE_GROUP));
        return group;
    }
}
