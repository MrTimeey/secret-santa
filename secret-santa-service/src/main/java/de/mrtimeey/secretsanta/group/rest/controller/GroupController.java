package de.mrtimeey.secretsanta.group.rest.controller;

import de.mrtimeey.secretsanta.group.rest.service.GroupRestService;
import de.mrtimeey.secretsanta.group.rest.request.CreateGroupRequest;
import de.mrtimeey.secretsanta.group.rest.response.SecretSantaGroupTO;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
@Validated
public class GroupController {

    private final GroupRestService groupRestService;

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    public SecretSantaGroupTO createGroup(@RequestBody @Valid CreateGroupRequest createGroupRequest) {
        return groupRestService.createNewGroup(createGroupRequest);
    }

    @GetMapping(value = "/{groupId}")
    public ResponseEntity<SecretSantaGroupTO> getGroup(@PathVariable String groupId) {
        return groupRestService.getGroup(groupId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
