package de.mrtimeey.secretsanta.group.rest.controller;

import de.mrtimeey.secretsanta.base.AbstractBaseIT;
import de.mrtimeey.secretsanta.base.TestUtils;
import de.mrtimeey.secretsanta.group.domain.entity.Person;
import de.mrtimeey.secretsanta.group.domain.entity.SecretSantaGroup;
import de.mrtimeey.secretsanta.group.domain.repository.GroupRepository;
import de.mrtimeey.secretsanta.group.domain.repository.PersonRepository;
import de.mrtimeey.secretsanta.group.rest.response.PersonTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class PersonControllerIT extends AbstractBaseIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private GroupRepository groupRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        personRepository.deleteAll();
        groupRepository.deleteAll();
    }

    @Test
    void createPersonWithNonExistingGroup() throws Exception {
        PersonTO personTO = PersonTO.builder().secretSantaGroupId("wrongId").name("peter").mail("peter@secret-santa.de").build();

        mockMvc.perform(post(PersonController.API_URL)
                .content(TestUtils.getObjectAsJsonString(personTO))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

        assertThat(personRepository.count()).isEqualTo(0L);
    }

    @Test
    void createPersonWithReleasedGroup() throws Exception {
        SecretSantaGroup secretSantaGroup = groupRepository.save(SecretSantaGroup.builder().title("superGroup").released(true).build());
        PersonTO personTO = PersonTO.builder().secretSantaGroupId(secretSantaGroup.getId()).name("peter").mail("peter@secret-santa.de").build();

        mockMvc.perform(post(PersonController.API_URL)
                .content(TestUtils.getObjectAsJsonString(personTO))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

        assertThat(personRepository.count()).isEqualTo(0L);
        assertThat(groupRepository.count()).isEqualTo(1L);
    }

    @Test
    void createPerson() throws Exception {
        SecretSantaGroup secretSantaGroup = groupRepository.save(SecretSantaGroup.builder().title("superGroup").build());
        PersonTO personTO = PersonTO.builder().secretSantaGroupId(secretSantaGroup.getId()).name("peter").mail("peter@secret-santa.de").build();

        MvcResult mvcResult = mockMvc.perform(post(PersonController.API_URL)
                .content(TestUtils.getObjectAsJsonString(personTO))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        assertThat(personRepository.count()).isEqualTo(1L);
        assertThat(groupRepository.count()).isEqualTo(1L);
        PersonTO resultPerson = TestUtils.getObjectFromJsonString(mvcResult.getResponse().getContentAsString(), PersonTO.class);
        assertThat(resultPerson.getName()).isEqualTo(personTO.getName());
        assertThat(resultPerson.getMail()).isEqualTo(personTO.getMail());
        assertThat(resultPerson.getSecretSantaGroupId()).isEqualTo(personTO.getSecretSantaGroupId());
    }

    private static Stream<Arguments> uniqueConstraintsProvider() {
        return Stream.of(
                Arguments.of("peter", "manfred@secret-santa.de", status().isBadRequest(), 1),
                Arguments.of("manfred", "peter@secret-santa.de", status().isBadRequest(), 1),
                Arguments.of("manfred", "manfred@secret-santa.de", status().isCreated(), 2));
    }

    @ParameterizedTest
    @MethodSource("uniqueConstraintsProvider")
    void uniqueConstraints(String name, String email, ResultMatcher result, long personCount) throws Exception {
        SecretSantaGroup secretSantaGroup = groupRepository.save(SecretSantaGroup.builder().title("superGroup").build());
        Person person = personRepository.save(Person.builder().secretSantaGroupId(secretSantaGroup.getId()).name("peter").mail("peter@secret-santa.de").build());
        PersonTO personTO = PersonTO.builder().secretSantaGroupId(secretSantaGroup.getId()).name(name).mail(email).build();

        mockMvc.perform(post(PersonController.API_URL)
                .content(TestUtils.getObjectAsJsonString(personTO))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(result);

        assertThat(personRepository.count()).isEqualTo(personCount);
        assertThat(groupRepository.count()).isEqualTo(1L);
    }
}
