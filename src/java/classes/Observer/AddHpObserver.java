package classes.Observer;

import classes.Medicine.Medicine;
import classes.Medicine.MedicineCreator;
import classes.Treatment;

public class AddHpObserver extends Observer {
    static MedicineCreator creator = new MedicineCreator();

    public  AddHpObserver(Control control){
        this.control = control;
        this.control.add(this);
    }

    @Override
    public void update() {
        if (this.control.patient.getMedicineName().equals("true")) {
            Medicine medicine = creator.FactoryMethod(this.control.patient.getDisease());
            Treatment treatment = new Treatment();
            treatment.setExecutor(this.control.patient, medicine);
            treatment.execute(this.control.patient);
            if(this.control.patient.getHealth() == 100){
                this.control.patient.setStatus("healthy");
            }else{
                this.control.patient.setStatus("");
            }
        }
    }
}
