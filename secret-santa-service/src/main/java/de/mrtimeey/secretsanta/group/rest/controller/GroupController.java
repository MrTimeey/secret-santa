package de.mrtimeey.secretsanta.group.rest.controller;

import de.mrtimeey.secretsanta.group.rest.request.CreateGroupRequest;
import de.mrtimeey.secretsanta.group.rest.response.SecretSantaGroupTO;
import de.mrtimeey.secretsanta.group.rest.service.GroupRestService;
import de.mrtimeey.secretsanta.group.rest.service.ReleaseRestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
@Validated
public class GroupController {

    private final GroupRestService groupRestService;
    private final ReleaseRestService releaseRestService;

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED)
    @ResponseBody
    public SecretSantaGroupTO createGroup(@Valid @RequestBody CreateGroupRequest createGroupRequest) {
            return groupRestService.createNewGroup(createGroupRequest);

    }

    @GetMapping(value = "/{groupId}")
    public ResponseEntity<SecretSantaGroupTO> getGroup(@PathVariable String groupId) {
        return groupRestService.getGroup(groupId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/{groupId}/release")
    public ResponseEntity<Void> releaseGroup(@PathVariable String groupId) {
        releaseRestService.release(groupId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/{groupId}/pairs")
    public List<Pair<String, String>> getPairsFromGroup(@PathVariable String groupId) {
        return groupRestService.getPairs(groupId);
    }

    @PostMapping(value = "/{groupId}/shuffle")
    public ResponseEntity<Void> shuffleGroup(@PathVariable String groupId) {
        if (groupRestService.isReleased(groupId)) {
            return ResponseEntity.badRequest().build();
        }
        groupRestService.createSecretSantaPairs(groupId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable String groupId) {
        groupRestService.delete(groupId);
        return ResponseEntity.ok().build();
    }
}
