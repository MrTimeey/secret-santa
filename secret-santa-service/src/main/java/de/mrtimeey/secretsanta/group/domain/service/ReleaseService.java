package de.mrtimeey.secretsanta.group.domain.service;

import de.mrtimeey.secretsanta.group.domain.entity.Person;
import de.mrtimeey.secretsanta.group.domain.entity.SecretSantaGroup;
import de.mrtimeey.secretsanta.group.domain.mail.MailContentService;
import de.mrtimeey.secretsanta.group.domain.mail.SecretSantaMailService;
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
    private final MailContentService mailContentService;

    public void release(String groupId) {
        SecretSantaGroup secretSantaGroup = getSecretSantaGroup(groupId);
        List<Pair<Person, Person>> pairs = personService.getParticipants(groupId).stream()
                .map(p -> Pair.of(p, personService.findById(p.getTargetPerson()).orElseThrow(IllegalStateException::new)))
                .collect(Collectors.toList());
        pairs.forEach(p -> {
            String mailContent = mailContentService.getMailContent(secretSantaGroup, p.getFirst(), p.getSecond());
            secretSantaMailService.send(p.getFirst(), mailContent);
        });
        secretSantaGroup.setReleased(true);
        groupService.saveGroup(secretSantaGroup);
    }

    public void resendMail(String groupId) {
        SecretSantaGroup secretSantaGroup = getSecretSantaGroup(groupId);
        List<Pair<Person, Person>> pairs = personService.getParticipants(groupId).stream()
                .map(p -> Pair.of(p, personService.findById(p.getTargetPerson()).orElseThrow(IllegalStateException::new)))
                .collect(Collectors.toList());
        pairs.forEach(p -> {
            String mailContent = mailContentService.getRetryMailContent(secretSantaGroup, p.getFirst(), p.getSecond());
            secretSantaMailService.send(p.getFirst(), mailContent);
        });
    }

    private SecretSantaGroup getSecretSantaGroup(String groupId) {
        return groupService.getGroup(groupId).orElseThrow(IllegalStateException::new);
    }
}
