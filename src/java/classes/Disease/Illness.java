package classes.Disease;

import classes.Patient;

public class Illness extends Disease {
    public Illness(String name, String description) {
        this.name = name;
        this.description = description;
    }
    @Override
    public void makeDamage(Patient patient) {
        patient.makeDamage(1);
    }

    @Override
    public Disease cloneDisease() throws CloneNotSupportedException {
        return (Illness)super.clone();
    }
}
