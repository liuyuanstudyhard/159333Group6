package com.coffee_backend.entity.vo;

import com.coffee_backend.entity.Coffees;
import lombok.Data;

import java.util.List;

@Data
public class CategoriesCoffeesData {
    private String name;
    private List<Coffees> items;
}
