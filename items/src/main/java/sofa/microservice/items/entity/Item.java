package sofa.microservice.items.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "ITEMS")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String characterId;
    private String itemName;
    private String itemDescription;

    public void printString(){
        System.out.println(characterId + "\t" + itemName + "\t" + itemDescription);
    }
}
