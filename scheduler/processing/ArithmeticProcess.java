/* ArithmeticProcess.java */
/**
** Hecho por: Abigail Carrillo
** Carnet: 2500761
** Seccion: B
**/

/* Esta clase es uno de los cuatro procesos los cuales pueden entrar a las colas. Especificamente 
el proceso Aritmetico*/

package scheduler.processing;

public class ArithmeticProcess extends SimpleProcess{

    /* El Campo tiempo representa el tiempo en segundos en que se procesará */
    private static double tiempo;

    /* -Constructor de nuestra clase- 
        Parametro 1: ID de nuestro proceso
        Parametro 2: tiempo de procesamiento en segundos
    */
    public ArithmeticProcess(int id, double tiempo){

        super(id);
        this.tiempo=tiempo;

    }

    /* Nombre del Método: tiempoProceso()
        No contiene parámetros
        Nos devuelve el tiempo de procesamiento en segundos
    */
    public double tiempoProceso(){

         return tiempo;

    }

    /* Nombre del Método: tipoProceso()
        No contiene parámetros
        Nos devuelve una letra "A" que representa el tipo de proceso con el que estamos trabajando.
        En este caso: "Arithmetic"
    */
    public String tipoProceso(){

        return "A";

    }


    /* Nombre del Método: toString() 
        No contiene parámetros
        Nos devuelve un resumen de lo que contiene nuestro objeto de tipo ArithmeticProcess
    */
    
    public String toString(){

        return "[ID: " + super.getId() + "] " + "[Tiempo de procesamiento: " + tiempoProceso() + "s]" + 
        " [Tipo de proceso: " + tipoProceso() + "]";
    }
}