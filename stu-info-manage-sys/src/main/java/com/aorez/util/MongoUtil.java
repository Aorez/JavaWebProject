package com.aorez.util;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MongoUtil {
    private static Properties properties;
    private static MongoDatabase mongoDatabase;
    private static InputStream inputStream = null;
    private static String host;
    private static int port;
    private static String dbname;
    private static String username;
    private static String password;

    static {
        if (properties == null) {
            properties = new Properties();
        }


        try {
            inputStream = MongoUtil.class.getClassLoader().getResourceAsStream("mongodb.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        host = properties.getProperty("host");
        port = Integer.parseInt(properties.getProperty("port"));
        dbname = properties.getProperty("dbname");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }


    /*定义方法，获取MongoDB连接对象*/
    public static MongoClient getMongoClient(){
//        String addr = "mongodb://"  + host + ":" + port + "/" + dbname;
//        String addr = "mongodb://" + username + ":" + password + "@" + host + ":" + port + "/" + dbname;
        MongoClient mongoClient = new MongoClient(host, port);
        return mongoClient;
    }

    /*定义一个方法，实现连接指定数据库*/
    public static MongoDatabase getMongoConn(){
        MongoClient mongoClient = getMongoClient();
        mongoDatabase = mongoClient.getDatabase(dbname);
        System.out.println("数据库连接成功！");
        return mongoDatabase;
    }

    public static MongoCollection<Document> getMongoCollection(String name) {
        mongoDatabase = getMongoConn();
        return mongoDatabase.getCollection(name);
    }
}
