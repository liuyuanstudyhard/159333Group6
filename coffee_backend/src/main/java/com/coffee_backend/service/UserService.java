package com.coffee_backend.service;

import com.coffee_backend.entity.User;
import com.coffee_backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public long registerUser(String username, String password, String name, String email, String phone, String gender, String address) {
        User existingUser = userMapper.findUserByUsername(username);
        if (existingUser != null) {

            return -1;
        }

        return userMapper.registerUser(username, password, name, email, phone, gender, address);
    }


    public User loginUser(String username, String password) {
        User user = userMapper.findUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }


    public User getUserById(Long id) {
        return userMapper.findUserById(id);
    }


    public boolean updateUser(Long id, String name, String email, String phone, String gender, String address) {
        int updated = userMapper.updateUser(id, name, email, phone, gender, address);
        return updated > 0;
    }


}
