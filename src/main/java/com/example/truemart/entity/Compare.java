package com.example.truemart.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Compare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "compare",cascade = CascadeType.ALL)
    private List<CompareItem> compareItems;

    public Compare() {
        this.compareItems = new ArrayList<>();
    }

    public void addCompareItem(CompareItem item) {
        item.setCompare(this);
        this.compareItems.add(item);
    }
}
