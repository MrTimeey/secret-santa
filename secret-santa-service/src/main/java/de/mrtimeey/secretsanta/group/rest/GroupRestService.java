package de.mrtimeey.secretsanta.group.rest;

import de.mrtimeey.secretsanta.group.domain.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupRestService {

    private final GroupService groupService;

    public SecretSantaGroupTO createNewGroup(CreateGroupRequest createGroupRequest) {
        return SecretSantaGroupTO.fromBusinessModel(groupService.createNewGroup(createGroupRequest.getTitle()));
    }
}
