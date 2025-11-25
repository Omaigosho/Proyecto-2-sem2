/* LCFSPolicy.java */
/**
** Hecho por: Abigail Carrillo
** Carnet: 2500761
** Seccion: B
**/

/* Esta clase representa la política Last Come First Serve, la cual puede ser representada como una pila. En esta clase el último 
proceso que es ingresado a la pila es el primero que es atentido*/

package scheduler.scheduling.policies;
import java.util.*;
import scheduler.processing.SimpleProcess;

public class LCFSPolicy extends Policy implements Enqueable{

    /* Instanciamos una pila que nos servirá para guardar todos nuestros porcesos*/
    private Stack<SimpleProcess> pila= new Stack<SimpleProcess>();

    /* METODO SOBREESCRITO
        Nombre del Método: add(E)
        Parámetro: Objeto de tipo SimpleProcess
        Utilidad: agregar un proceso nuevo a la pila y aumentar los contadores de tamnaño y total de procesos
    */
    @Override
    public void add(SimpleProcess p){

        pila.push(p);
        size++;
        totalProcesses++;
    
    }

    /* METODO SOBREESCRITO
        Nombre del Método: remove()
        Parámetro: Sin parámetros
        Utilidad: remover el útimo proceso que entró a la pila para ser atendido
    */
    @Override
    public void remove(){

        if (!pila.empty()){
            pila.pop();
            size--;
        }
        
    }

    /* METODO SOBREESCRITO
        Nombre del Método: next()
        Parámetro: Sin parámetros
        Utilidad: nos devuelve cuál será el proximo proceso en ser atendido, sin sacarlo de la pila
    */
    @Override
    public SimpleProcess next(){

        if (!pila.empty()){

            return pila.peek();
        }
        return null;
    }

}