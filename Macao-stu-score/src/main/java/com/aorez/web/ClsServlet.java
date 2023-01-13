package com.aorez.web;

import com.alibaba.fastjson.JSON;
import com.aorez.controller.ClsController;
import com.aorez.model.Cls;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/cls/*")
public class ClsServlet extends BaseServlet{
    private ClsController clsController = new ClsController();

    public void analyse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        String jsonLine = bufferedReader.readLine();
        Cls cls = JSON.parseObject(jsonLine, Cls.class);
        String grade = cls.getGrade();
        float sec = cls.getSec();

        List<Cls> list = clsController.analyse(grade, sec);
        String jsonString = JSON.toJSONString(list);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }
}
