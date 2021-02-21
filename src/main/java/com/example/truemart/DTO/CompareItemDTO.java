package com.example.truemart.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class CompareItemDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String product_name;
    private String short_detail;
    private String price;
    private boolean isStockable;
    private String image_url;
    private String url;
    private long id;

    public CompareItemDTO() {
    }


}
