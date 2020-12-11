package de.mrtimeey.secretsanta.group.domain.service;

import de.mrtimeey.secretsanta.group.domain.entity.Person;
import de.mrtimeey.secretsanta.group.domain.entity.SecretSantaGroup;
import de.mrtimeey.secretsanta.mail.SecretSantaMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReleaseService {

    private final GroupService groupService;
    private final PersonService personService;
    private final SecretSantaMailService secretSantaMailService;

    public void release(String groupId) {
        SecretSantaGroup secretSantaGroup = groupService.getGroup(groupId).orElseThrow(IllegalStateException::new);
        List<Pair<Person, Person>> pairs = personService.getParticipants(groupId).stream()
                .map(p -> Pair.of(p, personService.findById(p.getTargetPerson()).orElseThrow(IllegalStateException::new)))
                .collect(Collectors.toList());
        pairs.forEach(p -> secretSantaMailService.send(secretSantaGroup, p.getFirst(), p.getSecond()));
        secretSantaGroup.setReleased(true);
        groupService.saveGroup(secretSantaGroup);
    }
}
