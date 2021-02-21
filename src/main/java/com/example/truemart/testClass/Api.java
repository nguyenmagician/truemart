package com.example.truemart.testClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api {
    private UUID id;
    private String email;
    private String name;
}
