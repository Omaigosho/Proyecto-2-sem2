package scheduler.processing;

/* ConditionalProcess.java */
/**
 ** Hecho por: Carlos Augusto Gonzalez Paiz.
 ** Carnet: 25000624.
 ** Seccion: B.
 */

/*
 * Clase que representa un proceso condicional.
 */
public class ConditionalProcess extends SimpleProcess {
    /*
     * Tiempo de procesamiento del proceso.
     */
    double Tiempo;

    /*
     * Constructor de la clase ConditionalProcess.
     */
    ConditionalProcess(int ID, double Time) {
        super(ID);
        this.Tiempo = Time;
    }

    /*
     * Método que devuelve el tiempo de procesamiento en milisegundos.
     */
    public double processTimeMs() {
        return Tiempo * 1000;
    }

    /*
     * Método que devuelve el tipo de proceso.
     */
    public String tipoProceso() {
        return "C";
    }
}
