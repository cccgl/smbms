package com.lei.service.user;

import com.lei.pojo.User;

import java.sql.Connection;
import java.util.List;

public interface UserService {
    public User login(String userCode,String password);

    //修改密码
    public boolean updatePwd(int id, String password);

    //查询记录数
    public int getUserCount(String username,int userrole);

    //获取用户列表
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize);
}
