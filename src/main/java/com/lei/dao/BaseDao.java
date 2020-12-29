package com.lei.dao;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.io.IOException;

//操作数据库的公共类（读取properties里面的文件）
public class BaseDao {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    //静态代码块 随着类的加载而执行，而且只执行一次
    static {
        Properties properties = new Properties();
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");

    }

    public static Connection getConnection() throws ClassNotFoundException{
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection =DriverManager.getConnection("jdbc:mysql://localhost:3306/smbms?useUnicode=true&serverTimezone=Asia/Shanghai&useSSL=FALSE" ,"root","");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

    //查询方法
    public static ResultSet execute(Connection connection, String sql, Object[] params, ResultSet resultSet,PreparedStatement preparedStatement) throws Exception {
        //PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        for (int i = 0; i < params.length; i++) {

            preparedStatement.setObject(i + 1, params[i]);
        }
        resultSet = preparedStatement.executeQuery();

        return resultSet;
    }

    //增删改方法
    public static int execute(Connection connection, String sql, Object[] params, PreparedStatement preparedStatement) throws Exception {
       // PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        for (int i = 0; i < params.length; i++) {

            preparedStatement.setObject(i + 1, params[i]);
        }
        int updateRows = preparedStatement.executeUpdate();

        return updateRows;
    }

    //释放资源
    public static boolean closeResource(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet)
    {
        boolean flag=true;
        if(resultSet!=null)
        {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag=false;
            }
        }
        if(connection!=null)
        {
            try {
                connection.close();
            } catch (Exception throwables) {
                throwables.printStackTrace();
                flag=false;
            }
        }
        if(preparedStatement!=null)
        {
            try {
                preparedStatement.close();
            } catch (Exception throwables) {
                throwables.printStackTrace();
                flag=false;
            }
        }
        return flag;
    }
}
