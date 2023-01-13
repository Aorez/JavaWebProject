package com.aorez.web;

import com.alibaba.fastjson.JSON;
import com.aorez.controller.ArticleController;
import com.aorez.model.Article;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/article/*")
public class ArticleServlet extends BaseServlet {
    private ArticleController articleController = new ArticleController();

    public void findArticle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Article article = articleController.findArticle(Integer.parseInt(id));
        String jsonString = JSON.toJSONString(article);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }

    public void cover(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader bufferedReader = req.getReader();
        String jsonLine = bufferedReader.readLine();
        Article article = JSON.parseObject(jsonLine, Article.class);
        boolean b = articleController.cover(article);
        if (b) {
            resp.getWriter().write("success");
        }
    }

    public void count(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long l = articleController.count();
        resp.getWriter().write(Long.toString(l));
    }

    public void remove(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        boolean b = articleController.remove(Integer.parseInt(id));
        if (b) {
            resp.getWriter().write("success");
        }
    }
}
