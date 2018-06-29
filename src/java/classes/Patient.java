package classes;

import classes.Disease.Decorator;
import classes.Disease.Disease;
import classes.Medicine.Medicine;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Patient extends Human {
    private SimpleStringProperty address = new SimpleStringProperty("");
    private SimpleStringProperty state = new SimpleStringProperty("");
    private SimpleIntegerProperty health = new SimpleIntegerProperty(0);
    private SimpleStringProperty medicineName = new SimpleStringProperty("");
    private SimpleStringProperty diseaseName = new SimpleStringProperty("");
    private SimpleStringProperty status = new SimpleStringProperty("");
    private Decorator disease;
    private Medicine medicine;

    public void makeDamage(int damage) {
        if ((this.health.get() - damage) >= 0) {
            this.setHealth(this.health.get() - damage);
        } else if(this.health.get() != 0 && (this.health.get() - damage) < 0){
            this.setHealth(0);
        }
    }


    public void execute() {
        medicine.addHealth(this);
    }


    public Patient() {
    }

    public Patient(String address1, String name, String surname, int year, String _id) {
        this.address.set(address1);
        this.name.set(name);
        this.surname.set(surname);
        this.year.set(year);
        this._id = _id;
        this.health.set(100);
        this.diseaseName.set("");
    }

    public Patient(String address1, String name, String surname, int year, String login, String pass, String _id, Decorator disease) {
        this.address.set(address1);
        this.name.set(name);
        this.surname.set(surname);
        this.year.set(year);
        this.login.set(login);
        this.pass.set(pass);
        this.state.set("");
        this.health.set(100);
        this.disease = disease;
        this._id = _id;
        this.health.set(100);
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Decorator disease) {
        this.disease = disease;
    }


    public String getState() {
        return state.get();
    }

    public void setState(String state) {
        this.state.set(state);
    }

    public SimpleStringProperty stateProperty() {
        return state;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public int getHealth() {
        return health.get();
    }

    public void setHealth(int health) {
        this.health.set(health);
    }

    public SimpleIntegerProperty healthProperty() {
        return health;
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

    public String getDiseaseName() {
        return diseaseName.get();
    }

    public SimpleStringProperty diseaseNameProperty() {
        return diseaseName;
    }


    public void setDiseaseName(String diseaseName) {
        if (!diseaseName.equals("")) {
            this.diseaseName.set(diseaseName);
            this.state.set("ill");
        } else {
            this.state.set("healthy");
        }
    }



    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
    public String getStatus() {
        return status.get();
    }

    public void addHealth(int health) {
        if ((this.health.get() + health) <= 100) {
            this.health.set(this.health.get() + health);
        }else if(this.health.get() != 100 && (this.health.get() + health) > 100){
            this.setHealth(100);
        }
    }
    public SimpleStringProperty statusProperty() {
        return status;
    }
}
