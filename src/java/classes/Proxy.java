package classes;

import db.DbContex;
import db.DbDoctor;
import db.DbProcess;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Proxy implements InterfComposite {
    Composite composite;
    Human human;
    DbContex db;

    public Proxy(Composite composite, DbContex db) {
        this.composite = composite;
        this.db = db;
    }

    public Proxy(Human human, DbContex db){
        this.human = human;
        this.db = db;
    }



    @Override
    public boolean Add(Human human) {
        if(human instanceof Patient){
            if(checkPatient(human)){
                db.add(human);
                return true;
            }
        } else if(human instanceof Doctor){
            if(checkDoctor(human)){
                composite.Add(human);
                db.add(human);
                return true;
            }
        }
        return false;
    }

    public boolean checkPatient(Human human){
        if(checkString(human.getName())
                && checkString(human.getSurname())
                && checkYear( Integer.toString(human.getYear()))){
           return true;
        }
        return false;
    }
    public boolean checkDoctor(Human human){
        if(checkString(human.getName()) && checkString(human.getSurname())
                && checkYear( Integer.toString(human.getYear()))
                && checkCabinet(Integer.toString(((Doctor)human).getCabinet()))){
            return true;
        }
        return false;
    }

    @Override
    public void Remove(Human human) {

    }







    public static boolean checkString(String userNameString){
        Pattern p = Pattern.compile("[A-Z]([a-z])+");
        Matcher m = p.matcher(userNameString);
        return m.matches();
    }

    public static boolean checkYear(String userNameString){
        Pattern p = Pattern.compile("[1-9][0-9][0-9][0-9]");
        Matcher m = p.matcher(userNameString);
        return m.matches();
    }
    public static boolean checkCabinet(String userNameString){
        Pattern p = Pattern.compile("[1-9][0-9][0-9]");
        Matcher m = p.matcher(userNameString);
        return m.matches();
    }

}
