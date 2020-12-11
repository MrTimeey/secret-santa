package de.mrtimeey.secretsanta.mail;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
public class MailTemplateService {

    private Configuration freemarkerConfig;
    private static final String TEMPLATE_DIRECTORY = "templates/";

    public String processTemplate(String templateName, Map<String, Object> data) {
        Template template = loadTemplate(templateName, TEMPLATE_DIRECTORY + templateName + ".ftl");
        try (StringWriter writer = new StringWriter()) {
            template.process(data, writer);
            return writer.toString();
        } catch (Exception e) {
            log.error("Failed processing print data template!", e);
            throw new IllegalStateException("Failed processing print data template!", e);
        }
    }

    private Template loadTemplate(String templateName, String templatePath) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(templatePath)) {
            if (is == null) {
                throw new IllegalStateException("Failed loading freemarker template!");
            }
            String templateContent = new String(is.readAllBytes());
            ((StringTemplateLoader) freemarkerConfig.getTemplateLoader()).putTemplate(templateName, templateContent);
            return freemarkerConfig.getTemplate(templateName);
        } catch (IOException e) {
            log.error("Failed loading print data template!", e);
            throw new IllegalStateException(e);
        }
    }
}
