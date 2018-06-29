package controllers;

import classes.Doctor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ConfirmDialogController {
    @FXML
    public Button okButton;
    @FXML
    public Button cancelButton;

    private boolean state;

    public boolean getState(){
        return this.state;
    }

    public void actionButtonPressed(javafx.event.ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) return;
        Button clickedButton = (Button) source;
        switch (clickedButton.getId()) {
            case "okButton":
                this.state = true;
                break;

            case "cancelButton":
                this.state = false;
                break;


        }
    }
}
