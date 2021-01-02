package com.lei.service.user;

import com.lei.dao.BaseDao;
import com.lei.dao.user.UserDao;
import com.lei.dao.user.UserDaoImp1;
import com.lei.pojo.User;
//import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

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

    @Override
    public boolean updatePwd(int id, String password) {
        Connection connection=null;
        boolean flag=false;
        System.out.println("newpassword service"+password);
        try {
            connection=BaseDao.getConnection();
            if(userDao.updatePwd(connection,id,password)>0)
            {
                flag=true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            BaseDao.closeResource(connection,null,null);
        }
        return flag;
    }

    @Override
    public int getUserCount(String username, int userrole) {

        Connection connection=null;
        int count=0;
        try {
            connection=BaseDao.getConnection();
            count=userDao.getUserCount(connection,username,userrole);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            BaseDao.closeResource(connection,null,null);
        }
        return  count;

    }
    @Override
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
        // TODO Auto-generated method stub
        Connection connection = null;
        List<User> userList = null;
        System.out.println("queryUserName ---- > " + queryUserName);
        System.out.println("queryUserRole ---- > " + queryUserRole);
        System.out.println("currentPageNo ---- > " + currentPageNo);
        System.out.println("pageSize ---- > " + pageSize);
        try {
            connection = BaseDao.getConnection();
            userList = userDao.getUserList(connection, queryUserName,queryUserRole,currentPageNo,pageSize);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return userList;
    }
}
