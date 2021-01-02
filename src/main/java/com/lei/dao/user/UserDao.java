package com.lei.dao.user;

import com.lei.pojo.Role;
import com.lei.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    //得到要登录的用户
    public User getLoginUser(Connection connection,String userCode);

    //修改当前密码
    public int updatePwd(Connection connection,int id,String password);

    //查询用户总数
    public int getUserCount(Connection connection,String username,int  userrole) throws SQLException;

    //通过条件查询用户列表
    public List<User> getUserList(Connection connection,String userName,int userRole,int currentPageNo,int pageSize) throws Exception;

    //获取角色列表
    //public List<Role> getUserList(Connection connectionn) throws Exception;

}
