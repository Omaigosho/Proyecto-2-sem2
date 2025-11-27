/* FCFSPolicy.java */

/*
 * Hecho por: Edson Pereira
 * Carnet: 25000144
 * Sección: B
 */

/* FCFSPolicy.java es la clase que implementa la FCFS policy, 
osea la politica donde el proceso en llegar primero es el proceso en atenderse primero  */
package scheduler.scheduling.policies;

import java.util.concurrent.ConcurrentLinkedQueue;
import scheduler.processing.SimpleProcess;

public class FCFSPolicy extends Policy {
    /*
     * filaProcesos representa la fila de procesos que va a implementar la FCFS
     * policy
     */
    private ConcurrentLinkedQueue<SimpleProcess> filaProcesos;

    /**
     * El constructor inicializa la fila de procesos vacia y llama al constructor de
     * la superclase
     **/
    public FCFSPolicy() {
        super();
        this.filaProcesos = new ConcurrentLinkedQueue<>();
    }

    /**
     * next() devuelve el siguiente proceso a ser ejecutado
     * 
     * @return el primer proceso de la fila
     */
    @Override
    public SimpleProcess next() {
        return this.filaProcesos.peek();
    }

    /**
     * remove() remueve el primer proceso de la fila
     * 
     * @return el primer proceso de la fila
     */
    @Override
    public void remove() {
        SimpleProcess procesoRemovido = this.filaProcesos.poll();
        if (procesoRemovido != null) {
            this.size--;
        }
    }

    /**
     * add() agrega un proceso a la fila
     * 
     * @param process el proceso a agregar
     */
    @Override
    public void add(SimpleProcess process) {
        this.filaProcesos.add(process);
        this.size++;
        this.totalProcesses++;
    }

    /**
     * toString() devuelve una representacion en String de la FCFS policy
     * 
     * @return una representacion en String de la FCFS policy
     */
    @Override
    public String toString() {
        return "FCFSPolicy{" + "filaProcesos=" + filaProcesos + ", size=" + size + ", totalProcesses="
                + totalProcesses() + '}';
    }

}
