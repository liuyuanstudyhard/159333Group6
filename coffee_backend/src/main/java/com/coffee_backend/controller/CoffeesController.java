package com.coffee_backend.controller;

import com.coffee_backend.common.Result;
import com.coffee_backend.entity.Coffees;
import com.coffee_backend.entity.vo.CategoriesCoffeesData;
import com.coffee_backend.service.CoffeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/coffees")
public class CoffeesController {

    @Autowired
    private CoffeesService coffeesService;

    // Get all categories and their corresponding coffees
    @GetMapping("/getCoffees")
    public Result<List<CategoriesCoffeesData>> getAllCategoriesWithCoffees() {
        return coffeesService.getAllCategoriesWithCoffees();
    }

    // Add new coffee
    @PostMapping("/add")
    public Result<Coffees> addCoffee(@RequestBody Coffees coffee) {
        return coffeesService.addCoffees(coffee);
    }

    // Example Delete the coffee with the specified ID
    @DeleteMapping("/delete/{id}")
    public Result<Boolean> deleteCoffee(@PathVariable Long id) {
        return coffeesService.deleteCoffees(id);
    }

    // Update coffee information
    @PutMapping("/update")
    public Result<Coffees> updateCoffee(@RequestBody Coffees coffee) {
        return coffeesService.updateCoffees(coffee);
    }

    // Gets the specified coffee by ID
    @GetMapping("/get/{id}")
    public Result<Coffees> getCoffeeById(@PathVariable Long id) {
        return coffeesService.getCoffeesById(id);
    }

    // Get all coffee (regardless of category)
    @GetMapping("/getAll")
    public Result<List<Coffees>> getAllCoffees() {
        return coffeesService.getAllCoffees();
    }
}
