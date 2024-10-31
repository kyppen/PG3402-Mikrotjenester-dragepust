package sofa.microservice.playerCharacter;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sofa.microservice.playerCharacter.entity.PlayerCharacter;

import java.util.List;

@Repository
public interface PlayerCharacterRepository extends JpaRepository<PlayerCharacter, Long> {

    List<PlayerCharacter> findAllByUserId (String userId);
}
