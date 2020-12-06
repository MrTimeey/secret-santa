package de.mrtimeey.secretsanta.group.domain.repository;

import de.mrtimeey.secretsanta.group.domain.entity.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends MongoRepository<Person, String> {

    List<Person> findAllBySecretSantaGroupId(String secretSantaGroupId);
}
