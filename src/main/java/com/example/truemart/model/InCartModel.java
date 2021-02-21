package com.example.truemart.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
public class InCartModel {
    private long id;
    @Email
    private String email;
    private String size;
    private int quantities;
}
