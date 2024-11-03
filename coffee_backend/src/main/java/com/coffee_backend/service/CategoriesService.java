package com.coffee_backend.service;

import com.coffee_backend.common.Result;
import com.coffee_backend.entity.Categories;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoriesService extends IService<Categories> {
    Result<Categories> addCategories(Categories category);
    Result<Boolean> deleteCategories(Long id);
    Result<Categories> updateCategories(Categories category);
    Result<Categories> getCategoriesById(Long id);
    Result<List<Categories>> getAllCategories();
}
