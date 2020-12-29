package com.lei.servlet.user;

import com.lei.pojo.User;
import com.lei.service.user.UserService;
import com.lei.service.user.UserServiceImp1;
import com.lei.util.Constants;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    //控制层调用业务层
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("loginServlet start_____");

        String userCode=req.getParameter("userCode");
        String userPassword =req.getParameter("userPassword");

        //和数据层中的密码进行对比
        UserService userService=new UserServiceImp1();
        User user=userService.login(userCode,userPassword);

        if(user!=null)//存在此人
        {
            //存在此人且密码正确
            if(user.getUserPassword().equals(userPassword)) {
                req.getSession().setAttribute(Constants.USER_SESSION, user);
                resp.sendRedirect("jsp/frame.jsp");
            }
            //存在此人且密码错误
            else{
                //转发回登录页面 且显示密码错误
//                System.out.println(user.getUserPassword());
//                System.out.println(userPassword);
                req.setAttribute("error","密码不正确");
                req.getRequestDispatcher("login.jsp").forward(req,resp);
            }
        }
        else
        {
            //转发回登录页面 且显示密码错误
            req.setAttribute("error","用户名或者密码不正确");
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
