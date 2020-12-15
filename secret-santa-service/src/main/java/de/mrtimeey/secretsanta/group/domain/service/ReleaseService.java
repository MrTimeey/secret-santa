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

    public static final String START_SUBJECT = "Wichtel-Gruppe: Dein geheimer Wichtel-Partner!";
    public static final String CANCEL_SUBJECT = "Wichtel-Gruppe: Abbruch!";
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
            secretSantaMailService.send(p.getFirst(), mailContent, START_SUBJECT);
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
            secretSantaMailService.send(p.getFirst(), mailContent, START_SUBJECT);
        });
    }

    public void cancelGroup(String groupId) {
        SecretSantaGroup secretSantaGroup = getSecretSantaGroup(groupId);
        personService.getParticipants(groupId).forEach(p -> {
            String mailContent = mailContentService.getCancelMailContent(secretSantaGroup, p);
            secretSantaMailService.send(p, mailContent, CANCEL_SUBJECT);
        });
        secretSantaGroup.setReleased(false);
        groupService.saveGroup(secretSantaGroup);
    }

    private SecretSantaGroup getSecretSantaGroup(String groupId) {
        return groupService.getGroup(groupId).orElseThrow(IllegalStateException::new);
    }

}
