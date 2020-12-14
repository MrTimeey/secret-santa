package de.mrtimeey.secretsanta.group.domain.mail;

import de.mrtimeey.secretsanta.group.domain.entity.Person;
import de.mrtimeey.secretsanta.group.domain.entity.SecretSantaGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecretSantaMailService {

    private final JavaMailSender mailSender;
    private final MailContentService mailContentService;

    @Value("${spring.mail.username}")
    private String username;

    public void send(SecretSantaGroup secretSantaGroup, Person person, Person targetPerson) {
        try {


        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = mailContentService.getMailContent(secretSantaGroup, person, targetPerson);
        try {
            helper.setText(htmlMsg, true);
            helper.setTo(person.getMail());
            helper.setSubject("Wichtel-Gruppe: Dein geheimer Wichtel-Partner!");
            helper.setFrom(username);
        } catch (MessagingException e) {
            log.error("Failed sending mail to '{}'!", person, e);
        }
        mailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("Failed sending mail to '{}' with address '{}'!", person.getName(), person.getMail(), e);
        }
    }
}
