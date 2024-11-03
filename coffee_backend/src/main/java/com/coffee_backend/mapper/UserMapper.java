package com.coffee_backend.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coffee_backend.entity.Categories;
import com.coffee_backend.entity.User;
import org.apache.ibatis.annotations.*;


@Mapper
public interface UserMapper extends BaseMapper<Categories> {

    @Insert("INSERT INTO users (username, password, name, email, phone, gender, address) " +
            "VALUES (#{username}, #{password}, #{name}, #{email}, #{phone}, #{gender}, #{address})")
    int registerUser(String username, String password, String name, String email, String phone, String gender, String address);

    @Select("SELECT * FROM users WHERE username = #{username}")
    User findUserByUsername(String username);


    @Select("SELECT * FROM users WHERE id = #{id}")
    User findUserById(Long id);


    @Update("UPDATE users SET name = #{name}, email = #{email}, phone = #{phone}, gender = #{gender}, address = #{address} WHERE id = #{id}")
    int updateUser(Long id, String name, String email, String phone, String gender, String address);
}
