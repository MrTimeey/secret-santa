package de.mrtimeey.secretsanta.group.domain.mail;

import de.mrtimeey.secretsanta.group.domain.entity.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public boolean send(Person person, String htmlMsg, String subject) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            try {
                helper.setText(htmlMsg, true);
                helper.setTo(person.getMail());
                helper.setSubject(subject);
                helper.setFrom("no-reply@the-secret-santa.de");
            } catch (MessagingException e) {
                log.error("Failed sending mail to '{}'!", person, e);
            }
            mailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            log.error("Failed sending mail to '{}' with address '{}'!", person.getName(), person.getMail(), e);
            return false;
        }
    }
}
