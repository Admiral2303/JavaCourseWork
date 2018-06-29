package classes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Doctor extends Human {
    private SimpleStringProperty position = new SimpleStringProperty("");
    private ObservableList<String> patientsIds = FXCollections.observableArrayList();
    private SimpleIntegerProperty cabinet = new SimpleIntegerProperty(0);

    public int getCabinet() {
        return cabinet.get();
    }

    public SimpleIntegerProperty cabinetProperty() {
        return cabinet;
    }

    public void setCabinet(int cabinet) {
        this.cabinet.set(cabinet);
    }





    public String getPosition() {
        return position.get();
    }

    public SimpleStringProperty positionProperty() {
        return position;
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public Doctor(){}
    public Doctor(String position, String name, String surname, int year, String login, String pass, String _id, int cabinet){
        this.position.set(position);
        this.name.set(name);
        this.surname.set(surname);
        this.year.set(year);
        this.login.set(login);
        this.pass.set(pass);
        this.cabinet.set(cabinet);
        this._id = _id;
    }
    public  void setPatientsId(String id){
        patientsIds.add(id);
    }
    public ObservableList<String> getPatientId(){
        return this.patientsIds;
    }


}
