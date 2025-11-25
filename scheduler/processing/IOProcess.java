/* IOProcess.java */

/**
 * @author Edson Pereira
 * @carnet 25000144
 * @seccion B
 */

/* IOProcess.java es uno de los tipos de procesos que se pueden generar, 
especificamente el tipo de proceso Input/Output */

package scheduler.processing;

public class IOProcess extends SimpleProcess {
    /*
     * El campo processTime representa la cantidad de tiempo (en segundos) en que se
     * "procesa" el proceso (valga la redundancia)
     */
    private final double processTime;

    /*
     * Constructor de la clase IOProcess
     * 
     * @param id: ID del proceso
     * 
     * @param processTime: Tiempo de procesamiento del proceso en segundos
     */
    public IOProcess(int id, double processTime) {
        super(id);
        this.processTime = processTime;
    }

    /*
     * Este metodo devuelve el tiempo de procesamiento del proceso en milisegundos
     * 
     * @return: Tiempo de procesamiento del proceso en milisegundos
     */
    public double processTimeMS() {
        return processTime * 1000;
    }

    /*
     * Este metodo devuelve un String que representa el tipo de proceso
     * 
     * @return: Tipo de proceso
     */
    public String tipoProceso() {
        return "IO";
    }

    /*
     * Este metodo devuelve una representacion en String de los datos del proceso
     * 
     * @return: String con los datos del proceso (id, tiempo de procesamiento y tipo
     * de proceso)
     */
    @Override
    public String toString() {
        return super.toString() + " [Tiempo procesamiento: " + processTimeMS() + "ms]" + " [Tipo de proceso: "
                + tipoProceso() + "]";
    }

}
