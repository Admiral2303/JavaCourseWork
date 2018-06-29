package classes.Observer;

import classes.Patient;

abstract class Observer {
    protected Control control;
    public abstract void update();
}