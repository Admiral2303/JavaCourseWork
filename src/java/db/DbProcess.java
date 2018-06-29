package db;

import classes.Human;
import classes.Patient;

import java.util.ArrayList;


public interface DbProcess {
    public ArrayList<Human> get();

    public void add(Human human);

    public void remove(String id);

    public void update(String id, Human human);

    public String getId(Human human);

    public void addPatient(String doctorId, String patientId);

    public Patient getById(String id);
}
