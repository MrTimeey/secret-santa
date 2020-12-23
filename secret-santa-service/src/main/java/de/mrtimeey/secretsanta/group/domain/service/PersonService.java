package de.mrtimeey.secretsanta.group.domain.service;

import com.google.common.collect.Lists;
import de.mrtimeey.secretsanta.group.domain.entity.Person;
import de.mrtimeey.secretsanta.group.domain.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    public boolean uniquePerson(String name, String mail) {
        if (personRepository.findFirstByName(name).isPresent()) {
            return false;
        }
        if (personRepository.findFirstByMail(mail).isPresent()) {
            return false;
        }
        return true;
    }

    public Optional<Person> findFirstByName(String name) {
        return personRepository.findFirstByName(name);
    }

    public Optional<Person> findFirstByMail(String mail) {
        return personRepository.findFirstByMail(mail);
    }

    public List<Person> getParticipants(String groupId) {
        return personRepository.findAllBySecretSantaGroupId(groupId);
    }

    public void saveRandomPairs(String groupId) {
        List<Pair<Person, Person>> randomPairsForGroup = getRandomPairsForGroup(groupId);
        randomPairsForGroup.forEach(pair -> {
            pair.getFirst().setTargetPerson(pair.getSecond().getId());
            this.save(pair.getFirst());
        });

    }

    private List<Pair<Person, Person>> getRandomPairsForGroup(String groupId) {
        List<Person> participants = this.getParticipants(groupId);
        if (participants.isEmpty() || participants.size() == 1) {
            return Lists.newArrayList();
        }

        List<Pair<Person, Person>> result = new ArrayList<>();
        int counter = 0;

        while (result.isEmpty()) {
            counter++;
            if (counter == 50) {
                throw new IllegalStateException("Can not build random pairs!");
            }
            List<Person> targetPersonList = Lists.newArrayList(participants);
            for (Person participant : participants) {
                Person randomPerson = getRandomPerson(targetPersonList, participant);
                if (randomPerson == null) {
                    result = new ArrayList<>();
                    break;
                } else {
                    result.add(Pair.of(participant, randomPerson));
                    targetPersonList.remove(randomPerson);
                }
            }
        }
        return result;
    }

    private Person getRandomPerson(List<Person> targetPersonList, Person... excludedPersonList) {
        Collections.shuffle(targetPersonList);
        return targetPersonList.stream()
                .filter(p -> !Arrays.asList(excludedPersonList).contains(p))
                .findFirst()
                .orElse(null);
    }

}
