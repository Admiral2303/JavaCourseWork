package classes.Observer;

import classes.Medicine.Medicine;
import classes.Medicine.MedicineCreator;
import classes.Treatment;

public class DelHpObserver extends Observer {

    public  DelHpObserver(Control control){
        this.control = control;
        this.control.add(this);
    }

    @Override
    public void update() {
        if(!this.control.patient.getDiseaseName().equals("") && this.control.patient.getMedicineName().equals("false")){
            this.control.patient.getDisease().makeDamage(this.control.patient);
            if(this.control.patient.getHealth() == 0){
                this.control.patient.setStatus("die");
            }else{
                this.control.patient.setStatus("");
            }
        }
    }
}
