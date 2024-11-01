package sofa.microservice.items;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sofa.microservice.items.entity.Item;

import java.util.ArrayList;
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
    public void addSet(String setId, String characterId){
        System.out.println("addSet()");
        List<Item> itemitemsplaceholderList = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Item item = new Item();
            item.setItemDescription("something");
            item.setItemName("item name");
            item.setCharacterId(characterId);
            itemitemsplaceholderList.add(item);
        }
        itemRepository.saveAll(itemitemsplaceholderList);
    }
    public void setItems(String setId, String characterId){
        itemRepository.deleteItemsBycharacterId(characterId);
        List<Item> itemitemsplaceholderList = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Item item = new Item();
            item.setItemDescription("something");
            item.setItemName("item name");
            item.setCharacterId(characterId);
            itemitemsplaceholderList.add(item);
        }
        itemRepository.saveAll(itemitemsplaceholderList);
    }




}
