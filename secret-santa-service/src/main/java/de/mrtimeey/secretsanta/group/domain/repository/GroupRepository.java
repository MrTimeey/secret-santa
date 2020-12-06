package de.mrtimeey.secretsanta.group.domain.repository;

import de.mrtimeey.secretsanta.group.domain.entity.SecretSantaGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends MongoRepository<SecretSantaGroup, String> {

}
