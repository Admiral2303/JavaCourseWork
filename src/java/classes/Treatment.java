package classes;

import classes.Medicine.Medicine;

public class Treatment {
    public void setExecutor(Patient patient, Medicine medicine){
        patient.setMedicine(medicine);
    }
    public void execute(Patient patient){
        patient.execute();
    }
}
