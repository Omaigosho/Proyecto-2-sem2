/*Generador.java*/

/**
 * @author Edson Pereira
 * @carnet 25000144
 * @seccion B
 */

/*Generador.java sirve para generar procesos de manera aleatoria 
e ingresarlos a la politica que se este usando en ese momento */
package scheduler.processing;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import scheduler.scheduling.policies.Policy;

public class Generador implements Runnable {

    // Política de planificación que administra la cola de procesos.
    private final Policy policy;

    // Tiempo de servicio (segundos) para procesos aritméticos.
    private final double arith;

    // Tiempo de servicio (segundos) para procesos de I/O.
    private final double io;

    // Tiempo de servicio (segundos) para procesos condicionales.
    private final double cond;

    // Tiempo de servicio (segundos) para procesos de loop.
    private final double loop;

    // Rango de tiempo de ingreso (segundos) para los procesos.
    private final double rangoTiempoIngresoMin;
    private final double rangoTiempoIngresoMax;

    // Bandera para detener la generación de procesos.
    private final AtomicBoolean continueGen;

    // Referencia a las estadísticas globales.
    private final Stats stats;
    private int corrID = 0;

    /**
     * Constructor del generador de procesos.
     *
     * @param policy                política de planificación compartida
     * @param arith                 tiempo de servicio de procesos aritméticos
     * @param io                    tiempo de servicio de procesos de IO
     * @param cond                  tiempo de servicio de procesos condicionales
     * @param loop                  tiempo de servicio de procesos de loop
     * @param rangoTiempoIngresoMin tiempo mínimo entre llegadas de procesos
     * @param rangoTiempoIngresoMax tiempo máximo entre llegadas de procesos
     * @param continueGen           bandera para detener la generación
     * @param stats                 referencia a las estadísticas globales
     *
     */

    public Generador(Policy policy, double arith, double io, double cond, double loop, double rangoTiempoIngresoMin,
            double rangoTiempoIngresoMax, AtomicBoolean continueGen, Stats stats) {
        this.policy = policy;
        this.arith = arith;
        this.io = io;
        this.cond = cond;
        this.loop = loop;
        this.rangoTiempoIngresoMin = rangoTiempoIngresoMin;
        this.rangoTiempoIngresoMax = rangoTiempoIngresoMax;
        this.continueGen = continueGen;
        this.stats = stats;
    }

    /**
     * Método principal que ejecuta el generador de procesos.
     * De manera aleatoria se generan procesos de tipo aritmético, de I/O,
     * condicional y de loop.
     * 
     * En funcion del proceso que se genere, se le asigna un tiempo de
     * procesamiento y se lo agrega a la politica.
     * 
     * Aparte, se actualizan las estadisticas globales.
     * 
     * @throws Exception si ocurre un error al generar procesos
     */
    @Override
    public void run() {
        while (continueGen.get()) {
            try {
                double delaySeconds = ThreadLocalRandom.current().nextDouble(rangoTiempoIngresoMin,
                        rangoTiempoIngresoMax);
                Thread.sleep((long) (delaySeconds * 1000));
                if (!continueGen.get()) {
                    break;
                }

                Random rand = new Random();
                int randProcChar = rand.nextInt(4);
                double randProcTime = 0;
                SimpleProcess randProc = null;
                switch (randProcChar) {
                    case 0:
                        randProcTime = arith;
                        randProc = new ArithmeticProcess(corrID++, randProcTime);
                        stats.incrementGeneratedArith();
                        break;
                    case 1:
                        randProcTime = io;
                        randProc = new IOProcess(corrID++, randProcTime);
                        stats.incrementGeneratedIO();
                        break;
                    case 2:
                        randProcTime = cond;
                        randProc = new ConditionalProcess(corrID++, randProcTime);
                        stats.incrementGeneratedCond();
                        break;
                    case 3:
                        randProcTime = loop;
                        randProc = new LoopProcess(corrID++, randProcTime);
                        stats.incrementGeneratedLoop();
                        break;
                    default:
                        break;
                }

                policy.add(randProc);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
