package classes.Disease;

import classes.Patient;

public class HeadDisease extends Decorator implements Cloneable  {
    static int damage = 5;
    @Override
    public void makeDamage(Patient patient) {
        if(disease != null){
            disease.makeDamage(patient);
            patient.makeDamage(damage);
        }
    }
    @Override
    public HeadDisease cloneDisease() throws CloneNotSupportedException {
        return (HeadDisease)super.clone();
    }
}
