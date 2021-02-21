package com.example.truemart.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(length = 2000)
    private String long_detail;

    @Column(length = 500)
    private String short_detail;

    private boolean isDiscountable;

    private float discount;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<Image> images;

    public Product() {
        this.images = new ArrayList<>();
    }

    public Product(String name, String long_detail, String short_detail, boolean isDiscountable, float discount) {
        this.name = name;
        this.long_detail = long_detail;
        this.short_detail = short_detail;
        this.isDiscountable = isDiscountable;
        this.discount = discount;
        this.images = new ArrayList<>();
    }

    public void addImage(Image image) {
        image.setProduct(this);
        this.getImages().add(image);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLong_detail() {
        return long_detail;
    }

    public void setLong_detail(String long_detail) {
        this.long_detail = long_detail;
    }

    public String getShort_detail() {
        return short_detail;
    }

    public void setShort_detail(String short_detail) {
        this.short_detail = short_detail;
    }

    public boolean isDiscountable() {
        return isDiscountable;
    }

    public void setDiscountable(boolean discountable) {
        isDiscountable = discountable;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

}
