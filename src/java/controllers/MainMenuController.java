package controllers;

import classes.Doctor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    @FXML
    public Button patientButton;
    @FXML
    public Button registrationButton;
    @FXML
    private Button maindocButtom;
    @FXML
    private Button doctorButton;

    private Stage mainDoctorStage;

    private Stage doctorStage;




    private void showRegistration(ActionEvent actionEvent) throws IOException {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/addpatient.fxml"));
            stage.setTitle("Information");
            stage.setMinHeight(150);
            stage.setMaxWidth(500);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            Node source = (Node) actionEvent.getSource();
            Stage localstage = (Stage) source.getScene().getWindow();
            localstage.hide();
            stage.show();
    }


    public void actionButtonPressed(javafx.event.ActionEvent actionEvent) throws IOException {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) return;

        Button clickedButton = (Button) source;

        javafx.stage.Window parentWindow = ((Node) actionEvent.getSource()).getScene().getWindow();

        switch (clickedButton.getId()) {
            case "maindocButtom":
                showMaindoctor(actionEvent);
                break;
            case "doctorButton":
                break;
            case "patientButton":
                showLogin(actionEvent);
                break;
            case "registrationButton":
                showRegistration(actionEvent);
                break;
        }
    }
    private void showMaindoctor(ActionEvent actionEvent) throws IOException {
        if (mainDoctorStage == null) {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/maindoctor.fxml"));
            stage.setTitle("Information");
            stage.setMinHeight(150);
            stage.setMaxWidth(500);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            Node source = (Node) actionEvent.getSource();
            Stage localstage = (Stage) source.getScene().getWindow();
            localstage.hide();
            stage.show();
        }
    }

    private void showLogin(ActionEvent actionEvent) throws IOException {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
            stage.setTitle("Information");
            stage.setMinHeight(150);
            stage.setMaxWidth(500);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            Node source = (Node) actionEvent.getSource();
            Stage localstage = (Stage) source.getScene().getWindow();
            localstage.hide();
            stage.show();
    }

}
