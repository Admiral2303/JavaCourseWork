package classes.Disease;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class DiseaseBundleCashe {
    private ObservableList<Decorator> diseases = FXCollections.observableArrayList();

    public DiseaseBundleCashe(){
        Decorator headDisease = new HeadDisease();
        Decorator heartDisease = new HeartDisease();
        Decorator bodyDisease = new BodyDisease();
        Illness headache = new Illness("headache", "When you have some pain in your head");
        headDisease.setDisease(headache);
        Illness arrhythmia = new Illness("arrhythmia", "When you have some problems with your heart");
        heartDisease.setDisease(arrhythmia);
        Illness herpes = new Illness("herpes", "When you have some problems with your skin");
        bodyDisease.setDisease(herpes);
        diseases.add(headDisease);
        diseases.add(heartDisease);
        diseases.add(bodyDisease);
    }

    public ObservableList<Decorator> getDiseases() {
        return diseases;
    }

    public ObservableList<String> getDiseasesName() {
        ObservableList<String> names = FXCollections.observableArrayList();
        for(Decorator d: diseases){
            names.add(d.getName());
        }
        return names;
    }

    public Decorator get(String name) throws CloneNotSupportedException {
        for(Decorator d: diseases){
            if(d.getName().equals(name)){
                return (Decorator) d.cloneDisease();
            }
        }
        return null;
    }

}
