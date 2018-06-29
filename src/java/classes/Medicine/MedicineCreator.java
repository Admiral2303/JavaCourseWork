package classes.Medicine;


import classes.Disease.*;


public class MedicineCreator {
    public Medicine FactoryMethod(Disease disease){
        if(disease instanceof HeadDisease){
            return new HeadPills("citramon");
        } else if(disease instanceof HeartDisease){
            return new HeartMedications("valeriana");
        } else if(disease instanceof  BodyDisease){
            return new BodyMedications("bodytablet");
        } else{
            return new DefaultPills("aspirin");
        }
    }
}
