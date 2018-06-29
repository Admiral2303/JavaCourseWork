package db;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Iterator;

public class database {
    public static void main( String args[] ) {
        // Creating a Mongo client
        MongoClient mongo = new MongoClient("localhost", 27017);
        MongoDatabase database = mongo.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("test");

        FindIterable<Document> iterDoc = collection.find();
        int i = 1;

        // Getting the iterator
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            Document doc = (Document) it.next();
            //System.out.println(it.next());
            System.out.println(doc.getObjectId("_id"));

            i++;
        }
//        Document document = new Document("title", "MongoDB")
//                .append("id", 1)
//                .append("description", "database")
//                .append("likes", 100)
//                .append("url", "http://www.tutorialspoint.com/mongodb/")
//                .append("by", "tutorials point");
//        collection.insertOne(document);
//        System.out.println("Document inserted successfully");

    }
}
