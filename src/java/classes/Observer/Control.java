package classes.Observer;

import classes.Patient;

import java.util.ArrayList;
import java.util.List;

public class Control {
    private List<Observer> observers = new ArrayList<>();
    public Patient patient;

    public void add(Observer o) {
        observers.add(o);
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        execute();
    }


    private void execute() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
