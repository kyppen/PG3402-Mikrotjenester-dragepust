package sofa.microservice.items;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sofa.microservice.items.ItemSetDTO.ItemSet;
import sofa.microservice.items.entity.Item;
import sofa.microservice.items.util.JsonToItemSet;

import java.beans.Transient;
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
    @Transactional
    public void RemoveItem(String ID){
        System.out.println("Delete2");
        itemRepository.removeItemById(Long.parseLong(ID));
        System.out.println("Delete3");
    }
    public void addSet(String setId, String characterId){
        System.out.println("addSet");
        List<Item> itemitemsplaceholderList = new ArrayList<>();
        JsonToItemSet jsonToItemSet = new JsonToItemSet();
        ItemSet itemSet = jsonToItemSet.getItemSet(1);
        System.out.println("Itemset length" + itemSet.getItems().size());
        for(Item setitem : itemSet.getItems()){
            Item item = new Item();
            item.setItemName(setitem.getItemName());
            item.setItemDescription(setitem.getItemDescription());
            item.setCharacterId(characterId);
            itemitemsplaceholderList.add(item);
        }
        itemRepository.saveAll(itemitemsplaceholderList);
    }




}
