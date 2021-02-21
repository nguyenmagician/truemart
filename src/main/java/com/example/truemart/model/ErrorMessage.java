package com.example.truemart.model;

import com.example.truemart.model.ErrorCustom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
    private List<ErrorCustom> errors;
}
