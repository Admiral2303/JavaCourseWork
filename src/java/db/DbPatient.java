package db;

import classes.Human;
import classes.Patient;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Iterator;

public class DbPatient implements DbProcess {
    private MongoClient mongo = new MongoClient( "localhost" , 27017 );
    private MongoDatabase database = mongo.getDatabase("hospital");

    // Retrieving a collection
    MongoCollection<Document> collection = database.getCollection("patient");
    DbCard dbCard = new DbCard();

    public ArrayList<Human> get() {
        ArrayList<Human> patients = new ArrayList<Human>();

        FindIterable<Document> iterDoc = collection.find();

        Iterator it = iterDoc.iterator();
        while(it.hasNext()) {
            Document doc = (Document) it.next();
            Patient patient = new Patient();
            patient.setName(doc.getString("name"));
            patient.setSurname(doc.getString("surname"));
            patient.setAddress(doc.getString("address"));
            patient.setYear(doc.getInteger("year"));
            patient.setLogin(doc.getString("login"));
            patient.setPass(doc.getString("pass"));
            patient.setHealth(doc.getInteger("health"));
            patient._id = doc.getObjectId("_id").toString();
            patients.add(patient);
        }

        return patients;
    }

    public void add(Human human) {
        if(human != null) {
            Document document = new Document("name", human.getName())
                    .append("surname", human.getSurname())
                    .append("year", human.getYear())
                    .append("address", ((Patient) human).getAddress())
                    .append("login", human.getLogin())
                    .append("health", ((Patient) human).getHealth())
                    .append("pass", human.getPass());
            collection.insertOne(document);
            System.out.println(human.getName());
        } else throw new NullPointerException("human is null");
    }

    public String getId(Human human) {
        FindIterable<Document> iterDoc = collection.find();
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            Document doc = (Document) it.next();
            if(doc.getString("name").equals(human.getName()) && doc.getString("surname").equals(human.getSurname())
                    && doc.getInteger("year") == human.getYear()){
                return doc.getObjectId("_id").toString();
            }
        }
        return null;
    }

    @Override
    public void addPatient(String doctorId, String patientId) {

    }

    public Patient getById(String id) {
        FindIterable<Document> iterDoc = collection.find();
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            Document doc = (Document) it.next();
            if(doc.getObjectId("_id").toString().equals(id)){
                //String disease = dbCard.takePatientDisease(id);
                return new Patient(doc.getString("address"), doc.getString("name"), doc.getString("surname"), doc.getInteger("year"), id);
            }
        }
        return null;
    }

    public void remove(String id) {
        collection.deleteOne(new Document("_id", new ObjectId(id)));
       // collection.deleteOne(Filters.eq("_id", id));
    }

    public void update(String id, Human human) {
        Document document = new Document("name", human.getName())
                .append("surname", human.getSurname())
                .append("year", human.getYear())
                .append("address", ((Patient) human).getAddress())
                .append("login", human.getLogin())
                .append("pass", human.getPass());
       // collection.updateOne(new Document("_id", new ObjectId(id)), document);


        Bson filter = new Document("_id", new ObjectId(id));
        Bson newValue = document;
        Bson updateOperationDocument = new Document("$set", newValue);
        collection.updateOne(filter, updateOperationDocument);
    }
}
