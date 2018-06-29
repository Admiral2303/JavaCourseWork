package controllers;

import classes.Db;
import classes.Doctor;
import classes.Human;
import db.DbContex;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class DoctorViewController {


    @FXML
    private TableColumn<Doctor, String> nameColoumn;
    @FXML
    private TableColumn<Doctor, String> surnameColoumn;
    @FXML
    private TableColumn<Doctor, String> positionColoumn;
    @FXML
    private TableColumn<Doctor, Integer> cabinetColoumn;
    @FXML
    private TableView<Human> docTable;
    @FXML
    private CustomTextField searchText;
    @FXML
    private Button searchButton;

    private ObservableList<Human> humansList = FXCollections.observableArrayList();

    static DbContex db = Db.getInstance(1);
    private ObservableList<Human> backupList;

    public void initialize() throws IOException {
        docTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        nameColoumn.setCellValueFactory(new PropertyValueFactory<Doctor, String>("name"));
        surnameColoumn.setCellValueFactory(new PropertyValueFactory<Doctor, String>("surname"));
        positionColoumn.setCellValueFactory(new PropertyValueFactory<Doctor, String>("position"));
        cabinetColoumn.setCellValueFactory(new PropertyValueFactory<Doctor, Integer>("cabinet"));

        ArrayList<Human> doctors = db.get();
        for(Human h : doctors){
            humansList.add((Doctor) h);
        }
        docTable.setItems(humansList);
        fillTable();

        setupClearButtonField(searchText);

    }



    public void actionSearch(ActionEvent actionEvent) {
        humansList.clear();
        for (Human person : backupList) {
            if (person.getName().toLowerCase().contains(searchText.getText().toLowerCase()) ||
                    person.getSurname().toLowerCase().contains(searchText.getText().toLowerCase()) ||
                    ((Doctor)person).getPosition().toLowerCase().contains(searchText.getText().toLowerCase())) {
                humansList.add(person);
            }
        }
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


    private void fillTable() {
        backupList = FXCollections.observableArrayList();
        backupList.addAll(humansList);
    }
}
