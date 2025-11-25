package scheduler.processing;

/* PriorityProcess.java */
/**
 ** Hecho por: Carlos Augusto Gonzalez Paiz.
 ** Carnet: 25000624.
 ** Seccion: B.
*/

public class ConditionalProcess extends SimpleProcess{
    double Tiempo;

    ConditionalProcess(int ID, double Time){
        super(ID);
        this.Tiempo = Time;
    }
    public double processTimeMs(){
        return Tiempo * 1000;
    }
    public String tipoProceso(){
        return "C";
    }
}
