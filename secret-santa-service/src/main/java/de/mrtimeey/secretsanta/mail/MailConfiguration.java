package de.mrtimeey.secretsanta.mail;

import freemarker.cache.StringTemplateLoader;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class MailConfiguration {

    @Bean
    public Configuration freemarkerConfig() {
        freemarker.template.Configuration freemarkerConfig = new Configuration(Configuration.VERSION_2_3_30);
        freemarkerConfig.setTagSyntax(Configuration.ANGLE_BRACKET_TAG_SYNTAX);
        freemarkerConfig.setDefaultEncoding("UTF-8");
        freemarkerConfig.setNumberFormat("computer");
        freemarkerConfig.setObjectWrapper(new BeansWrapperBuilder(Configuration.VERSION_2_3_30).build());
        freemarkerConfig.setTemplateLoader(new StringTemplateLoader());
        return freemarkerConfig;
    }

}
