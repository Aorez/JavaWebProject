package com.aorez.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aorez.pojo.User;
import com.aorez.sql.UserSQL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserSQL userSQL = new UserSQL();

    /**
     * 登录验证
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        String jsonLine = bufferedReader.readLine();
        JSONObject jsonObject = JSON.parseObject(jsonLine);
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");

        //判断字符串是否为空
        if (username == null || username.length() <= 0 || password == null || password.length() <= 0) {
            resp.getWriter().write("fail");
            return;
        }

        //管理员入口
        if ("admin".equals(username) && "admin".equals(password)) {
            resp.getWriter().write("admin");
            return;
        }

        //执行sql获得User对象
        User user = userSQL.select(username, password);
        //登录失败
        if (user == null) {
            resp.getWriter().write("fail");
        }
        else {

        }
    }

    public void selectAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> list = userSQL.selectAll();
        String jsonString = JSON.toJSONString(list);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }

    public void updateById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        String jsonLine = bufferedReader.readLine();
        User user = JSON.parseObject(jsonLine, User.class);
        boolean b = userSQL.updateById(user);
        if (b) {
            resp.getWriter().write("success");
        }
    }

    public void deleteById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        boolean b = userSQL.deleteById(Integer.parseInt(id));
        if (b) {
            resp.getWriter().write("success");
        }
    }

    public void insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        String jsonLine = bufferedReader.readLine();
        User user = JSON.parseObject(jsonLine, User.class);
        boolean b = userSQL.insert(user);
        if (b) {
            resp.getWriter().write("success");
        }
    }

}