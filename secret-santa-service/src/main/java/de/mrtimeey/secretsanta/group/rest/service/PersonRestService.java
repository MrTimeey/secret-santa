package de.mrtimeey.secretsanta.group.rest.service;

import de.mrtimeey.secretsanta.group.domain.entity.Person;
import de.mrtimeey.secretsanta.group.domain.service.PersonService;
import de.mrtimeey.secretsanta.group.rest.response.PersonTO;
import de.mrtimeey.secretsanta.group.rest.utils.PersonHalUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonRestService {

    private final PersonService personService;
    private final PersonHalUtils personHalUtils;

    public PersonTO create(PersonTO personTO) {
        Person tempPerson = Person.builder()
                .name(personTO.getName())
                .mail(personTO.getMail())
                .secretSantaGroupId(personTO.getSecretSantaGroupId())
                .build();
        PersonTO person = PersonTO.fromBusinessModel(personService.save(tempPerson));
        return personHalUtils.addLinks(person);
    }

    public Optional<PersonTO> getPerson(String personId) {
        return personService.findById(personId)
                .map(PersonTO::fromBusinessModel)
                .map(personHalUtils::addLinks);
    }

    public Optional<PersonTO> updatePerson(PersonTO personTO) {
        return personService.findById(personTO.getId())
                .map(personTO::writeValuesToBusinessModel)
                .map(personService::save)
                .map(PersonTO::fromBusinessModel)
                .map(personHalUtils::addLinks);
    }

    public void deletePerson(String personId) {
        personService.findById(personId)
                .ifPresent(personService::delete);
    }
}
