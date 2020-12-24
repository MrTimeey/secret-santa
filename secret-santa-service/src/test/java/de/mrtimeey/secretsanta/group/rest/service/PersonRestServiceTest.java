package de.mrtimeey.secretsanta.group.rest.service;

import de.mrtimeey.secretsanta.group.domain.service.PersonService;
import de.mrtimeey.secretsanta.group.rest.response.PersonTO;
import de.mrtimeey.secretsanta.group.rest.utils.PersonHalUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PersonRestServiceTest {

    private PersonRestService personRestService;
    private PersonService personService;
    private PersonHalUtils personHalUtils;

    @BeforeEach
    void setUp() {
        personService = mock(PersonService.class);
        personHalUtils = mock(PersonHalUtils.class);
        personRestService = new PersonRestService(personService,personHalUtils);
    }

    @ParameterizedTest
    @ValueSource(strings = { "true", "false" })
    void uniquePerson(boolean expected) {
        final String secretSantaGroupId = "000";
        final String name = "peter";
        final String mail = "peter@secret-santa.de";
        PersonTO personTo = mock(PersonTO.class);
        when(personTo.getSecretSantaGroupId()).thenReturn(secretSantaGroupId);
        when(personTo.getName()).thenReturn(name);
        when(personTo.getMail()).thenReturn(mail);
        when(personService.uniquePerson(secretSantaGroupId, name, mail)).thenReturn(expected);

        boolean result = personRestService.uniquePerson(personTo);

        assertThat(result).isEqualTo(expected);
    }
}
