package com.example.truemart.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String telephone;
    private String password;
    private boolean yesSubscribe = true;
}
