package technology.scholz.bingo.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import technology.scholz.bingo.domain.Spiel;

import java.util.Optional;
import java.util.UUID;

@Repository
@EnableScan
public interface SpielRespository extends CrudRepository<Spiel, UUID> {

    Optional<Spiel> findById(UUID spielId);

    Optional<Spiel> findByName(String spielName);
}

