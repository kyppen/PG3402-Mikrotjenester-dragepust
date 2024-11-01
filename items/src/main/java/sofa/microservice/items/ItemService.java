package sofa.microservice.items;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sofa.microservice.items.entity.Item;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    public void addItem(Item item){
        item.printString();
        itemRepository.save(item);
        System.out.println("saved");

    }
    public List<Item> findItemsByCharacterId(String id){
        System.out.println("service");
        return itemRepository.findItemsBycharacterId(id);
    }
    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }



}
