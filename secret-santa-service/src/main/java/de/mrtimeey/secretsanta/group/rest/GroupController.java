package de.mrtimeey.secretsanta.group.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
@Validated
public class GroupController {

    private final GroupRestService groupRestService;

    @PostMapping
    public SecretSantaGroupTO createGroup(@RequestBody @Valid CreateGroupRequest createGroupRequest) {
        return groupRestService.createNewGroup(createGroupRequest);
    }

}
