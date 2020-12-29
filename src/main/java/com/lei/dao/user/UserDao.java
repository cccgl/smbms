package com.lei.dao.user;

import com.lei.pojo.User;

import java.sql.Connection;

public interface UserDao {

    //得到要登录的用户
    public User getLoginUser(Connection connection,String userCode);

}
