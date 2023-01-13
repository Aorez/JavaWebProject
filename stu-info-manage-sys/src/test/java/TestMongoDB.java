import com.aorez.util.MongoUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;

import java.sql.Date;

public class TestMongoDB {

    private static MongoDatabase mongoDatabase;

    public static void main(String[] args) {
        mongoDatabase = MongoUtil.getMongoConn();
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("collection_news");
        FindIterable<Document> one = mongoCollection.find(new BasicDBObject("_id", 1));
        MongoCursor<Document> iterator = one.iterator();
        Document next = iterator.next();
        String title = next.getString("title");
        System.out.println(title);
    }

//    @Test
//    public void update() {
//        mongoDatabase = MongoUtil.getMongoConn();
//        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("article");
//        Document where = new Document();
//        where.append("_id", 0);
//        Document update = new Document();
//        update.append("_id", 0);
//        update.append("title", "今日记录新");
//        update.append("0", "hahahaha");
//        mongoCollection.findOneAndUpdate(where, new Document("$set", update), new FindOneAndUpdateOptions().upsert(true));
//
//
//        FindIterable<Document> one = mongoCollection.find(new BasicDBObject("_id", 0));
//        MongoCursor<Document> iterator = one.iterator();
//        Document next = iterator.next();
//        String title = next.getString("title");
//        System.out.println(title);
//    }
//
//    @Test
//    public void getDBs(){
//        MongoClient mongoClient = MongoUtil.getMongoClient();
//        MongoIterable<String> databaseNames = mongoClient.listDatabaseNames();
//        for (String databaseName : databaseNames) {
//            System.out.println(databaseName);
//        }
//    }
//
//    @Test
//    public void getCollection(){
//        mongoDatabase = MongoUtil.getMongoConn();
//        MongoIterable<String> collectionNames = mongoDatabase.listCollectionNames();
//        for (String collectionName : collectionNames) {
//            System.out.println(collectionName);
//        }
//    }
//
//    @Test
//    public void createCollection(){
//        mongoDatabase = MongoUtil.getMongoConn();
//        mongoDatabase.createCollection("itcast");
//        System.out.println("创建集合成功！");
//    }
//
//    @Test
//    public void dropCollection(){
//        mongoDatabase = MongoUtil.getMongoConn();
//        MongoCollection<Document> itcast = mongoDatabase.getCollection("itcast");
//        itcast.drop();
//        System.out.println("集合删除成功！");
//    }
//
//    @Test
//    public void testFind() {
//        mongoDatabase = MongoUtil.getMongoConn();
//        MongoCollection<Document> article = mongoDatabase.getCollection("article");
//        Document where = new Document();
//        where.append("title", "题目");
//        FindIterable<Document> documents = article.find(where);
//        MongoCursor<Document> iterator = documents.iterator();
//        Document next = iterator.next();
//        ObjectId id = next.getObjectId("_id");
//        String s = id.toString();
//        System.out.println(s);
//
//        Document find = new Document();
//        find.append("_id", new ObjectId(s));
//        FindIterable<Document> documents1 = article.find(find);
//        MongoCursor<Document> iterator1 = documents1.iterator();
//        Document next1 = iterator1.next();
//        String title = next1.getString("title");
//        System.out.println(title);
//    }
//
//    @Test
//    public void testDate() {
//        mongoDatabase = MongoUtil.getMongoConn();
//        MongoCollection<Document> article = mongoDatabase.getCollection("article");
//        Document document = new Document();
//        Date date = new Date(new java.util.Date().getTime());
//        System.out.println(date);
//        document.append("date", date);
//        System.out.println(document);
//
//        Document update = new Document();
//        update.append("id", 1);
//        int id = article.findOneAndUpdate(new BasicDBObject("_id", 0), new Document("$inc", update)).getInteger("id");
//        System.out.println(id);
//
//        Document add = new Document();
//        add.append("date", date);
//        add.append("_id", id);
//        System.out.println(add);
//        article.insertOne(add);
//
//        Document find = new Document();
//        find.append("_id", id);
//        java.util.Date date1 = article.find(find).iterator().next().getDate("date");
//        System.out.println(date1);
//    }
}