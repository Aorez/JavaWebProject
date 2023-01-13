package com.aorez.web;

import com.alibaba.fastjson.JSON;
import com.aorez.pojo.Course;
import com.aorez.pojo.PageBean;
import com.aorez.service.CourseService;
import com.aorez.sql.CourseSQL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/course/*")
public class CourseServlet extends BaseServlet {
    private CourseSQL courseSQL = new CourseSQL();
    private CourseService courseService = new CourseService();

    public void selectAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Course> list = courseSQL.selectAll();
        String jsonString = JSON.toJSONString(list);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }

    public void insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        String jsonLine = bufferedReader.readLine();
        Course course = JSON.parseObject(jsonLine, Course.class);
        boolean b = courseSQL.insert(course);
        if (b) {
            resp.getWriter().write("success");
        }
    }

    public void updateByCourseId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        String jsonLine = bufferedReader.readLine();
        Course course = JSON.parseObject(jsonLine, Course.class);
        boolean b = courseSQL.updateByCourseId(course);
        if (b) {
            resp.getWriter().write("success");
        }
    }

    public void deleteByCourseId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String courseId = req.getParameter("courseId");
        boolean b = courseSQL.deleteByCourseId(courseId);
        if (b) {
            resp.getWriter().write("success");
        }
    }

    public void selectByCondition(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        String jsonLine = bufferedReader.readLine();
        Course course = JSON.parseObject(jsonLine, Course.class);
        List<Course> list = courseSQL.selectByCondition(course);
        String jsonString = JSON.toJSONString(list);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }

    public void selectByConditionAndPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        String jsonLine = bufferedReader.readLine();
        Course course = JSON.parseObject(jsonLine, Course.class);

        String _currentPage = req.getParameter("currentPage");
        String _pageSize = req.getParameter("pageSize");
        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);

        PageBean<Course> pageBean = courseService.selectByConditionAndPage(currentPage, pageSize, course);

        String jsonString = JSON.toJSONString(pageBean);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }
}