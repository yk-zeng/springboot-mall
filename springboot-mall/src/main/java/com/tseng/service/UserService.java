package com.tseng.service;

import com.tseng.dto.UserRegisterRequest;
import com.tseng.model.User;

public interface UserService {

    Integer register(UserRegisterRequest userRegisterRequest);
    User getUserById(Integer userId);

}
