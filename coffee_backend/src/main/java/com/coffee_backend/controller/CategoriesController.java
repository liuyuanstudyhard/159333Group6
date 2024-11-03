package com.coffee_backend.controller;


import com.coffee_backend.common.Result;
import com.coffee_backend.entity.Categories;
import com.coffee_backend.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    // Get all categories
    @GetMapping("/getAll")
    public Result getAll() {
        return categoriesService.getAllCategories();
    }

    // Add categories
    @PostMapping("/add")
    public Result addCategory(@RequestBody Categories category) {
        return categoriesService.addCategories(category);
    }

    // Update categories
    @PutMapping("/update")
    public Result updateCategory(@RequestBody Categories category) {
        return categoriesService.updateCategories(category);
    }

    // Deleteccategories
    @DeleteMapping("/delete/{id}")
    public Result deleteCategory(@PathVariable long id) {
        return categoriesService.deleteCategories(id);
    }

    // Get categories
    @GetMapping("/get/{id}")
    public Result getCategoryById(@PathVariable long id) {
        return categoriesService.getCategoriesById(id);
    }
}
