package sofa.microservice.items;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sofa.microservice.items.ItemSetDTO.ItemSet;
import sofa.microservice.items.entity.Item;
import sofa.microservice.items.util.JsonToItemSet;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    private final ItemRepository itemRepository;

    public void addItem(Item item){
        item.printString();
        itemRepository.save(item);
        log.info("New item added to characterid: " + item.getCharacterId());
    }
    public List<Item> findItemsByCharacterId(String id){
        log.info("Return items by characterid: " + id);
        return itemRepository.findItemsByCharacterId(id);
    }
    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }
    @Transactional
    public void RemoveItem(String ID){
        itemRepository.removeItemById(Long.parseLong(ID));
        log.info("Item with id: " + ID + " removed");
    }
    @Transactional
    public void RemoveAllCharacterItems(String characterId){
        itemRepository.deleteItemsByCharacterId(characterId);
    }
    public void addSet(String setId, String characterId){
        List<Item> itemitemsplaceholderList = new ArrayList<>();
        JsonToItemSet jsonToItemSet = new JsonToItemSet();
        ItemSet itemSet = jsonToItemSet.getItemSet(Integer.parseInt(setId));
        log.info("Set gotten successfully! {}", itemSet.toString());
        for(Item setitem : itemSet.getItems()){
            Item item = new Item();
            item.setItemName(setitem.getItemName());
            item.setItemDescription(setitem.getItemDescription());
            item.setCharacterId(characterId);
            itemitemsplaceholderList.add(item);
        }
        itemRepository.saveAll(itemitemsplaceholderList);
        log.info("{} number of items saved", itemitemsplaceholderList.size());
    }




}
