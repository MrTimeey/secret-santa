package de.mrtimeey.secretsanta.group.rest.controller;

import de.mrtimeey.secretsanta.group.rest.service.GroupRestService;
import de.mrtimeey.secretsanta.group.rest.service.PersonRestService;
import de.mrtimeey.secretsanta.group.rest.request.PersonRequest;
import de.mrtimeey.secretsanta.group.rest.response.PersonTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
@Validated
public class PersonController {

    private final PersonRestService personRestService;
    private final GroupRestService groupRestService;

    @PostMapping
    public ResponseEntity<PersonTO> createPerson(@RequestBody @Valid PersonRequest personRequest) {
        if (!groupRestService.groupExisting(personRequest.getSecretSantaGroup())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(personRestService.create(personRequest));
    }

    @GetMapping(value = "/{personId}")
    public ResponseEntity<PersonTO> getPerson(@PathVariable String personId) {
        return personRestService.getPerson(personId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
