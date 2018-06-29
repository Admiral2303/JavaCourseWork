package classes.Disease;

import classes.Medicine.Medicine;
import classes.Patient;

public abstract class Disease {
    public String name;
    protected String description;

    public abstract void makeDamage(Patient patient);
    public abstract Disease cloneDisease() throws CloneNotSupportedException;

}
