package classes.Disease;

import classes.Medicine.Medicine;
import classes.Patient;

public abstract class Decorator extends Disease {
    protected Disease disease;

    public Disease getDisease() {
        return disease;
    }

    public String getName() {
        return disease.name;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    @Override
    public void makeDamage(Patient patient) {
        if(disease != null){
            disease.makeDamage(patient);
        }
    }
}
