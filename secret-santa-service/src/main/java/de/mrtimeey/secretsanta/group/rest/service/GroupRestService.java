package de.mrtimeey.secretsanta.group.rest.service;

import com.google.common.base.Strings;
import de.mrtimeey.secretsanta.group.domain.entity.Person;
import de.mrtimeey.secretsanta.group.domain.entity.SecretSantaGroup;
import de.mrtimeey.secretsanta.group.domain.service.GroupService;
import de.mrtimeey.secretsanta.group.domain.service.PersonService;
import de.mrtimeey.secretsanta.group.rest.controller.PersonController;
import de.mrtimeey.secretsanta.group.rest.request.CreateGroupRequest;
import de.mrtimeey.secretsanta.group.rest.response.PersonTO;
import de.mrtimeey.secretsanta.group.rest.response.SecretSantaGroupTO;
import de.mrtimeey.secretsanta.group.rest.utils.GroupHalUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
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
    private final GroupHalUtils groupHalUtils;


    public SecretSantaGroupTO createNewGroup(CreateGroupRequest createGroupRequest) {
        SecretSantaGroup secretSantaGroup = groupService.createNewGroup(createGroupRequest.getTitle());
        SecretSantaGroupTO secretSantaGroupTO = SecretSantaGroupTO.fromBusinessModel(secretSantaGroup);
        return groupHalUtils.addHalLinks(secretSantaGroupTO);
    }

    public void createSecretSantaPairs(String groupId) {
        personService.saveRandomPairs(groupId);
    }

    public List<Pair<String, String>> getPairs(String groupId) {
        List<Person> participants = personService.getParticipants(groupId);
        return participants.stream()
                .filter(p -> !Strings.isNullOrEmpty(p.getTargetPerson()))
                .map(p -> Pair.of(p.getName(), personService.findById(p.getTargetPerson()).map(Person::getName).orElse("")))
                .collect(Collectors.toList());
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
                .map(groupHalUtils::addHalLinks);
    }

    public boolean groupExisting(String groupId) {
        return groupService.getGroup(groupId).isPresent();
    }

    public void delete(String groupId) {
        groupService.getGroup(groupId)
                .map(g -> deleteParticipantsInGroup(groupId, g))
                .ifPresent(groupService::delete);
    }

    private SecretSantaGroup deleteParticipantsInGroup(String groupId, SecretSantaGroup secretSantaGroup) {
        personService.getParticipants(groupId)
                .forEach(personService::delete);
        return secretSantaGroup;
    }

}
