package classes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

abstract public class Human implements HumanInterface{
    public String _id;
    protected SimpleStringProperty name = new SimpleStringProperty("");
    protected SimpleStringProperty surname = new SimpleStringProperty("");
    protected SimpleIntegerProperty year = new SimpleIntegerProperty(0);
    protected SimpleStringProperty login = new SimpleStringProperty("");
    protected SimpleStringProperty pass = new SimpleStringProperty("");

    public Human(String name, String surname, int year, String login, String pass, String _id) {
        this._id = _id;
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.year = new SimpleIntegerProperty(year);
        this.login = new SimpleStringProperty(login);
        this.pass = new SimpleStringProperty(pass);
    }

    protected Human() {
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public void setPass(String pass) {
        this.pass.set(pass);
    }

    public String getName() {

        return name.get();
    }

    public String getSurname() {
        return surname.get();
    }

    public int getYear() {
        return year.get();
    }

    public String getLogin() {
        return login.get();
    }

    public String getPass() {
        return pass.get();
    }

    public SimpleStringProperty nameProperty() {

        return name;
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public SimpleIntegerProperty yearProperty() {
        return year;
    }

    public SimpleStringProperty loginProperty() {
        return login;
    }

    public SimpleStringProperty passProperty() {
        return pass;
    }

}
