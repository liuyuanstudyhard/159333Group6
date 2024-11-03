package com.coffee_backend.mapper;

import com.coffee_backend.entity.Coffees;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CoffeesMapper extends BaseMapper<Coffees> {

}
