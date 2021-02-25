package de.mrtimeey.secretsanta.base;

import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static de.mrtimeey.secretsanta.base.Constants.INTEGRATION_TEST;

@Tag(INTEGRATION_TEST)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.mail.username=mock@it-test.de"})
@Testcontainers
public abstract class AbstractBaseIT {


    @DynamicPropertySource
    static void mongoDBProperties(DynamicPropertyRegistry registry) {
        // MongoDBContainer will be shared between test methods of each test
        MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.2.0"));
        mongoDBContainer.start();
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }


    @MockBean
    private JavaMailSender mailSender;
}
