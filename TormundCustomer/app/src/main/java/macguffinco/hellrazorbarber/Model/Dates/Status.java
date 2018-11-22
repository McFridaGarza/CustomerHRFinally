package macguffinco.hellrazorbarber.Model.Dates;

import android.renderscript.Sampler;

public enum  Status {

    NotAssigned ("Sin Asignar",0),
    Active ("Activo",1),
    Cancel ("Cancelado",2),
    Due ("Vencido",3),
    Pending ("Pendiente",4),;

    private String Description;
    private int Value;

    private Status (String description, int value){
        this.Description = description;
        this.Value = Value;
    }

    public String getDescription() {
        return Description;
    }

    public int getValue() {
        return Value;
    }

}
