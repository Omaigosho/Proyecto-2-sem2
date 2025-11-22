package scheduler.processing;

import java.util.concurrent.atomic.AtomicBoolean;
import scheduler.scheduling.policies.Policy;

public class Procesador implements Runnable {

    private final Policy policy;
    private final AtomicBoolean continueGen;
    private final Stats stats;

    public Procesador(Policy policy, AtomicBoolean continueGen, Stats stats) {
        this.policy = policy;
        this.continueGen = continueGen;
        this.stats = stats;
    }

    @Override
    public void run() {
        while (continueGen.get()) {
            try {
                if (policy.size() == 0) {
                    Thread.sleep(50);
                    continue;
                }
                SimpleProcess proc = policy.next();
                if (proc == null) {
                    Thread.sleep(50);
                    continue;
                }

                policy.remove();

                double serviceMs = obtenerTiempoServicio(proc);

                System.out.println("\n[CPU] Atendiendo proceso: " + proc.toString());
                System.out.println("[CPU] Tiempo de servicio: " + serviceMs + " ms");
                System.out.println("[CPU] Política usada: " + policy.getClass().getSimpleName());
                System.out.println("[CPU] Procesos completados hasta ahora: " + stats.getCompletedTotal());

                Thread.sleep((long) serviceMs);

                stats.registerCompletedProcess((long) serviceMs);

                System.out.println("[CPU] --- Proceso completado ---");
                System.out.println("[CPU] Cola actual: " + policy.toString());
                System.out.println("[CPU] Total completados: " + stats.getCompletedTotal());
                System.out.println();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("[CPU] Terminando ejecución del procesador...");
    }

    private double obtenerTiempoServicio(SimpleProcess p) {
        if (p instanceof ArithProcess) {
            return ((ArithProcess) p).processTimeMS();
        }
        if (p instanceof IOProcess) {
            return ((IOProcess) p).processTimeMS();
        }
        if (p instanceof CondProcess) {
            return ((CondProcess) p).processTimeMS();
        }
        if (p instanceof LoopProcess) {
            return ((LoopProcess) p).processTimeMS();
        }

        // fallback en caso de que agreguen más procesos
        return 0;
    }
}
