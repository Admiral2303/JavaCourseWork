package controllers;

import classes.Db;
import classes.Doctor;
import classes.Patient;
import classes.Proxy;
import db.DbContex;
import db.DbPatient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddPatientController {

    @FXML
    public TextField nameText;
    @FXML
    public TextField surnameText;
    @FXML
    public TextField yearText;
    @FXML
    public TextField addresText;
    @FXML
    public TextField loginText;
    @FXML
    public TextField passwordText;

    DbContex db = Db.getInstance(2);

    private Patient patient = new Patient();

    Proxy proxy = new Proxy(patient, db);

    public void actionSave(ActionEvent actionEvent) {
        System.out.println();
        if (!nameText.getText().equals("") && !surnameText.getText().equals("") && !yearText.getText().equals("")
                && !addresText.getText().equals("") && !loginText.getText().equals("") && !passwordText.getText().equals("")) {
            patient.setAddress(addresText.getText());
            patient.setName(nameText.getText());
            patient.setSurname(surnameText.getText());
            patient.setYear(Integer.parseInt(yearText.getText()));
            patient.setLogin(loginText.getText());
            patient.setPass(passwordText.getText());
            patient.setHealth(100);
            proxy.Add(patient);
            actionClose(actionEvent);
        } else {
            allertDialog("Incorect data");
        }

    }

    public void allertDialog(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(text);
        alert.setContentText("Ooops, there was an error!");
        alert.show();
    }

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
        loadWindow("/mainmenu.fxml");
    }


    public void setPatient(Patient patient) {
        this.patient = patient;
        nameText.setText(patient.getName());
        surnameText.setText(patient.getSurname());
        yearText.setText(Integer.toString(patient.getYear()));
        addresText.setText(patient.getAddress());

    }


    void loadWindow(String loc) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage();
            stage.setTitle("sfdsf");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Error loading" + ex);
        }
    }

    public Patient getPatient() {
        return patient;
    }
}
