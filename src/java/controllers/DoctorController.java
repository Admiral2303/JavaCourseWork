package controllers;

import classes.*;
import classes.Composite;
import classes.Disease.DiseaseBundleCashe;
import classes.*;
import classes.Medicine.MedicineCreator;
import classes.Observer.*;
import classes.Observer.Control;
import db.DbCard;
import db.DbContex;
import db.DbPatient;
import db.DbProcess;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DoctorController {
    @FXML
    public Button addDisease;
    @FXML
    public ComboBox<String> diseaseBox;
    @FXML
    public Button addMedicineButton;
    @FXML
    public Text time;
    @FXML
    private Button addButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button deleteButton;
    @FXML
    private CustomTextField searchText;
    @FXML
    private Button searchButton;
    @FXML
    private TableView<Human> docTable;
    @FXML
    private Label labelCount;
    @FXML
    private TableColumn<Patient, String> nameColoumn;
    @FXML
    private TableColumn<Patient, String> surnameColoumn;
    @FXML
    private TableColumn<Patient, String> adressColoumn;
    @FXML
    public TableColumn<Patient, String> stateColoumn;
    @FXML
    public TableColumn<Patient, String> diseaseColoumn;
    @FXML
    public TableColumn<Patient, String> medicineColoumn;
    @FXML
    public TableColumn<Patient, Integer> healthColoumn;
    @FXML
    public TableColumn<Patient, String> statusColoumn;

    private Parent fxmlEdit;

    private FXMLLoader fxmlLoader = new FXMLLoader();

    private AddPatientController addPatientController;

    private Stage addPatientStage;

    public static Stage mainStage;

    DbContex db =  Db.getInstance(2);

    DbCard dbCard = Db.getDbCard();

    private ObservableList<Human> backupList;

    public static Composite doctor;

    Proxy proxy = new Proxy(doctor,db);

    static DiseaseBundleCashe diseases = new DiseaseBundleCashe();


    Timeline timeline;
    int mins = 0, secs = 0, millis = 0;

    Control control = new Control();
    DelHpObserver h = new DelHpObserver(control);
    AddHpObserver d = new AddHpObserver(control);


    public void setDoctor(Composite doctor){
        DoctorController.doctor = doctor;
    }






    public void initialize() throws IOException {
        docTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        nameColoumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("name"));
        surnameColoumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("surname"));
        adressColoumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("address"));
        stateColoumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("state"));
        diseaseColoumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("diseaseName"));
        medicineColoumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("medicineName"));
        healthColoumn.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("health"));
        statusColoumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("status"));
        addMedicineButton.setDisable(true);
        addDisease.setDisable(true);

        time.setText("00:00:000");
        timeline = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                change(time);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
        timeline.play();
        updateCountLabel();
        docTable.setItems(doctor.getHumansList());
        fillTable();
        initListeners();
        setupClearButtonField(searchText);
        fxmlLoader.setLocation(getClass().getResource("/addpatient.fxml"));
        fxmlEdit = fxmlLoader.load();
        addPatientController = fxmlLoader.getController();
        diseaseBox.setItems(diseases.getDiseasesName());
        diseaseBox.getSelectionModel().selectFirst();
        Image image = new Image(getClass().getResourceAsStream("/house.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        exitButton.setGraphic(imageView);
        docTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            Patient selectedPatient = (Patient) docTable.getSelectionModel().getSelectedItem();
            if(!selectedPatient.getStatus().equals("die")) {
                addMedicineButton.setDisable(false);
                addDisease.setDisable(false);
            } else{
                addMedicineButton.setDisable(true);
                addDisease.setDisable(true);
            }
        });
    }





    public void setMainStage(Stage mainStage) {
        DoctorController.mainStage = mainStage;
    }

    public void updateCountLabel() {
        labelCount.setText("Кількість пацієнтів: " + doctor.getHumansList().size());

    }

    private void fillTable() {
        backupList = FXCollections.observableArrayList();
        backupList.addAll(doctor.getHumansList());
    }

    private void initListeners() {
        doctor.getHumansList().addListener((ListChangeListener) (c) -> {
            updateCountLabel();
        });
    }





    public void actionButtonPressed(javafx.event.ActionEvent actionEvent) throws CloneNotSupportedException {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) return;
        Button clickedButton = (Button) source;
        Patient selectedPatient = (Patient) docTable.getSelectionModel().getSelectedItem();
        javafx.stage.Window parentWindow = ((Node) actionEvent.getSource()).getScene().getWindow();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        switch (clickedButton.getId()) {
            case "addButton":
                loadPatientSearch();
                break;
            case "exitButton":
                for(Human p : doctor.getHumansList()){
                    dbCard.add(new CardMember(p._id, ((Patient)p).getDiseaseName(), ((Patient)p).getMedicineName(),
                            format1.format(cal.getTime()), ((Patient)p).getState(),((Patient)p).getHealth(), ((Patient) p).getStatus()) );
                }
                Node sourceN = (Node) actionEvent.getSource();
                Stage stage = (Stage) sourceN.getScene().getWindow();
                stage.hide();
                loadWindow("/mainmenu.fxml");
                break;
            case "deleteButton":
                break;
            case "addDisease":
                String disease = diseaseBox.getValue();
                System.out.println(disease);
                selectedPatient.setDisease(diseases.get(disease));
                selectedPatient.setDiseaseName(disease);
                selectedPatient.setState("ill");
                selectedPatient.setMedicineName("false");
                dbCard.add(new CardMember(selectedPatient._id, disease, "false", format1.format(cal.getTime()),
                        "ill",selectedPatient.getHealth(),  ((Patient) selectedPatient).getStatus()) );
                break;
            case "addMedicineButton":
                selectedPatient.setMedicineName("true");
                dbCard.add(new CardMember(selectedPatient._id, selectedPatient.getDiseaseName(),
                        "true", format1.format(cal.getTime()), "ill", selectedPatient.getHealth(),
                        ((Patient) selectedPatient).getStatus()) );
                break;
        }
    }

    void loadPatientSearch() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/patientsearch.fxml"));
            Stage stage = new Stage();
            stage.setTitle("sfdsf");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Error loading"+ex);
        }
    }





    public void actionSearch(javafx.event.ActionEvent actionEvent) {
        doctor.getHumansList().clear();

        for (Human person : backupList) {
            if (person.getName().toLowerCase().contains(searchText.getText().toLowerCase()) ||
                    person.getSurname().toLowerCase().contains(searchText.getText().toLowerCase())) {
                doctor.getHumansList().add(person);
            }
        }
    }




    void change(Text text) {
        Date now = new Date();
        if(millis == 1000) {
            secs++;
            if(secs % 5 == 0 ){
                for(Human patient: doctor.getHumansList()) {
                    control.setPatient((Patient)patient);
                }
            }
            millis = 0;
        }
        if(secs == 60) {
            mins++;
            secs = 0;
        }
        text.setText((((mins/10) == 0) ? "0" : "") + mins + ":"
                + (((secs/10) == 0) ? "0" : "") + secs + ":"
                + (((millis/10) == 0) ? "00" : (((millis/100) == 0) ? "0" : "")) + millis++);
    }




    private void showDialog() {
        if (addPatientStage == null) {
            addPatientStage = new Stage();
            addPatientStage.setTitle("Information");
            addPatientStage.setMinHeight(150);
            addPatientStage.setMinWidth(300);
            addPatientStage.setResizable(false);
            addPatientStage.setScene(new Scene(fxmlEdit));
            addPatientStage.initModality(Modality.WINDOW_MODAL);
            addPatientStage.initOwner(mainStage);
        }
        addPatientStage.showAndWait();
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
}
