package sofa.microservice.items.ItemSetDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sofa.microservice.items.entity.Item;

import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemSet {
    private String setName;
    private List<Item> items;

    @Override
    public String toString() {
        return "ItemSet{setName='" + setName + "', items=" + items + "}";
    }
}
