package de.mrtimeey.secretsanta.group.rest.service;

import de.mrtimeey.secretsanta.group.domain.GroupService;
import de.mrtimeey.secretsanta.group.domain.PersonService;
import de.mrtimeey.secretsanta.group.domain.entity.SecretSantaGroup;
import de.mrtimeey.secretsanta.group.rest.controller.GroupController;
import de.mrtimeey.secretsanta.group.rest.controller.PersonController;
import de.mrtimeey.secretsanta.group.rest.request.CreateGroupRequest;
import de.mrtimeey.secretsanta.group.rest.response.PersonTO;
import de.mrtimeey.secretsanta.group.rest.response.SecretSantaGroupTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class GroupRestService {

    private final GroupService groupService;
    private final PersonService personService;

    public SecretSantaGroupTO createNewGroup(CreateGroupRequest createGroupRequest) {
        SecretSantaGroup secretSantaGroup = groupService.createNewGroup(createGroupRequest.getTitle());
        SecretSantaGroupTO secretSantaGroupTO = SecretSantaGroupTO.fromBusinessModel(secretSantaGroup);
        secretSantaGroupTO.add(linkTo(methodOn(GroupController.class).getGroup(secretSantaGroupTO.getId())).withSelfRel());
        return secretSantaGroupTO;
    }

    public Optional<SecretSantaGroupTO> getGroup(String groupId) {
        return groupService.getGroup(groupId)
                .map(SecretSantaGroupTO::fromBusinessModel)
                .map(group -> {
                    List<PersonTO> participants = personService.getParticipants(groupId).stream()
                            .map(PersonTO::fromBusinessModel)
                            .map(p -> p.add(linkTo(methodOn(PersonController.class).getPerson(p.getId())).withSelfRel()))
                            .collect(Collectors.toList());
                    group.setParticipants(participants);
                    return group;
                })
                .map(group -> {
                    group.add(linkTo(methodOn(GroupController.class).getGroup(group.getId())).withSelfRel());
                    return group;
                });
    }

    public boolean groupExisting(String groupId) {
        return groupService.getGroup(groupId).isPresent();
    }
}
