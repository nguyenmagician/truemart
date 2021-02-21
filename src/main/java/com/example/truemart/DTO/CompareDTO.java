package com.example.truemart.DTO;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class CompareDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<CompareItemDTO> compareItemDTOList;

    public CompareDTO() {
        this.compareItemDTOList = new ArrayList<>();
    }
}
