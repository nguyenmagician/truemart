package com.example.truemart.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class CartDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private int amount;
    private List<CartItemDTO> carts;
    private String subtotal;
    private String shipping;
    private String taxes;
    private String total;

    public CartDTO() {
        this.amount = 0;
        this.carts = new ArrayList<>();
        this.shipping = "$0.00";
        this.taxes = "$0.00";
    }


}
