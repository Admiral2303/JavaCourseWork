package classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Composite extends Human implements InterfComposite {
    private ObservableList<Human> humansList = FXCollections.observableArrayList();

    public Composite(){}
    public Composite(String name, String surname, int year, String login, String pass, String _id) {
        super(name, surname, year, login, pass, _id);
    }
    @Override
    public boolean Add(Human c) {
        humansList.add(c);
        return true;
    }
    @Override
    public void Remove(Human c) {
        humansList.remove(c);
    }
    public void removeAll(){ ;
        humansList.clear();
    }

    public ObservableList<Human> getHumansList(){return humansList;}
}
