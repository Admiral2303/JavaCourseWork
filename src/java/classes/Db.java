package classes;

import db.*;

public class Db {
    //private static Db instance ;
    private static DbContex dbDoctor;
    private static DbContex dbPatient;
    private static DbCard dbCard;

    public static DbContex getInstance(int index) {
        if(index == 1){
            if(dbDoctor == null){
                dbDoctor = new DbContex(new DbDoctor());
            }
            return dbDoctor;
        } else if(index == 2){
            if(dbPatient == null){
                dbPatient = new DbContex(new DbPatient());
            }
            return dbPatient;
        }
        return null;
    }
    public static DbCard getDbCard(){
        if(dbCard == null){
            dbCard = new DbCard();
        }
        return dbCard;
    }

    private Db() {}
}
