package com.lei.servlet.user;

import com.lei.pojo.User;
import com.lei.service.user.UserService;
import com.lei.service.user.UserServiceImp1;
import com.lei.util.Constants;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//实现servlet复用
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从session获取id
        Object o=req.getSession().getAttribute(Constants.USER_SESSION);

        String newpassword=req.getParameter("newpassword");
        boolean flag=false;
        System.out.println("newpassword1"+newpassword);
        if(o!=null&&!StringUtils.isNullOrEmpty(newpassword))
        {
            UserService userService=new UserServiceImp1();

            flag=userService.updatePwd(((User)o).getId(),newpassword);

            if(flag)
            {
                System.out.println("newpassword flag"+newpassword);
                req.setAttribute("message","修改密码成功 请重新登录");
                //修改完密码 移除session
                req.getSession().removeAttribute(Constants.USER_SESSION);

            }
            else
            {
                req.setAttribute("message","修改密码失败");
             }
        }
        else
        {
            req.setAttribute("message","新密码有问题");


        }
        req.getRequestDispatcher("pwdmodify.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
