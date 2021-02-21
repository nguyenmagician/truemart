package com.example.truemart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaxMinPrice {
    private BigDecimal max;
    private BigDecimal min;
}
