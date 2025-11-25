/*Stats.java */

/**
 * @author Edson Pereira
 * @carnet 25000144
 * @seccion B
 */

/*Stats.java es la clase que se encarga de almacenar y actualizar 
las estadísticas de los procesos generados y completados */
package scheduler.processing;

public class Stats {

    /* generatedTotal es la cantidad total de procesos generados */
    private int generatedTotal;

    /* generatedArith es la cantidad de procesos aritméticos generados */
    private int generatedArith;

    /* generatedIO es la cantidad de procesos de I/O generados */
    private int generatedIO;

    /* generatedCond es la cantidad de procesos condicionales generados */
    private int generatedCond;

    /* generatedLoop es la cantidad de procesos de loop generados */
    private int generatedLoop;

    /* completedTotal es la cantidad total de procesos completados */
    private int completedTotal;

    /* totalServiceTimeMillis es el tiempo total de servicio en milisegundos */
    private long totalServiceTimeMillis;

    /**
     * Constructor de la clase Stats
     */
    public Stats() {

        generatedTotal = 0;
        generatedArith = 0;
        generatedIO = 0;
        generatedCond = 0;
        generatedLoop = 0;
        completedTotal = 0;
        totalServiceTimeMillis = 0;
    }

    /**
     * Incrementa la cantidad de procesos aritméticos generados
     */
    public synchronized void incrementGeneratedArith() {
        generatedArith++;
        generatedTotal++;
    }

    /**
     * Incrementa la cantidad de procesos de I/O generados
     */
    public synchronized void incrementGeneratedIO() {
        generatedIO++;
        generatedTotal++;
    }

    /**
     * Incrementa la cantidad de procesos condicionales generados
     */
    public synchronized void incrementGeneratedCond() {
        generatedCond++;
        generatedTotal++;
    }

    /**
     * Incrementa la cantidad de procesos de loop generados
     */
    public synchronized void incrementGeneratedLoop() {
        generatedLoop++;
        generatedTotal++;
    }

    /**
     * Llamar cuando un proceso se termina de atender.
     * 
     * @param serviceTimeMillis tiempo que tardó en atenderse en milisegundos
     */
    public synchronized void registerCompletedProcess(long serviceTimeMillis) {
        completedTotal++;
        totalServiceTimeMillis += serviceTimeMillis;
    }

    /**
     * Obtiene la cantidad total de procesos generados
     * 
     * @return cantidad total de procesos generados
     */
    public synchronized int getGeneratedTotal() {
        return generatedTotal;
    }

    /**
     * Obtiene la cantidad de procesos aritméticos generados
     * 
     * @return cantidad de procesos aritméticos generados
     */
    public synchronized int getGeneratedArith() {
        return generatedArith;
    }

    /**
     * Obtiene la cantidad de procesos de I/O generados
     * 
     * @return cantidad de procesos de I/O generados
     */
    public synchronized int getGeneratedIO() {
        return generatedIO;
    }

    /**
     * Obtiene la cantidad de procesos condicionales generados
     * 
     * @return cantidad de procesos condicionales generados
     */
    public synchronized int getGeneratedCond() {
        return generatedCond;
    }

    /**
     * Obtiene la cantidad de procesos de loop generados
     * 
     * @return cantidad de procesos de loop generados
     */
    public synchronized int getGeneratedLoop() {
        return generatedLoop;
    }

    /**
     * Obtiene la cantidad total de procesos completados
     * 
     * @return cantidad total de procesos completados
     */
    public synchronized int getCompletedTotal() {
        return completedTotal;
    }

    public synchronized long getTotalServiceTimeMillis() {
        return totalServiceTimeMillis;
    }

    /**
     * Devuelve el tiempo promedio de servicio por proceso (en segundos).
     */
    public synchronized double getAverageServiceTimeSeconds() {
        if (completedTotal == 0) {
            return 0.0;
        }
        double totalSeconds = totalServiceTimeMillis / 1000.0;
        return totalSeconds / completedTotal;
    }

    @Override
    public synchronized String toString() {
        return "Stats{" +
                "Procesos generados =" + generatedTotal +
                ", Procesos arith =" + generatedArith +
                ", Procesos IO =" + generatedIO +
                ", Procesos cond =" + generatedCond +
                ", Procesos loop =" + generatedLoop +
                ", Procesos completados =" + completedTotal +
                ", Tiempo total de servicio =" + totalServiceTimeMillis +
                ", Tiempo promedio de servicio =" + getAverageServiceTimeSeconds() +
                '}';
    }
}
