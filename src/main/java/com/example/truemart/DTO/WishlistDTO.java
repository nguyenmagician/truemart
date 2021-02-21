package com.example.truemart.DTO;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class WishlistDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<WishlistItemDTO> wishlistItemDTOS;

    public WishlistDTO() {
        this.wishlistItemDTOS = new ArrayList<>();
    }
}
