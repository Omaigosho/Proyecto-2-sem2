/* LCFSPolicy.java */
/**
** Hecho por: Abigail Carrillo
** Carnet: 2500761
** Seccion: B
**/
package scheduler.scheduling.policies;

import java.util.*;
import scheduler.processing.SimpleProcess;

public class LCFSPolicy extends Policy implements Enqueable {

    private Stack<SimpleProcess> pila = new Stack<SimpleProcess>();

    @Override
    public void add(SimpleProcess p) {

        pila.push(p);
        size++;
        totalProcesses++;

    }

    @Override
    public void remove() {

        if (!pila.empty()) {
            pila.pop();
            size--;
        }

    }

    @Override
    public SimpleProcess next() {

        if (!pila.empty()) {

            return pila.peek();
        }
        return null;
    }

}