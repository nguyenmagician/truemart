package com.example.truemart.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class WishlistItemDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String url;
    private long productId;
    private String product_name;
    private String img_url;
    private boolean isStocked;
    private String price;
    private long pns_id;
    private float discount;
    private String price_after_discount;
    private int amount;


    public WishlistItemDTO(long productId, String product_name, String img_url, boolean isStocked, String price,long id,float discount,String price_after_discount,int amount) {
        this.productId = productId;
        this.url = "/product?id="+productId;
        this.product_name = product_name;
        this.img_url = img_url;
        this.isStocked = isStocked;
        this.price = price;
        this.pns_id = id;
        this.discount = discount;
        this.price_after_discount = price_after_discount;
        this.amount = amount;
    }

    public WishlistItemDTO() {
    }
}
