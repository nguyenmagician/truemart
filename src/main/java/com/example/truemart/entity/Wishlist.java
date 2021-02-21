package com.example.truemart.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "wishlist",cascade = CascadeType.ALL)
    private List<WishlistItem> wishlistItems;

    public Wishlist() {
        this.wishlistItems = new ArrayList<>();
    }

    public void addWishlistItem(WishlistItem item) {
        item.setWishlist(this);
        this.wishlistItems.add(item);
    }

}
