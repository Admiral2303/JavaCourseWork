package db;

import classes.Doctor;
import classes.Human;
import classes.Patient;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class DbDoctor implements DbProcess {
    private MongoClient mongo = new MongoClient( "localhost" , 27017 );
    private MongoDatabase database = mongo.getDatabase("hospital");

    // Retrieving a collection
    MongoCollection<Document> collection = database.getCollection("doctor");


    public ArrayList<Human> get() {
        ArrayList<Human> doctors = new ArrayList<Human>();
        FindIterable<Document> iterDoc = collection.find();
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            Document doc = (Document) it.next();
            Doctor doctor= new Doctor();
            doctor.setName(doc.getString("name"));
            doctor.setSurname(doc.getString("surname"));
            doctor.setPosition(doc.getString("position"));
            doctor.setYear(doc.getInteger("year"));
            doctor.setLogin(doc.getString("login"));
            doctor.setPass(doc.getString("pass"));
            doctor.setCabinet(doc.getInteger("cabinet"));
            doctor._id = doc.getObjectId("_id").toString();
            List<Document> patients = (List<Document>)doc.get("patients");
            for(Document patient: patients){
                doctor.setPatientsId(patient.getString("patient"));
            }
            doctors.add(doctor);
        }
        return doctors;
    }

    public void add(Human human) {
        if(human != null) {
            ArrayList<DBObject> array = new ArrayList< DBObject >();
            Document document = new Document("name", human.getName())
                    .append("surname", human.getSurname())
                    .append("year", human.getYear())
                    .append("position", ((Doctor) human).getPosition())
                    .append("login", human.getLogin())
                    .append("pass", human.getPass())
                    .append("cabinet", ((Doctor) human).getCabinet())
                    .append("patients", array);
            collection.insertOne(document);
            System.out.println(human.getName());
        } else throw new NullPointerException("human is null");
    }

    public String getId(Human human) {
        FindIterable<Document> iterDoc = collection.find();
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            Document doc = (Document) it.next();
            if(doc.getString("name").equals(human.getName()) && doc.getString("surname").equals(human.getSurname())){
                return doc.getObjectId("_id").toString();
            }
        }
        return null;
    }

    public void remove(String id) {
        collection.deleteOne(new Document("_id", new ObjectId(id)));
    }

    public void update(String id, Human human) {
        Document document = new Document("name", human.getName())
                .append("surname", human.getSurname())
                .append("year", human.getYear())
                .append("position", ((Doctor) human).getPosition())
                .append("login", human.getLogin())
                .append("pass", human.getPass())
                .append("cabinet", ((Doctor) human).getCabinet());


        Bson filter = new Document("_id", new ObjectId(id));
        Bson newValue = document;
        Bson updateOperationDocument = new Document("$set", newValue);
        collection.updateOne(filter, updateOperationDocument);
    }

    public Patient getById(String id){
        return null;
    }

    public void addPatient(String doctorId, String patientId){
        collection.updateOne(new Document("_id", new ObjectId(doctorId)),
                new Document("$push", new Document("patients", new Document("patient", patientId))));
    }
}
