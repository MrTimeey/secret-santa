package de.mrtimeey.secretsanta.group.rest.service;

import de.mrtimeey.secretsanta.group.domain.service.ReleaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReleaseRestService {

    private final ReleaseService releaseService;

    public void release(String groupId) {
        releaseService.release(groupId);
    }

    public void resendMail(String groupId) {
        releaseService.resendMail(groupId);
    }
}
