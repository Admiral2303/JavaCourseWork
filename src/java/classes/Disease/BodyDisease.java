package classes.Disease;

import classes.Patient;

public class BodyDisease extends Decorator implements Cloneable  {
    static int damage = 2;
    @Override
    public void makeDamage(Patient patient) {
        if(disease != null){
            disease.makeDamage(patient);
            patient.makeDamage(damage);
        }
    }
    @Override
    public BodyDisease cloneDisease() throws CloneNotSupportedException {
        return (BodyDisease)super.clone();
    }

}
