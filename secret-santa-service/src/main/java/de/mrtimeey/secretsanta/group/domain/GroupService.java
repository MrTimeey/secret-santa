package de.mrtimeey.secretsanta.group.domain;

import de.mrtimeey.secretsanta.group.domain.entity.SecretSantaGroup;
import de.mrtimeey.secretsanta.group.domain.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public SecretSantaGroup createNewGroup(String title) {
        SecretSantaGroup secretSantaGroup = SecretSantaGroup.builder().title(title).build();
        return groupRepository.save(secretSantaGroup);
    }

    public Optional<SecretSantaGroup> getGroup(String groupId) {
        return groupRepository.findById(groupId);
    }

    public void delete(SecretSantaGroup group) {
        groupRepository.delete(group);
    }


}
