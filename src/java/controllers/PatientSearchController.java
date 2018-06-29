package controllers;

import classes.Db;
import classes.Doctor;
import classes.Human;
import classes.Patient;
import db.DbContex;
import db.DbDoctor;
import db.DbPatient;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.lang.reflect.Method;

public class PatientSearchController {
    @FXML
    public TableColumn<Patient, String> nameColoumn;
    @FXML
    public TableColumn<Patient, String> surnameColoumn;
    @FXML
    public TableColumn<Patient, String> addressColoumn;
    @FXML
    public Button searchButton;
    @FXML
    public TableView<Human> docTable;
    @FXML
    public CustomTextField searchText;
    @FXML
    public Button addButton;


    public static ObservableList<Human> humansList;

    private ObservableList<Human> backupList;

    DbContex db = Db.getInstance(2);
    DbContex dbDoctor = Db.getInstance(1);


    public void initialize() throws IOException {
        addPatientsList();
        docTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        nameColoumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("name"));
        surnameColoumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("surname"));
        addressColoumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("address"));
        docTable.setItems(humansList);
        fillTable();
        setupClearButtonField(searchText);
    }

    private ObservableList<Human> addPatientsList(){
        ObservableList<Human> humans = FXCollections.observableArrayList(db.get());
        humansList =  FXCollections.observableArrayList();
        ObservableList<Human> doctors = FXCollections.observableArrayList(dbDoctor.get());
        boolean flag = false;
        for(Human h: humans){
            for(Human h1: DoctorController.doctor.getHumansList()){
                if(h.getName().equals(h1.getName()) && h.getSurname().equals(h1.getSurname()) && h.getYear() == h1.getYear()){
                    flag = true;
                }
            }
            if(flag == true){
                flag = false;
                continue;
            }
            humansList.add(h);
        }
        for(Human doc: doctors){
            for(String id : ((Doctor)doc).getPatientId()){
                for(Human h: humansList){
                    if(id.equals(h._id)){
                        humansList.remove(h);
                    }
                }
            }
        }
        return null;
    }

    private void fillTable() {
        backupList = FXCollections.observableArrayList();
        backupList.addAll(humansList);
    }

    private void setupClearButtonField(CustomTextField customTextField) {
        try {
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField", TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(null, customTextField, customTextField.rightProperty());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void actionSearch(ActionEvent actionEvent) {

    }

    public void addPatient(ActionEvent actionEvent) {
        Patient selectedPatient = (Patient) docTable.getSelectionModel().getSelectedItem();
        DoctorController.doctor.Add(selectedPatient);
        dbDoctor.addPatient(DoctorController.doctor._id, selectedPatient._id);
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }
}
