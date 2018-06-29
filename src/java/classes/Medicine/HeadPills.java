package classes.Medicine;

import classes.Patient;

public class HeadPills extends Medicine {
    private static int addHp = 4;
    public HeadPills(String name) {
        super(name);
    }

    @Override
    public void addHealth(Patient patient) {
        if ((patient.getHealth() + addHp) <= 100) {
            patient.setHealth(patient.getHealth() + addHp);
        }else if(patient.getHealth() != 100 && (patient.getHealth() + addHp) > 100){
            patient.setHealth(100);
        }
        //patient.addHealth(addHp);
    }
}
