package com.coffee_backend.service.impl;

import com.coffee_backend.common.Result;
import com.coffee_backend.entity.Categories;
import com.coffee_backend.mapper.CategoriesMapper;
import com.coffee_backend.service.CategoriesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoriesServiceImpl extends ServiceImpl<CategoriesMapper, Categories> implements CategoriesService {
    @Override
    public Result<Categories> addCategories(Categories category) {
        boolean save = this.save(category);
        if (save) {
            return new Result<>(200, "Categories added successfully", category);
        } else {
            return new Result<>(500, "Failed to add category", null);
        }
    }

    @Override
    public Result<Boolean> deleteCategories(Long id) {
        boolean remove = this.removeById(id);
        if (remove) {
            return new Result<>(200, "Categories deleted successfully", true);
        } else {
            return new Result<>(404, "Categories not found", false);
        }
    }

    @Override
    public Result<Categories> updateCategories(Categories category) {
        boolean update = this.updateById(category);
        if (update) {
            return new Result<>(200, "Categories updated successfully", category);
        } else {
            return new Result<>(404, "Categories not found", null);
        }
    }

    @Override
    public Result<Categories> getCategoriesById(Long id) {
        Categories category = this.getById(id);
        if (category != null) {
            return new Result<>(200, "Categories found", category);
        } else {
            return new Result<>(404, "Categories not found", null);
        }
    }

    @Override
    public Result<List<Categories>> getAllCategories() {
        List<Categories> categories = this.list();
        return new Result<>(200, "Categories retrieved successfully", categories);
    }
}
