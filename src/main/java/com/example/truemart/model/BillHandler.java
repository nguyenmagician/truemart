package com.example.truemart.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
public class BillHandler {
    private boolean status;
    private List<ErrorMessage> errors;
    private long billId;

    public BillHandler() {
        this.errors = new ArrayList<>();
    }

    public BillHandler(boolean status, List<ErrorMessage> errors, long billId) {
        this.status = status;
        this.billId = billId;
        if(errors!=null) {
            this.errors = errors;
        }else {
            this.errors = new ArrayList<>();
        }
    }
}
