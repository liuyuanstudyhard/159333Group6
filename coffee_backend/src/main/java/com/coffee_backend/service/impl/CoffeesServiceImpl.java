package com.coffee_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coffee_backend.common.Result;
import com.coffee_backend.entity.Categories;
import com.coffee_backend.entity.Coffees;
import com.coffee_backend.entity.vo.CategoriesCoffeesData;
import com.coffee_backend.mapper.CategoriesMapper;
import com.coffee_backend.mapper.CoffeesMapper;
import com.coffee_backend.service.CoffeesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CoffeesServiceImpl extends ServiceImpl<CoffeesMapper, Coffees> implements CoffeesService {
    @Autowired
    private CategoriesMapper categoriesMapper;

    @Autowired
    private CoffeesMapper coffeesMapper;

    public Result<List<CategoriesCoffeesData>> getAllCategoriesWithCoffees() {
        List<Categories> categoriesList = categoriesMapper.selectList(null);
        List<CategoriesCoffeesData> result = new ArrayList<>();

        for (Categories category : categoriesList) {
            QueryWrapper<Coffees> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("category_id", category.getId());
            List<Coffees> coffees = coffeesMapper.selectList(queryWrapper);
            CategoriesCoffeesData data = new CategoriesCoffeesData();
            data.setName(category.getName());
            data.setItems(coffees);
            result.add(data);
        }

        return new Result<>(200, "Categories and coffees retrieved successfully", result);
    }

    @Override
    public Result<Coffees> addCoffees(Coffees coffee) {
        boolean save = this.save(coffee);
        if (save) {
            return new Result<>(200, "Coffee added successfully", coffee);
        } else {
            return new Result<>(500, "Failed to add coffee", null);
        }
    }

    @Override
    public Result<Boolean> deleteCoffees(Long id) {
        boolean remove = this.removeById(id);
        if (remove) {
            return new Result<>(200, "Coffee deleted successfully", true);
        } else {
            return new Result<>(404, "Coffee not found", false);
        }
    }

    @Override
    public Result<Coffees> updateCoffees(Coffees coffee) {
        boolean update = this.updateById(coffee);
        if (update) {
            return new Result<>(200, "Coffee updated successfully", coffee);
        } else {
            return new Result<>(404, "Coffee not found", null);
        }
    }

    @Override
    public Result<Coffees> getCoffeesById(Long id) {
        Coffees coffee = this.getById(id);
        if (coffee != null) {
            return new Result<>(200, "Coffee found", coffee);
        } else {
            return new Result<>(404, "Coffee not found", null);
        }
    }

    @Override
    public Result<List<Coffees>> getAllCoffees() {
        List<Coffees> coffees = this.list();
        return new Result<>(200, "Coffees retrieved successfully", coffees);
    }
}
