package sofa.microservice.items;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sofa.microservice.items.entity.Item;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {


    private final ItemService itemService;

    // Må ta in character id også ut
    @PostMapping("/add")
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        System.out.println("Add item");
        itemService.addItem(item);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }
    @GetMapping("/{characterId}")
    public ResponseEntity<List<Item>> getItems(@PathVariable String characterId) {
        System.out.println("Get item");
        List<Item> characterItems = itemService.findItemsByCharacterId(characterId);
        System.out.println(characterItems.size());
        return new ResponseEntity<>(characterItems, HttpStatus.OK);
    }
    @GetMapping("/All")
    public ResponseEntity<List<Item>> getAllItems() {
        System.out.println("Get items");
        return new ResponseEntity<>(itemService.getAllItems(), HttpStatus.OK);
    }
    @PostMapping("/set/{characterId}/{setId}")
    public ResponseEntity<Boolean> updateItem(@PathVariable String setId, @PathVariable String characterId) {
        System.out.println("AddItemSet");
        System.out.println(setId);
        System.out.println(characterId);
        itemService.addSet(setId, characterId);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }
    @PutMapping("/set/{characterId}/{setId}")
    public ResponseEntity<Boolean> updateItemSet(@PathVariable String setId, @PathVariable String characterId) {
        System.out.println("AddItemSet");

    }



}
