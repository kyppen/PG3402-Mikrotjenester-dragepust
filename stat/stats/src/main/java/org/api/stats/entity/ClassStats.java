package org.api.stats.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class ClassStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String className;
    private int baseHP;
    private int baseWillpower;
    private int baseMagic;

    @ElementCollection
    private List<String> skills;

    @ElementCollection
    private List<String> magic;

}
