package com.coffee_backend.service;

import com.coffee_backend.common.Result;
import com.coffee_backend.entity.Coffees;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coffee_backend.entity.vo.CategoriesCoffeesData;
import org.springframework.stereotype.Service;

import java.util.List;



public interface CoffeesService extends IService<Coffees> {
    Result<Coffees> addCoffees(Coffees coffee);
    Result<Boolean> deleteCoffees(Long id);
    Result<Coffees> updateCoffees(Coffees coffee);
    Result<Coffees> getCoffeesById(Long id);
    Result<List<Coffees>> getAllCoffees();
    Result<List<CategoriesCoffeesData>> getAllCategoriesWithCoffees();
}
