package de.mrtimeey.secretsanta.group.domain;

import de.mrtimeey.secretsanta.group.domain.entity.Person;
import de.mrtimeey.secretsanta.group.domain.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public void delete(Person person) {
        personRepository.delete(person);
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public Optional<Person> findById(String personId) {
        return personRepository.findById(personId);
    }

    public List<Person> getParticipants(String groupId) {
        return personRepository.findAllBySecretSantaGroupId(groupId);
    }
}
