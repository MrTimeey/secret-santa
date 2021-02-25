package de.mrtimeey.secretsanta.group.domain.service;

import de.mrtimeey.secretsanta.base.BaseTest;
import de.mrtimeey.secretsanta.group.domain.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersonServiceTest implements BaseTest {

    private PersonService personService;
    private PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        personRepository = mock(PersonRepository.class);
        personService = new PersonService(personRepository);
    }

    private static Stream<Arguments> uniquePersonProvider() {
        return Stream.of(
                Arguments.of(false, false, true),
                Arguments.of(true, false, false),
                Arguments.of(false, true, false),
                Arguments.of(true, true, false)
        );
    }

    @ParameterizedTest
    @MethodSource("uniquePersonProvider")
    void uniquePerson(boolean uniqueMailResponse, boolean uniqueNameResponse, boolean expected) {
        final String secretSantaGroupId = "000";
        final String name = "peter";
        final String mail = "peter@secret-santa.de";
        when(personRepository.existsByMailAndSecretSantaGroupId(mail, secretSantaGroupId)).thenReturn(uniqueMailResponse);
        when(personRepository.existsByNameAndSecretSantaGroupId(name, secretSantaGroupId)).thenReturn(uniqueNameResponse);

        boolean result = personService.uniquePerson(secretSantaGroupId, name, mail);

        assertThat(result).isEqualTo(expected);
    }

}
