package com.aorez.model;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


/**
 * 文章的类
 */
public class Article {
    private int id;
    private String title;
    private List<String> paras = new ArrayList<>();

    public Document getDocument() {
        Document document = new Document();
        document.append("id", id);
        document.append("title", title);
        for (int i = 0; i < paras.size(); i++) {
            document.append(Integer.toString(i), paras.get(i));
        }
        return document;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getParas() {
        return paras;
    }

    public void setParas(List<String> paras) {
        this.paras = paras;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
