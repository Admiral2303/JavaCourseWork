package db;

import classes.Human;
import classes.Patient;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public class DbContex {
    private DbProcess dbProcess;

    public DbContex(DbProcess db){
        this.dbProcess = db;
    }
    public ArrayList<Human> get(){
        return dbProcess.get();
    }
    public void add(Human human){
        dbProcess.add(human);
    }
    public void remove(String id){
        dbProcess.remove(id);
    }
    public void update(String id, Human human){
        dbProcess.update(id, human);
    }
    public String getId(Human human){
        return dbProcess.getId(human);
    }
    public void addPatient(String doctorId, String patientId){
        dbProcess.addPatient(doctorId, patientId);
    }
    public Patient getById(String id){
        return dbProcess.getById(id);
    }

}
