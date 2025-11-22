import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import scheduler.processing.Generador;
import scheduler.processing.Procesador;
import scheduler.processing.Stats;
import scheduler.scheduling.policies.FCFSPolicy;
import scheduler.scheduling.policies.PPPolicy;
import scheduler.scheduling.policies.LCFSPolicy;
import scheduler.scheduling.policies.Policy;

public class ProcessScheduler {
    public static void main(String[] args) {
        // 1. Validación básica de argumentos
        if (args.length != 6) {
            System.out.println("Se deben ingresar 6 argumentos al momento de ejecutar el programa");
            System.out.println("Ejemplo: java ProcessScheduler -politica rango_tiempo_ingreso arith io cond loop");
            System.exit(1);
        }

        // 2. Validar política
        String[] permitted = { "-fcfs", "-pp", "-lcfs" };
        boolean isValidPolicy = false;
        for (String perm : permitted) {
            if (args[0].equals(perm)) {
                isValidPolicy = true;
                break;
            }
        }

        if (!isValidPolicy) {
            System.out.println("El argumento " + args[0] + " no es una politica valida");
            System.exit(1);
        }

        // 3. Parsear rango de tiempo de ingreso
        String[] rangoTiempoIngreso = args[1].split("-");
        if (rangoTiempoIngreso.length != 2) {
            System.out.println("El argumento " + args[1] + " no es un rango valido");
            System.exit(1);
        }

        double rangoTiempoIngresoMin = Double.parseDouble(rangoTiempoIngreso[0]);
        double rangoTiempoIngresoMax = Double.parseDouble(rangoTiempoIngreso[1]);

        if (rangoTiempoIngresoMin < 0 || rangoTiempoIngresoMax < 0) {
            System.out.println("El argumento " + args[1] + " no es un rango valido");
            System.exit(1);
        } else if (!(rangoTiempoIngresoMin <= rangoTiempoIngresoMax)) {
            System.out.println("El argumento " + args[1] + " no es un rango valido");
            System.exit(1);
        }

        // 4. Parsear tiempos de servicio por tipo de proceso
        double arith = Double.parseDouble(args[2]);
        double io = Double.parseDouble(args[3]);
        double cond = Double.parseDouble(args[4]);
        double loop = Double.parseDouble(args[5]);

        if (arith <= 0 || io <= 0 || cond <= 0 || loop <= 0) {
            System.out.println("Uno de los argumentos de tiempo arith, io, cond o loop no es valido");
            System.exit(1);
        }

        // 5. Crear la política adecuada
        Policy policy = null;
        switch (args[0]) {
            case "-fcfs":
                policy = new FCFSPolicy();
                break;
            case "-pp":
                policy = new PPPolicy();
                break;
            case "-lcfs":
                policy = new LCFSPolicy();
                break;
            default:
                System.out.println("Politica no soportada");
                System.exit(1);
        }

        AtomicBoolean runContinue = new AtomicBoolean(true);
        Stats stats = new Stats();

        Generador generador = new Generador(
                policy,
                arith,
                io,
                cond,
                loop,
                rangoTiempoIngresoMin,
                rangoTiempoIngresoMax,
                runContinue,
                stats);

        Procesador procesador = new Procesador(
                policy,
                runContinue,
                stats);

        Thread genThread = new Thread(generador, "Generador");
        Thread cpuThread = new Thread(procesador, "Procesador");

        genThread.start();
        cpuThread.start();

        System.out.println("Simulación iniciada con política: " + policy.getClass().getSimpleName());
        System.out.println("Presione 'q' y ENTER para terminar la simulación.");

        Scanner sc = null;
        try {
            sc = new Scanner(System.in);
            while (true) {
                String line = sc.nextLine();
                if (line != null && line.equalsIgnoreCase("q")) {
                    runContinue.set(false);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            genThread.join();
            cpuThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("");
        System.out.println("===== RESUMEN FINAL =====");
        System.out.println("Política usada: " + policy.getClass().getSimpleName());
        System.out.println("Procesos generados (total): " + stats.getGeneratedTotal());
        System.out.println("  Aritméticos: " + stats.getGeneratedArith());
        System.out.println("  IO:          " + stats.getGeneratedIO());
        System.out.println("  Condicional: " + stats.getGeneratedCond());
        System.out.println("  Loop:        " + stats.getGeneratedLoop());
        System.out.println("Procesos completados: " + stats.getCompletedTotal());
        System.out.println("Procesos restantes en la cola: " + policy.size());
        System.out.println("Tiempo promedio de servicio (s): " + stats.getAverageServiceTimeSeconds());
        System.out.println("==========================");
    }
}
