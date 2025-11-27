package scheduler.processing;

/* ConditionalProcess.java */
/**
 ** Hecho por: Carlos Augusto Gonzalez Paiz.
 ** Carnet: 25000624.
 ** Seccion: B.
 */

/*
 * Clase que "simula" un proceso condicional.
 */
public class ConditionalProcess extends SimpleProcess {
    double Tiempo;

    // Constructor
    ConditionalProcess(int ID, double Time) {
        super(ID);
        this.Tiempo = Time;
    }

    /*
     * Método, que devuelve el tiempo que tarda el proceso pero en milisegundos.
     */
    public double processTimeMs() {
        return Tiempo * 1000;
    }

    /*
     * Método que devuelve la letra que representa el tipo de proceso.
     */
    public String tipoProceso() {
        return "C";
    }
}
