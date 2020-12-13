package de.mrtimeey.secretsanta.group.domain.mail;

import de.mrtimeey.secretsanta.group.domain.entity.Person;
import de.mrtimeey.secretsanta.group.domain.entity.SecretSantaGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailContentService {

    private final MailTemplateService mailTemplateService;

    public String getMailContent(SecretSantaGroup secretSantaGroup, Person person, Person targetPerson) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", person.getName());
        data.put("targetPersonName", targetPerson.getName());
        data.put("targetPersonMail", targetPerson.getMail());
        data.put("secretSantaGroupTitle", secretSantaGroup.getTitle());
        return mailTemplateService.processTemplate("mail", data);
    }
}
