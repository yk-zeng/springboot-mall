package com.tseng.dao;

import com.tseng.dto.UserRegisterRequest;
import com.tseng.model.User;

public interface UserDao {


    User getUserById(Integer userId);
    User getUserByEmail(String email);
    Integer createUser(UserRegisterRequest userRegisterRequest);

}
