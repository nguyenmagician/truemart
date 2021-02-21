package com.example.truemart.DTO;

import lombok.Data;

@Data
public class FeatureProduct extends SingleProduct{
    public FeatureProduct() {
        super();
    }

    public FeatureProduct(long id, String name, String img1_url, String img2_url, String price, String price_after_discount, String discount, boolean isDiscounted, boolean isNew,String short_detail) {
        super(id, name, img1_url, img2_url, price, price_after_discount, discount, isDiscounted, isNew,short_detail);
    }

    public FeatureProduct(long id) {
        super(id);
    }
}
