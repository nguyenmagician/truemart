package com.example.truemart.testClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class TestApi {
    @NotEmpty(message = "khong duoc trong")
    @Email(message = "email must vaild")
    private String email;

    @NotEmpty(message = "khong duoc trong")
    @Size(min=2, message="Last name must not be less than 2 characters")
    private String name;

    public TestApi() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
