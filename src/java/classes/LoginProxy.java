package classes;

import classes.Db;
import controllers.DoctorController;
import controllers.PatientController;
import db.DbContex;

import java.util.ArrayList;

public class LoginProxy {
    public String login;
    public String password;


    private DbContex dbPatient =  Db.getInstance(2);
    private DbContex dbDoctor =  Db.getInstance(1);

    public LoginProxy(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public boolean check(){
        ArrayList<Human> doctors = dbDoctor.get();
        ArrayList<Human> patients = dbPatient.get();
        for(Human h : doctors){
            if(h.getLogin().equals(login)/* && h.getPass().equals(password)*/){
                return false;
            }
        }
        for(Human h: patients){
            if(h.getLogin().equals(login)/* && h.getPass().equals(password)*/) {
               return false;
            }
        }
        return true;
    }

}
