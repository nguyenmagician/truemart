package com.example.truemart.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InCartResponse {
    private String message;

    public InCartResponse(String message) {
        this.message = message;
    }
}
