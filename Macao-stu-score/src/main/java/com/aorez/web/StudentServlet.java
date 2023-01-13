package com.aorez.web;

import com.alibaba.fastjson.JSON;
import com.aorez.pojo.PageBean;
import com.aorez.pojo.Student;
import com.aorez.service.StudentService;
import com.aorez.sql.StudentSql;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/student/*")
public class StudentServlet extends BaseServlet {
    private StudentSql studentSql = new StudentSql();
    private StudentService studentService = new StudentService();

    public void selectAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Student> list = studentSql.selectAll();
        String jsonString = JSON.toJSONString(list);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }

    public void insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        String jsonLine = bufferedReader.readLine();
        Student student = JSON.parseObject(jsonLine, Student.class);
        boolean b = studentSql.insert(student);
        if (b) {
            resp.getWriter().write("success");
        }
    }

    public void updateBySno(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        String jsonLine = bufferedReader.readLine();
        Student student = JSON.parseObject(jsonLine, Student.class);
        boolean b = studentSql.updateBySno(student);
        if (b) {
            resp.getWriter().write("success");
        }
    }

    public void deleteBySno(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sno = req.getParameter("sno");
        boolean b = studentSql.deleteBySno(sno);
        if (b) {
            resp.getWriter().write("success");
        }
    }

    public void selectByCondition(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        String jsonLine = bufferedReader.readLine();
        Student student = JSON.parseObject(jsonLine, Student.class);
        List<Student> list = studentSql.selectByCondition(student);
        String jsonString = JSON.toJSONString(list);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }

    public void selectByConditionAndPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        BufferedReader bufferedReader = req.getReader();
        String jsonLine = bufferedReader.readLine();
        Student student = JSON.parseObject(jsonLine, Student.class);

        String _currentPage = req.getParameter("currentPage");
        String _pageSize = req.getParameter("pageSize");
        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);

        PageBean<Student> pageBean = studentService.selectByConditionAndPage(currentPage, pageSize, student);

        String jsonString = JSON.toJSONString(pageBean);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }
}