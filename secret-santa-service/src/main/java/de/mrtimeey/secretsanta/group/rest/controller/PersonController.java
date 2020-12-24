package de.mrtimeey.secretsanta.group.rest.controller;

import de.mrtimeey.secretsanta.group.rest.request.OnCreate;
import de.mrtimeey.secretsanta.group.rest.request.OnUpdate;
import de.mrtimeey.secretsanta.group.rest.response.PersonTO;
import de.mrtimeey.secretsanta.group.rest.service.GroupRestService;
import de.mrtimeey.secretsanta.group.rest.service.PersonRestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(PersonController.API_URL)
@RequiredArgsConstructor
@Validated
public class PersonController {
    static final String API_URL = "/person";

    private final PersonRestService personRestService;
    private final GroupRestService groupRestService;

    @PostMapping
    @Validated(OnCreate.class)
    public ResponseEntity<PersonTO> createPerson(@RequestBody @Valid PersonTO personTO) {
        String secretSantaGroupId = personTO.getSecretSantaGroupId();
        if (!groupRestService.groupExisting(secretSantaGroupId) || groupRestService.isReleased(secretSantaGroupId)) {
            return ResponseEntity.badRequest().build();
        }
        if (!personRestService.uniquePerson(personTO)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(personRestService.create(personTO));
    }

    @GetMapping(value = "/{personId}")
    public ResponseEntity<PersonTO> getPerson(@PathVariable String personId) {
        return personRestService.getPerson(personId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{personId}")
    public ResponseEntity<Void> deletePerson(@PathVariable String personId) {
        personRestService.deletePerson(personId);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Validated(OnUpdate.class)
    public ResponseEntity<PersonTO> updatePerson(@RequestBody PersonTO personTO) {
        return personRestService.updatePerson(personTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }
}
