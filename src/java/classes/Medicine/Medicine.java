package classes.Medicine;

import classes.Patient;

public abstract class Medicine {
    public String name;



    public Medicine(String name) {
        this.name = name;

    }

    public abstract void addHealth(Patient patient);

}
