package classes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CardMember {
    private SimpleStringProperty patient_id = new SimpleStringProperty("");
    private SimpleStringProperty diseaseName = new SimpleStringProperty("");
    private SimpleStringProperty medicineName = new SimpleStringProperty("");
    private SimpleIntegerProperty health = new SimpleIntegerProperty(0);
    private SimpleStringProperty date = new SimpleStringProperty("");
    private SimpleStringProperty state = new SimpleStringProperty("");
    private SimpleStringProperty status = new SimpleStringProperty("");

    public String getState() {
        return state.get();
    }

    public SimpleStringProperty stateProperty() {
        return state;
    }

    public void setState(String state) {
        this.state.set(state);
    }



    public CardMember(String patient_id, String diseaseName, String medicineName, String date, String state, int health, String status){
        this.patient_id.set(patient_id);
        this.diseaseName.set(diseaseName);
        this.medicineName.set(medicineName);
        this.date.set(date);
        this.state.set(state);
        this.health.set(health);
       // this.status.set(status);
    }
    public String getPatient_id() {
        return patient_id.get();
    }

    public SimpleStringProperty patient_idProperty() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id.set(patient_id);
    }

    public String getDiseaseName() {
        return diseaseName.get();
    }

    public SimpleStringProperty diseaseNameProperty() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName.set(diseaseName);
    }

    public String getMedicineName() {
        return medicineName.get();
    }

    public SimpleStringProperty medicineNameProperty() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName.set(medicineName);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public int getHealth() {
        return health.get();
    }

    public SimpleIntegerProperty healthProperty() {
        return health;
    }

    public void setHealth(int health) {
        this.health.set(health);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
