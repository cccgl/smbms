package com.lei.service.user;

import com.lei.dao.BaseDao;
import com.lei.dao.user.UserDao;
import com.lei.dao.user.UserDaoImp1;
import com.lei.pojo.User;
//import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class UserServiceImp1 implements UserService{
    //业务层需要dao层 引入dao层
    private UserDao userDao;
    public UserServiceImp1(){
        userDao=new UserDaoImp1();
    }


    public User login(String userCode, String password) {
        Connection connection=null;
        User user=null;

        try {
            connection= BaseDao.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        user=userDao.getLoginUser(connection,userCode);



        BaseDao.closeResource(connection,null,null);

        return  user;
    }

//    @Test
//    public void test(){
//        UserServiceImp1 userServiceImp1=new UserServiceImp1();
//        User admin=userServiceImp1.login("admin","123456");
//        System.out.println(admin.getUserPassword());
//    }
}
