package com.aorez.controller;

import com.aorez.model.Article;
import com.aorez.util.MongoUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/*
{
    '_id':'_id','id':NumberInt('1')
}
 */
public class ArticleController {
    private static MongoCollection<Document> collection;
    private static final String collectionName = "article";

    static {
        collection = MongoUtil.getMongoCollection("article");
    }

    public Article findArticle(int id) {
        Article article = null;

        Document find = new Document();
        //将_id是0的那个文档排除
        List<String> a = new ArrayList<>();
        a.add("_id");
        find.append("_id", new BasicDBObject("$nin", a));
        FindIterable<Document> findIterable = collection.find(find);

        MongoCursor<Document> iterator = findIterable.iterator();
        Document next = null;
        for (int i = 1; i <= id; i++) {
            if (iterator.hasNext()) {
                next = iterator.next();
            }
            else {
                next = null;
                break;
            }
        }

        if (next != null) {
//            int _id = (int)(double)next.getDouble("_id");
            int _id = next.getInteger("id");
            String title = next.getString("title");
            List<String> paras = new ArrayList<>();
            int index = 0;
            while (next.containsKey(Integer.toString(index))) {
                paras.add(next.getString(Integer.toString(index)));
                index++;
            }
            article = new Article();
            article.setTitle(title);
            article.setParas(paras);
            article.setId(_id);
        }

        return article;
    }

    public boolean cover(Article article) {
        //如果是新增文档，article.id等于-1
        if (article.getId() == -1) {
            Document updateId = new Document();
            updateId.append("id", 1);
            int id = collection.findOneAndUpdate(new BasicDBObject("_id", "_id"), new Document("$inc", updateId)).getInteger("id");
            article.setId(id);
            return insert(article);
        }
        else {
            return update(article);
        }
    }

    private boolean update(Article article) {
        if (remove(article.getId())) {
            return insert(article);
        }
        return false;
    }

    public boolean remove(int id) {
        return collection.findOneAndDelete(new BasicDBObject("id", id)) != null;
    }

    public boolean insert(Article article) {
        try {
            collection.insertOne(article.getDocument());
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }

    public long count() {
        return collection.countDocuments() - 1;
    }
}
