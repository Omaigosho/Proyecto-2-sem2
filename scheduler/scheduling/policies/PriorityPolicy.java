/*PriorityPolicy.java */
package scheduler.scheduling.policies;

import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;
import scheduler.processing.SimpleProcess;

/**
 ** Hecho por: Carlos Augusto González Paiz y Edson Joao Andrés Pereira Alvarado.
 ** Carnet: 25000624 y 25000144.
 ** Seccion: B
 */

public class PriorityPolicy extends Policy {

    /* Clase simple para guardar proceso, prioridad y orden de llegada */
    class PriorityPack {
        // Proceso simple que se guardará en la cola
        SimpleProcess proceso;
        // Prioridad del proceso
        int prioridad;
        // Orden de llegada del proceso
        long orden;

        // Constructor
        public PriorityPack(SimpleProcess p, int prio, long ord) {
            proceso = p;
            prioridad = prio;
            orden = ord;
        }

        // Método para convertir el objeto a String
        @Override
        public String toString() {
            return proceso.toString() + "[prio:" + prioridad + "]";
        }
    }

    /*
     * Cola de prioridad que se encarga de ordenar los procesos.
     * Estan ordenados en paquetes, con el proceso en cuestion, su prioridad y su
     * orden de llegada.
     */
    PriorityQueue<PriorityPack> filaProcesos;
    long nextOrden;
    final int MAX_PRIORITY = 3;

    public PriorityPolicy() {
        super();
        nextOrden = 0;

        filaProcesos = new PriorityQueue<>(
                Comparator.comparingInt((PriorityPack p) -> p.prioridad)
                        .thenComparingLong(p -> p.orden));
    }

    /*
     * METODO SOBREESCRITO
     * Nombre del Método: next()
     * Parámetro: Sin parámetros
     * Utilidad: nos devuelve cuál será el proximo proceso en ser atendido, sin
     * sacarlo de la pila
     */
    @Override
    public SimpleProcess next() {
        PriorityPack pack = filaProcesos.peek();
        if (pack == null)
            return null;
        return pack.proceso;
    }

    /*
     * METODO SOBREESCRITO
     * Nombre del Método: remove()
     * Parámetro: Sin parámetros
     * Utilidad: saca el proceso que se encuentra en la cima de la pila
     */
    @Override
    public void remove() {
        PriorityPack eliminado = filaProcesos.poll();
        if (eliminado != null) {
            size--;
        }
    }

    /*
     * METODO SOBREESCRITO
     * Nombre del Método: add()
     * Parámetro: SimpleProcess p
     * Utilidad: agrega un proceso a la pila
     */
    @Override
    public void add(SimpleProcess p) {
        if (p == null)
            return;

        int prioridad = ThreadLocalRandom.current().nextInt(1, MAX_PRIORITY + 1);
        PriorityPack pack = new PriorityPack(p, prioridad, nextOrden++);

        filaProcesos.add(pack);

        size++;
        totalProcesses++;
    }

    /*
     * METODO SOBREESCRITO
     * Nombre del Método: toString()
     * Parámetro: Sin parámetros
     * Utilidad: nos devuelve una representación en String de la Fila de Procesos
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PriorityPolicy { size=").append(size)
                .append(", totalProcesses=").append(totalProcesses())
                .append(", Queue=[");

        boolean first = true;
        for (PriorityPack pack : filaProcesos) {
            if (!first)
                sb.append(", ");
            sb.append(pack.toString());
            first = false;
        }

        sb.append("]}");
        return sb.toString();
    }
}