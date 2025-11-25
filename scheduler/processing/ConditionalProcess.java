package scheduler.processing;

/* PriorityProcess.java */
/**
 ** Hecho por: Carlos Augusto Gonzalez Paiz.
 ** Carnet: 25000624.
 ** Seccion: B.
*/


//Esta clase simula el proceso condicional para nuestro Process Scheduler.
public class ConditionalProcess extends SimpleProcess{
    double Tiempo;
    
//Construcctor.
    ConditionalProcess(int ID, double Time){
        super(ID);
        this.Tiempo = Time;
    }
    
    /*Se llama ProcessTimeMs, no recibe parametros,
    devuelve el tiempo del proceso en milisegundos,
    devuelve el tiempo que se va a utilizar para simular la realizacion de la tarea condicional.
    */
    public double processTimeMs(){
        return Tiempo * 1000;
    }

    /*Se llama TipoProceso, no recibe parametros,
    devuelve la letra que identifica el proceso que se esta utilizando y sirve para dar a entender el proceso
    que se esta llevando acabo.
    */ 
    public String tipoProceso(){
        return "C";
    }
}
