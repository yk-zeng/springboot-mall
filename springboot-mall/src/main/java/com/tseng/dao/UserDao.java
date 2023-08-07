package com.tseng.dao;

import com.tseng.dto.UserRegisterRequest;
import com.tseng.model.User;

public interface UserDao {

    Integer createUser(UserRegisterRequest userRegisterRequest);
    User getUserById(Integer userId);

}
