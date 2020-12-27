package com.lei.dao;

import java.io.InputStream;
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
        Properties properties=new Properties();
        InputStream is=BaseDao.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver=properties.getProperty("driver");
        url=properties.getProperty("url");
        username=properties.getProperty("username");
        password=properties.getProperty("password");

    }

}
