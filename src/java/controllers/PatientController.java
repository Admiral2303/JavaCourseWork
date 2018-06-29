package controllers;

import classes.*;
import db.DbCard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class PatientController {
    @FXML
    public Label nameLabel;
    @FXML
    public TableColumn<CardMember, String> dateColoumn;
    @FXML
    public TableColumn<CardMember, String> diseaseColoumn;
    @FXML
    public TableColumn<CardMember, String> medicineColoumn;
    @FXML
    public TableColumn<CardMember, String> stateColoumn;
    @FXML
    public Label surnameLabel;
    @FXML
    public Button exitButton;
    @FXML
    public Button doctorsViewButton;

    @FXML
    private TableView<classes.CardMember> docTable;

    DbCard db = Db.getDbCard();
    public static Patient patient;
    ObservableList<CardMember> cards = FXCollections.observableArrayList();

    public void initialize() throws IOException {
        nameLabel.setText("name: " + patient.getName());
        surnameLabel.setText("surname: " + patient.getSurname());
        docTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        dateColoumn.setCellValueFactory(new PropertyValueFactory<CardMember, String>("date"));
        diseaseColoumn.setCellValueFactory(new PropertyValueFactory<CardMember, String>("diseaseName"));
        medicineColoumn.setCellValueFactory(new PropertyValueFactory<CardMember, String>("medicineName"));
        stateColoumn.setCellValueFactory(new PropertyValueFactory<CardMember, String>("state"));

        ArrayList<CardMember> cardMembers = db.get();
        for(CardMember card : cardMembers){
            if(card.getPatient_id().equals(patient._id)) {
                cards.add(card);
            }
        }
        docTable.setItems(cards);
        Image image = new Image(getClass().getResourceAsStream("/house.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        exitButton.setGraphic(imageView);

    }

    public void exit(ActionEvent actionEvent) {
        Node sourceN = (Node) actionEvent.getSource();
        Stage stage = (Stage) sourceN.getScene().getWindow();
        stage.hide();
        loadWindow("/mainmenu.fxml");
    }

    void loadWindow(String loc) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage();
            stage.setTitle("Patient");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void viewDoctors(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/doctorview.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Information");
        stage.setScene(new Scene(parent));
        stage.show();
    }
}
