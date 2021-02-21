package com.example.truemart.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SingleProduct {
    private long id;
    private String product_url;
    private String name;
    private String img1_url;
    private String img2_url;
    private String price;
    private String price_after_discount;
    private String discount;
    private boolean isDiscounted;
    private boolean isNew;
    private String cart_url;
    private String compare_url;
    private String wishlist_url;
    private String short_detail;


    public SingleProduct(long id, String name, String img1_url, String img2_url, String price, String price_after_discount, String discount, boolean isDiscounted, boolean isNew,String short_detail) {
        this.id = id;
        this.name = name;
        this.img1_url = img1_url;
        this.img2_url = img2_url;
        this.price = price;
        this.price_after_discount = price_after_discount;
        this.discount = discount;
        this.isDiscounted = isDiscounted;
        this.isNew = isNew;
        this.product_url = "/product?id=" + id;
        this.cart_url = "/incart?productid=" + id;
        this.wishlist_url = "/wishlist?productid=" + id;
        this.compare_url = "/compare?productid=" + id;
        this.short_detail = short_detail;
    }

    public SingleProduct(long id) {
        this.id = id;
        this.product_url = "/product?id=" + id;
        this.cart_url = "/incart?productid=" + id;
        this.wishlist_url = "/wishlist?productid=" + id;
        this.compare_url = "/wishlist?productid=" + id;
    }
}
