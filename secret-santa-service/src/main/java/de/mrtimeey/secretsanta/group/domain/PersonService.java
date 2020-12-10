package de.mrtimeey.secretsanta.group.domain;

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

    public List<Person> getParticipants(String groupId) {
        return personRepository.findAllBySecretSantaGroupId(groupId);
    }

    public List<Pair<Person, Person>> getRandomPairsForGroup(String groupId) {
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
