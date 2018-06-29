package controllers;

import classes.*;
import classes.Disease.Decorator;
import classes.Disease.Disease;
import db.DbCard;
import db.DbContex;
import db.DbDoctor;
import db.DbPatient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sun.applet.Main;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {
    @FXML
    public TextField loginText;
    @FXML
    public PasswordField passwordText;

    private FXMLLoader fxmlLoader = new FXMLLoader();

    private Parent fxmlEdit;
    private Stage mainStage;

    private DoctorController doctorController;
    private Stage doctorStage;

    private DbContex dbPatient =  Db.getInstance(2);
    private DbContex dbDoctor =  Db.getInstance(1);
    private DbCard dbCard = Db.getDbCard();

    @FXML
    public void actionSave(ActionEvent actionEvent) throws IOException, CloneNotSupportedException {
        ArrayList<Human> doctors = dbDoctor.get();
        ArrayList<Human> patients = dbPatient.get();
        for(Human h : doctors){
            System.out.println(h.getLogin() + "   " + loginText.getText() + "   " + h.getPass() + "  " + passwordText.getText());
            if(h.getLogin().equals(loginText.getText()) && h.getPass().equals(passwordText.getText())){
                Composite doc = new Composite(h.getName(), h.getSurname(), h.getYear(),h.getLogin(),h.getPass(),h._id);
                for(String id : ((Doctor)h).getPatientId()){
                    Patient patient = loadPatient(id);
                    doc.Add(patient);
                }
                DoctorController.doctor = doc;
                loadWindow("/doctor.fxml");
                Node sourceN = (Node) actionEvent.getSource();
                Stage stage = (Stage) sourceN.getScene().getWindow();
                stage.hide();
            }
        }
        for(Human h: patients){
            if(h.getLogin().equals(loginText.getText()) && h.getPass().equals(passwordText.getText())) {
                Patient patient = new Patient(((Patient) h).getAddress(), h.getName(), h.getSurname(), h.getYear(), h._id);
                PatientController.patient = patient;
                loadWindow("/patient.fxml");
            }
        }
        Node sourceN = (Node) actionEvent.getSource();
        Stage stage = (Stage) sourceN.getScene().getWindow();
        stage.hide();
    }


    public Patient loadPatient(String id) throws CloneNotSupportedException {
        Patient patient = dbPatient.getById(id);
        CardMember card =  dbCard.getCardMemberbyId(patient._id);
        String disease = card.getDiseaseName();
        Decorator disease1 = DoctorController.diseases.get(disease);
        if(disease1 != null) {
            patient.setDisease(disease1);
            patient.setDiseaseName(disease1.getName());
        }
        String medicineName = card.getMedicineName();
        if(medicineName != null) {
            patient.setMedicineName(medicineName);
        }
        int health = card.getHealth();
        if(health != -1) {
            patient.setHealth(health);
        }
        String status = card.getStatus();
        if(status != null) {
            patient.setStatus(status);
        }
        return patient;
    }

    void loadWindow(String loc) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage();
            stage.setTitle("Doctor");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Error loading"+ex);
        }
    }

    public void actionClose(ActionEvent actionEvent) {
        Node sourceN = (Node) actionEvent.getSource();
        Stage stage = (Stage) sourceN.getScene().getWindow();
        stage.hide();
        loadWindow("/mainmenu.fxml");
    }
}
