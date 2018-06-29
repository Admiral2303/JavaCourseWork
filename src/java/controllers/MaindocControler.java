package controllers;


import classes.*;
import classes.Composite;
import db.DbContex;
import db.DbDoctor;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

import javax.print.Doc;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Optional;

public class MaindocControler {
    @FXML
    public Button exitButton;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private CustomTextField searchText;
    @FXML
    private Button searchButton;
    @FXML
    private TableView<classes.Human> docTable;
    @FXML
    private Label labelCount;

    @FXML
    private TableColumn<Doctor, String> nameColoumn;
    @FXML
    private TableColumn<Doctor, String> surnameColoumn;
    @FXML
    private TableColumn<Doctor, String> positionColoumn;
    @FXML
    private TableColumn<Doctor, Integer> cabinetColoumn;


    private Parent fxmlEdit;

    private FXMLLoader fxmlLoader = new FXMLLoader();

    private AddDialogController addDialogController;

    private Stage addDialogStage;

    private Stage mainStage;

    private ObservableList<Human> backupList;

    static DbContex db = Db.getInstance(1);
    static Composite maindoctor = new Composite("position", "name", 1980, "Admiral", "2303", "id");

    static InterfComposite proxy = new Proxy(maindoctor,db);

    public void initialize() throws IOException {
        docTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        nameColoumn.setCellValueFactory(new PropertyValueFactory<Doctor, String>("name"));
        surnameColoumn.setCellValueFactory(new PropertyValueFactory<Doctor, String>("surname"));
        positionColoumn.setCellValueFactory(new PropertyValueFactory<Doctor, String>("position"));
        cabinetColoumn.setCellValueFactory(new PropertyValueFactory<Doctor, Integer>("cabinet"));
        ArrayList<Human> doctors = db.get();
        for(Human h : doctors){
            maindoctor.Add((Doctor) h);
        }
        docTable.setItems(maindoctor.getHumansList());
        fillTable();
        initListeners();
        setupClearButtonField(searchText);
        updateCountLabel();
        javafx.scene.image.Image image = new Image(getClass().getResourceAsStream("/house.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        exitButton.setGraphic(imageView);

        fxmlLoader.setLocation(getClass().getResource("/add.fxml"));
        fxmlEdit = fxmlLoader.load();
        addDialogController = fxmlLoader.getController();
    }


    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void updateCountLabel() {
        labelCount.setText("Кількість лікарів: " + maindoctor.getHumansList().size());
    }

    private void fillTable() {
        backupList = FXCollections.observableArrayList();
        backupList.addAll(maindoctor.getHumansList());
    }

    private void initListeners() {
        maindoctor.getHumansList().addListener((ListChangeListener) (c) -> {
            updateCountLabel();
        });

        docTable.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                if (event.getClickCount() == 2) {
                    addDialogController.setDoctor((Doctor) docTable.getSelectionModel().getSelectedItem());
                    showDialog();
                }
            }
        });


    }







    public void actionButtonPressed(javafx.event.ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) return;
        Button clickedButton = (Button) source;
        switch (clickedButton.getId()) {
            case "addButton":
                addDialogController.setDoctor(new Doctor());
                showDialog();
                break;
            case "updateButton":
                UpdateController.doctor = (Doctor) docTable.getSelectionModel().getSelectedItem();
                loadWindow("/updatedoctor.fxml");
                break;
            case "deleteButton":
                if(dialogState("Do you want to delete this doctor")) {
                    maindoctor.Remove(docTable.getSelectionModel().getSelectedItem());
                    db.remove(db.getId(docTable.getSelectionModel().getSelectedItem()));
                    fillTable();
                }
                break;
            case "exitButton":
                maindoctor.removeAll();
                Node sourceN = (Node) actionEvent.getSource();
                Stage stage = (Stage) sourceN.getScene().getWindow();
                stage.hide();
                loadWindow("/mainmenu.fxml");
                break;
        }
    }


    public void actionSearch(javafx.event.ActionEvent actionEvent) {
        maindoctor.getHumansList().clear();
        for (Human person : backupList) {
            if (person.getName().toLowerCase().contains(searchText.getText().toLowerCase()) ||
                    person.getSurname().toLowerCase().contains(searchText.getText().toLowerCase()) ||
                    ((Doctor)person).getPosition().toLowerCase().contains(searchText.getText().toLowerCase())) {
                maindoctor.getHumansList().add(person);
            }
        }
    }


    public boolean dialogState(String text){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(text);
        alert.setContentText("Are you ok with this?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }



    void loadWindow(String loc) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage();
            stage.setTitle("ddsd");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    private void showDialog() {
        if (addDialogStage == null) {
            addDialogStage = new Stage();
            addDialogStage.setTitle("Information");
            addDialogStage.setMinHeight(150);
            addDialogStage.setMinWidth(300);
            addDialogStage.setResizable(false);
            addDialogStage.setScene(new Scene(fxmlEdit));
            addDialogStage.initModality(Modality.WINDOW_MODAL);
            addDialogStage.initOwner(mainStage);
        }
        addDialogStage.showAndWait();
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

}
