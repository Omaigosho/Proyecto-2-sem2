
/*ProcessScheduler.java */
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import scheduler.processing.Generador;
import scheduler.processing.Procesador;
import scheduler.processing.Stats;
import scheduler.scheduling.policies.FCFSPolicy;
import scheduler.scheduling.policies.LCFSPolicy;
import scheduler.scheduling.policies.Policy;
import scheduler.scheduling.policies.PriorityPolicy;

/*
 * Hecho por Edson Joao Andrés Pereira Alvarado
 * Carnet: 25000144
 * Seccion: B
 */

/*
 * ProcessScheduler es la clase principal del proyecto.
 * Se encarga de la ejecución del programa mediante los parametros que recibe en consola.
 * 
 */
public class ProcessScheduler {
    public static void main(String[] args) {

        /*
         * Si no se pasan los 6 argumentos, se muestra un mensaje de error y se sale del
         * programa.
         */
        if (args.length != 6) {
            System.out.println("Se deben ingresar 6 argumentos al momento de ejecutar el programa");
            System.out.println("Ejemplo: java ProcessScheduler -politica rango_tiempo_ingreso arith io cond loop");
            System.exit(1);
        }

        /*
         * Recorremos un array con las politicas permitidas y verificamos si el
         * argumento no.1 para ver si es una politica valida.
         */
        String[] permitted = { "-fcfs", "-pp", "-lcfs" };
        boolean isValidPolicy = false;
        for (String perm : permitted) {
            if (args[0].equals(perm)) {
                isValidPolicy = true;
                break;
            }
        }

        /*
         * Si el argumento no.1 no es una politica valida, se muestra un mensaje de
         * error
         * y se sale del programa.
         */
        if (!isValidPolicy) {
            System.out.println("El argumento " + args[0] + " no es una politica valida");
            System.exit(1);
        }

        /*
         * Dividimos el argumento no.2 en dos partes, el minimo y el maximo.
         * Si no se puede dividir en dos partes, se muestra un mensaje de error
         * y se sale del programa.
         */
        String[] rangoTiempoIngreso = args[1].split("-");
        if (rangoTiempoIngreso.length != 2) {
            System.out.println("El argumento " + args[1] + " no es un rango valido");
            System.exit(1);
        }

        /*
         * Convertimos el minimo y el maximo a double.
         * Si alguno de ellos es menor a 0 o el rango menor NO es menor o igual al
         * maximo,
         * se muestra un mensaje de error y se sale del programa.
         */
        double rangoTiempoIngresoMin = Double.parseDouble(rangoTiempoIngreso[0]);
        double rangoTiempoIngresoMax = Double.parseDouble(rangoTiempoIngreso[1]);

        if (rangoTiempoIngresoMin < 0 || rangoTiempoIngresoMax < 0) {
            System.out.println("El argumento " + args[1] + " no es un rango valido");
            System.exit(1);
        } else if (!(rangoTiempoIngresoMin <= rangoTiempoIngresoMax)) {
            System.out.println("El argumento " + args[1] + " no es un rango valido");
            System.exit(1);
        }

        /*
         * Convertimos los argumentos de tiempo arith, io, cond y loop a double.
         * Si alguno de ellos es menor a 0,
         * se muestra un mensaje de error y se sale del programa.
         */
        double arith = Double.parseDouble(args[2]);
        double io = Double.parseDouble(args[3]);
        double cond = Double.parseDouble(args[4]);
        double loop = Double.parseDouble(args[5]);

        if (arith <= 0 || io <= 0 || cond <= 0 || loop <= 0) {
            System.out.println("Uno de los argumentos de tiempo arith, io, cond o loop no es valido");
            System.exit(1);
        }

        /*
         * Creamos la politica segun el argumento no.1.
         */
        Policy policy = null;
        switch (args[0]) {
            case "-fcfs":
                policy = new FCFSPolicy();
                break;
            case "-pp":
                policy = new PriorityPolicy();
                break;
            case "-lcfs":
                policy = new LCFSPolicy();
                break;
            default:
                System.out.println("Politica no soportada");
                System.exit(1);
        }

        /*
         * Creo un objeto de tipo AtomicBoolean que dictamina si el programa debe
         * cotninuar ejecutandose.
         */
        AtomicBoolean runContinue = new AtomicBoolean(true);

        /*
         * Creo un objeto de tipo Stats que contiene estadisticas de la simulacion.
         */
        Stats stats = new Stats();

        /*
         * Creo un objeto de tipo generador que crea procesos de manera aleatoria.
         */
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

        /*
         * Creo un objeto de tipo procesador que ejecuta los procesos segun la politica.
         */
        Procesador procesador = new Procesador(
                policy,
                runContinue,
                stats);

        /*
         * Creo dos hilos, uno para el generador y otro para el procesador.
         */
        Thread genThread = new Thread(generador, "Generador");
        Thread cpuThread = new Thread(procesador, "Procesador");

        /*
         * Inicio los hilos. (Ya me aburri de poner comentarios jajajajaj)
         */
        genThread.start();
        cpuThread.start();

        /*
         * Muestro un mensaje de bienvenida y espero a que el usuario presione 'q' y
         * luego ENTER para
         * temrinar la simulacion.
         */
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

        /*
         * Espero a que los hilos terminen.
         * Si no hubo problema, muestro un resumen final con los datos de la simulacion.
         */
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
        System.out.println("==========================");
    }
}
