package scheduler.processing;

public class Stats {

    private int generatedTotal;
    private int generatedArith;
    private int generatedIO;
    private int generatedCond;
    private int generatedLoop;

    private int completedTotal;
    private long totalServiceTimeMillis;

    public Stats() {

        generatedTotal = 0;
        generatedArith = 0;
        generatedIO = 0;
        generatedCond = 0;
        generatedLoop = 0;
        completedTotal = 0;
        totalServiceTimeMillis = 0;
    }

    public synchronized void incrementGeneratedArith() {
        generatedArith++;
        generatedTotal++;
    }

    public synchronized void incrementGeneratedIO() {
        generatedIO++;
        generatedTotal++;
    }

    public synchronized void incrementGeneratedCond() {
        generatedCond++;
        generatedTotal++;
    }

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

    public synchronized int getGeneratedTotal() {
        return generatedTotal;
    }

    public synchronized int getGeneratedArith() {
        return generatedArith;
    }

    public synchronized int getGeneratedIO() {
        return generatedIO;
    }

    public synchronized int getGeneratedCond() {
        return generatedCond;
    }

    public synchronized int getGeneratedLoop() {
        return generatedLoop;
    }

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
