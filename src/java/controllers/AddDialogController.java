package controllers;

import classes.Doctor;
import classes.LoginProxy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddDialogController {
    @FXML
    public TextField nameText;
    @FXML
    public TextField surnameText;
    @FXML
    public TextField yearText;
    @FXML
    public TextField positionText;
    @FXML
    public TextField loginText;
    @FXML
    public TextField passwordText;
    @FXML
    public TextField cabinetText;

    private Doctor doctor;


    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public void actionSave(ActionEvent actionEvent) {
        doctor.setPosition(positionText.getText());
        doctor.setName(nameText.getText());
        doctor.setSurname(surnameText.getText());
        doctor.setYear(Integer.parseInt(yearText.getText()));
        doctor.setLogin(loginText.getText());
        doctor.setPass(passwordText.getText());
        doctor.setCabinet(Integer.parseInt(cabinetText.getText()));
        LoginProxy proxy = new LoginProxy(loginText.getText(), passwordText.getText());

        if(proxy.check()) {
        if (MaindocControler.proxy.Add(doctor)) {
        } else {
            allertDialog("Format is incorect");
        }
        }else {
            allertDialog("Login incorect");
        }
        actionClose(actionEvent);
    }


    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        nameText.setText(doctor.getName());
        surnameText.setText(doctor.getSurname());
        yearText.setText(Integer.toString(doctor.getYear()));
        positionText.setText(doctor.getPosition());
        loginText.setText(doctor.getLogin());
        passwordText.setText(doctor.getPass());
        cabinetText.setText(Integer.toString(doctor.getCabinet()));
    }

    public void allertDialog(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(text);
        alert.setContentText("Ooops, there was an error!");

        alert.showAndWait();
    }

    public Doctor getDoctor() {
        return doctor;
    }
}
