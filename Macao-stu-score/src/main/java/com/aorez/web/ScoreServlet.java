package com.aorez.web;

import com.alibaba.fastjson.JSON;
import com.aorez.pojo.PageBean;
import com.aorez.pojo.Score;
import com.aorez.service.ScoreService;
import com.aorez.sql.ScoreSQL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/score/*")
public class ScoreServlet extends BaseServlet {
    private ScoreSQL scoreSQL = new ScoreSQL();
    private ScoreService scoreService = new ScoreService();

    public void selectAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Score> list = scoreSQL.selectAll();
        String jsonString = JSON.toJSONString(list);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }

    public void insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        String jsonLine = bufferedReader.readLine();
        Score score = JSON.parseObject(jsonLine, Score.class);
        boolean b = scoreService.insert(score);
        if (b) {
            resp.getWriter().write("success");
        }
    }

    public void updateBySnoAndCourseId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        String jsonLine = bufferedReader.readLine();
        Score score = JSON.parseObject(jsonLine, Score.class);
        boolean b = scoreSQL.updateBySnoAndCourseId(score);
        if (b) {
            resp.getWriter().write("success");
        }
    }

    public void deleteBySnoAndCourseId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String courseId = req.getParameter("courseId");
        String sno = req.getParameter("sno");
        boolean b = scoreSQL.deleteBySnoAndCourseId(sno, courseId);
        if (b) {
            resp.getWriter().write("success");
        }
    }

    public void selectByCondition(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        String jsonLine = bufferedReader.readLine();
        Score score = JSON.parseObject(jsonLine, Score.class);
        String scoreOrdered = req.getParameter("scoreOrdered");
        List<Score> list = scoreSQL.selectByCondition(score, scoreOrdered);
        String jsonString = JSON.toJSONString(list);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }

    public void selectByConditionAndPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        String jsonLine = bufferedReader.readLine();
        Score score = JSON.parseObject(jsonLine, Score.class);

        String _currentPage = req.getParameter("currentPage");
        String _pageSize = req.getParameter("pageSize");
        String scoreOrdered = req.getParameter("scoreOrdered");
        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);

        PageBean<Score> pageBean = scoreService.selectByConditionAndPage(currentPage, scoreOrdered, pageSize, score);

        String jsonString = JSON.toJSONString(pageBean);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);

    }
}