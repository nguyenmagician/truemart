package com.example.truemart.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;


public class CartItemDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String productId;
    private String url;
    private int quantities;
    private String img_url;
    private String product_name;
    private String price;
    private String size;
    private String quantities_text;
    private String c_price;
    private int amount_stock;

    public CartItemDTO(long id,String product_name,int quantities,String img_url,String price,String size,String c_price) {
        this.id = id;
        this.productId = "productid-"+ String.valueOf(id);
        this.url = "/product?id="+id;
        this.product_name = product_name;
        this.quantities = quantities;
        this.img_url = img_url;
        this.price = price;
        this.size = size;
        this.quantities_text = String.valueOf(quantities);
        this.c_price = c_price;
    }

    public CartItemDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getQuantities() {
        return quantities;
    }

    public void setQuantities(int quantities) {
        this.quantities = quantities;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getQuantities_text() {
        return quantities_text;
    }

    public void setQuantities_text(String quantities_text) {
        this.quantities_text = quantities_text;
    }

    public String getC_price() {
        return c_price;
    }

    public void setC_price(String c_price) {
        this.c_price = c_price;
    }
}
