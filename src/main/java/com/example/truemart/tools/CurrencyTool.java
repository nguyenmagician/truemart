package com.example.truemart.tools;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyTool {
    public static final NumberFormat usFormat = NumberFormat.getCurrencyInstance(Locale.US);

    public static String getPrice(BigDecimal price) {
        return usFormat.format(price);
    }

    public static String getDiscountPrice(BigDecimal price,float discount) {
        BigDecimal prefix = BigDecimal.valueOf(1 - discount / 100);
        BigDecimal priceDiscount = price.multiply(prefix);

        return usFormat.format(priceDiscount);
    }
}
