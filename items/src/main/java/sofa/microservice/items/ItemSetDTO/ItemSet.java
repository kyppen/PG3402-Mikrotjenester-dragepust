package sofa.microservice.items.ItemSetDTO;

import lombok.*;
import sofa.microservice.items.entity.Item;

import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemSet {
    private String setName;
    private List<Item> items;

}
