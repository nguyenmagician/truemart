package com.example.truemart.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DictionaryTrueMart {
    public final static Map<String,Category> Category = new HashMap<>();
    public final static  Map<String,Brand> Brand = new HashMap<>();
    public final static Map<String, List<String>> ListChildrenOfCategory = new HashMap<>();

    public DictionaryTrueMart() {}
}
