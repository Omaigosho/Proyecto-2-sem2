package scheduler.scheduling.policies;

import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;
import scheduler.processing.SimpleProcess;

/* PriorityProcess.java */
/**
 ** Hecho por: Carlos Augusto González Paiz y Edson Joao Andrés Pereira Alvarado.
 ** Carnet: 25000624 y 25000144.
 ** Seccion: B
*/

//Esta clase implementa el uso de una un politica donde los .
public class PriorityPolicy extends Policy {

    // Clase simple para guardar proceso, prioridad y orden de llegada
    class PriorityPack {
        SimpleProcess proceso;
        int prioridad;
        long orden;

        public PriorityPack(SimpleProcess p, int prio, long ord){
            proceso = p;
            prioridad = prio;
            orden = ord;
        }

        public String toString(){
            return proceso.toString() + "[prio:" + prioridad + "]";
        }
    }

    PriorityQueue<PriorityPack> filaProcesos;
    long nextOrden;
    final int MAX_PRIORITY = 5;

    public PriorityPolicy(){
        super();
        nextOrden = 0;

        filaProcesos = new PriorityQueue<>(
            new Comparator<PriorityPack>() {
                public int compare(PriorityPack a, PriorityPack b){
                    int cmp = Integer.compare(a.prioridad, b.prioridad);
                    if(cmp != 0) return cmp;
                    return Long.compare(a.orden, b.orden);
                }
            }
        );
    }

    public SimpleProcess next(){
        PriorityPack pack = filaProcesos.peek();
        if(pack == null) return null;
        return pack.proceso;
    }

    public void remove(){
        PriorityPack eliminado = filaProcesos.poll();
        if(eliminado != null){
            size--;
        }
    }
    

    // ESTE ES EL MÉTODO QUE EXIGÍA enqueueable: SOLO RECIBE SimpleProcess
    public void add(SimpleProcess p){
        if(p == null) return;

        int prioridad = ThreadLocalRandom.current().nextInt(1, MAX_PRIORITY + 1);
        PriorityPack pack = new PriorityPack(p, prioridad, nextOrden++);

        filaProcesos.add(pack);

        size++;
        totalProcesses++;
    }


    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("PriorityPolicy { size=").append(size)
          .append(", totalProcesses=").append(totalProcesses())
          .append(", queue=[");

        boolean first = true;
        for(PriorityPack pack : filaProcesos){
            if(!first) sb.append(", ");
            sb.append(pack.toString());
            first = false;
        }

        sb.append("]}");
        return sb.toString();
    }
}