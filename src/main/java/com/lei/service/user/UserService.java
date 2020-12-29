package com.lei.service.user;

import com.lei.pojo.User;

public interface UserService {
    public User login(String userCode,String password);
}
