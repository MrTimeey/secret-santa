package de.mrtimeey.secretsanta.group.rest.service;

import de.mrtimeey.secretsanta.group.domain.PersonService;
import de.mrtimeey.secretsanta.group.domain.entity.Person;
import de.mrtimeey.secretsanta.group.rest.controller.GroupController;
import de.mrtimeey.secretsanta.group.rest.controller.PersonController;
import de.mrtimeey.secretsanta.group.rest.request.PersonRequest;
import de.mrtimeey.secretsanta.group.rest.response.PersonTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class PersonRestService {

    private final PersonService personService;

    public PersonTO create(PersonRequest personRequest) {

        Person tempPerson = Person.builder()
                .name(personRequest.getName())
                .mail(personRequest.getMail())
                .secretSantaGroupId(personRequest.getSecretSantaGroup())
                .build();
        PersonTO person = PersonTO.fromBusinessModel(personService.create(tempPerson));
        person.add(linkTo(methodOn(PersonController.class).getPerson(person.getId())).withSelfRel());
        person.add(linkTo(methodOn(GroupController.class).getGroup(personRequest.getSecretSantaGroup())).withRel("group"));
        return person;
    }

    public Optional<PersonTO> getPerson(String personId) {
        return personService.findById(personId)
                .map(PersonTO::fromBusinessModel)
                .map(p -> p.add(linkTo(methodOn(PersonController.class).getPerson(p.getId())).withSelfRel()))
                .map(p -> p.add(linkTo(methodOn(GroupController.class).getGroup(p.getSecretSantaGroupId())).withRel("group")));
    }

}
