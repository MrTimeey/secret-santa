package de.mrtimeey.secretsanta.group.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends MongoRepository<SecretSantaGroup, String> {

}
