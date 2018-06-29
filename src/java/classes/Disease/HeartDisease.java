package classes.Disease;

import classes.Patient;

public class HeartDisease extends Decorator implements Cloneable {
    static int damage = 3;
    @Override
    public void makeDamage(Patient patient) {
        if (disease != null) {
            disease.makeDamage(patient);
            patient.makeDamage(damage);
        }
    }

    @Override
    public HeartDisease cloneDisease() throws CloneNotSupportedException {
        return (HeartDisease) super.clone();
    }
}
