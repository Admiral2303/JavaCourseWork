package db;

import classes.CardMember;
import classes.Doctor;
import classes.Human;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class DbCard {
    private MongoClient mongo = new MongoClient("localhost", 27017);
    private MongoDatabase database = mongo.getDatabase("hospital");

    MongoCollection<Document> collection = database.getCollection("card");


    public ArrayList<CardMember> get() {
        ArrayList<CardMember> cardMembers = new ArrayList<CardMember>();
        FindIterable<Document> iterDoc = collection.find();
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            Document doc = (Document) it.next();
            CardMember cardMember = new CardMember(doc.getString("patient_id"), doc.getString("diseaseName"),
                    doc.getString("medicineName"), doc.getString("date"), doc.getString("state"),
                    doc.getInteger("health"), doc.getString("status"));
            cardMembers.add(cardMember);
        }
        return cardMembers;
    }

    public void add(CardMember cardMember) {
        if (cardMember != null) {
            Document document = new Document("patient_id", cardMember.getPatient_id())
                    .append("diseaseName", cardMember.getDiseaseName())
                    .append("medicineName", cardMember.getMedicineName())
                    .append("date", cardMember.getDate())
                    .append("state", cardMember.getState())
                    .append("health", cardMember.getHealth())
                    .append("status", cardMember.getStatus());
            collection.insertOne(document);
        } else throw new NullPointerException("card is null");
    }


    public CardMember getCardMemberbyId(String _id){
        FindIterable<Document> iterDoc = collection.find(eq("patient_id", _id));
        Iterator it = iterDoc.iterator();
        Document doc = new Document();
        while (it.hasNext()) {
            doc = (Document) it.next();
        }
        CardMember cardMember = new CardMember(doc.getString("patient_id"), doc.getString("diseaseName"),
                doc.getString("medicineName"), doc.getString("date"), doc.getString("state"),
                doc.getInteger("health"), doc.getString("status"));
        return cardMember;
    }

//    public String takePatientDisease(String _id) {
//        FindIterable<Document> iterDoc = collection.find(eq("patient_id", _id));
//        Iterator it = iterDoc.iterator();
//        Document doc = new Document();
//        while (it.hasNext()) {
//            doc = (Document) it.next();
//        }
//        return doc.getString("diseaseName");
//    }
//
//    public String takePatientMedicine(String _id) {
//        FindIterable<Document> iterDoc = collection.find(eq("patient_id", _id));
//        Iterator it = iterDoc.iterator();
//        Document doc = new Document();
//        while (it.hasNext()) {
//            doc = (Document) it.next();
//        }
//        return doc.getString("medicineName");
//    }
//
//    public int takePatientHealth(String _id) {
//        FindIterable<Document> iterDoc = collection.find(eq("patient_id", _id));
//        Iterator it = iterDoc.iterator();
//        Document doc = new Document();
//        while (it.hasNext()) {
//            doc = (Document) it.next();
//        }
//        if(doc.size() != 0) {
//            return doc.getInteger("health");
//        }
//        return -1;
//    }
//
//    public String takePatientStatus(String _id) {
//        FindIterable<Document> iterDoc = collection.find(eq("patient_id", _id));
//        Iterator it = iterDoc.iterator();
//        Document doc = new Document();
//        while (it.hasNext()) {
//            doc = (Document) it.next();
//        }
//        return doc.getString("status");
//    }


    public void remove(String id) {
        collection.deleteOne(new Document("_id", new ObjectId(id)));
    }


}
