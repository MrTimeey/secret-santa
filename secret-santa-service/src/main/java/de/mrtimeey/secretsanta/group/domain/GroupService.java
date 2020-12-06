package de.mrtimeey.secretsanta.group.domain;

import de.mrtimeey.secretsanta.group.domain.GroupRepository;
import de.mrtimeey.secretsanta.group.domain.SecretSantaGroup;
import de.mrtimeey.secretsanta.group.rest.CreateGroupRequest;
import de.mrtimeey.secretsanta.group.rest.SecretSantaGroupTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public SecretSantaGroup createNewGroup(String title) {
        SecretSantaGroup secretSantaGroup = SecretSantaGroup.builder().title(title).build();
        return groupRepository.save(secretSantaGroup);
    }
}
