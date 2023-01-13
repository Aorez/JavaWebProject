package com.aorez.web;

import com.alibaba.fastjson.JSON;
import com.aorez.pojo.Cls;
import com.aorez.pojo.PageBean;
import com.aorez.service.ClassService;
import com.aorez.sql.ClassSQL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/class/*")
public class ClassServlet extends BaseServlet {
    private ClassSQL classSQL = new ClassSQL();
    private ClassService classService = new ClassService();

    public void selectAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Cls> list = classSQL.selectAll();
        String jsonString = JSON.toJSONString(list);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }

    public void insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        String jsonLine = bufferedReader.readLine();
        Cls cls = JSON.parseObject(jsonLine, Cls.class);
        boolean b = classSQL.insert(cls);
        if (b) {
            resp.getWriter().write("success");
        }
    }

    public void updateByClassId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        String jsonLine = bufferedReader.readLine();
        Cls cls = JSON.parseObject(jsonLine, Cls.class);
        boolean b = classSQL.updateByClassId(cls);
        if (b) {
            resp.getWriter().write("success");
        }
    }

    public void deleteByClassId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String classId = req.getParameter("classId");
        boolean b = classSQL.deleteByClassId(classId);
        if (b) {
            resp.getWriter().write("success");
        }
    }

    public void selectByCondition(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        String jsonLine = bufferedReader.readLine();
        Cls cls = JSON.parseObject(jsonLine, Cls.class);
        List<Cls> list = classSQL.selectByCondition(cls);
        String jsonString = JSON.toJSONString(list);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }

    public void selectByConditionAndPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        String jsonLine = bufferedReader.readLine();
        Cls cls = JSON.parseObject(jsonLine, Cls.class);

        String _currentPage = req.getParameter("currentPage");
        String _pageSize = req.getParameter("pageSize");
        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);

        PageBean<Cls> pageBean = classService.selectByConditionAndPage(currentPage, pageSize, cls);

        String jsonString = JSON.toJSONString(pageBean);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }
}