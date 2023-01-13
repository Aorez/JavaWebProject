package com.aorez.web;

import com.alibaba.fastjson.JSON;
import com.aorez.pojo.PageBean;
import com.aorez.pojo.Teacher;
import com.aorez.service.TeacherService;
import com.aorez.sql.TeacherSQL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/teacher/*")
public class TeacherServlet extends BaseServlet {
    private TeacherSQL teacherSQL = new TeacherSQL();
    private TeacherService teacherService = new TeacherService();

    public void selectAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Teacher> list = teacherSQL.selectAll();
        String jsonString = JSON.toJSONString(list);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }

    public void insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        String jsonLine = bufferedReader.readLine();
        Teacher teacher = JSON.parseObject(jsonLine, Teacher.class);
        boolean b = teacherService.insert(teacher);
        if (b) {
            resp.getWriter().write("success");
        }
    }

    public void updateByTeacherId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        String jsonLine = bufferedReader.readLine();
        Teacher teacher = JSON.parseObject(jsonLine, Teacher.class);
        boolean b = teacherService.updateByTeacherId(teacher);
        if (b) {
            resp.getWriter().write("success");
        }
    }

    public void deleteByTeacherId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String teacherId = req.getParameter("teacherId");
        boolean b = teacherSQL.deleteByTeacherId(teacherId);
        if (b) {
            resp.getWriter().write("success");
        }
    }

    public void selectByCondition(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        String jsonLine = bufferedReader.readLine();
        Teacher teacher = JSON.parseObject(jsonLine, Teacher.class);
        List<Teacher> list = teacherSQL.selectByCondition(teacher);
        String jsonString = JSON.toJSONString(list);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }

    public void selectByConditionAndPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        BufferedReader bufferedReader = req.getReader();
        String jsonLine = bufferedReader.readLine();
        Teacher teacher = JSON.parseObject(jsonLine, Teacher.class);

        String _currentPage = req.getParameter("currentPage");
        String _pageSize = req.getParameter("pageSize");
        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);

        PageBean<Teacher> pageBean = teacherService.selectByConditionAndPage(currentPage, pageSize, teacher);

        String jsonString = JSON.toJSONString(pageBean);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }
}