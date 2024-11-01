package sofa.microservice.items;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sofa.microservice.items.entity.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findItemsBycharacterId(String characterId);
    void deleteItemsBycharacterId(String characterId);
}
