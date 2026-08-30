import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrates using a Phaser to synchronize 3 payment workers across
 * multiple phases: validation, authorization, and settlement.
 * All parties must complete each phase before any of them can advance
 * to the next.
 */
public class PhaserDemo {

    private final Phaser phaser = new Phaser(3);

    private void processPayment(String workerName) {
        validatePayment(workerName);
        phaser.arriveAndAwaitAdvance(); // wait for all 3 to finish validation

        authorizePayment(workerName);
        phaser.arriveAndAwaitAdvance(); // wait for all 3 to finish authorization

        settlePayment(workerName);
        phaser.arriveAndAwaitAdvance(); // wait for all 3 to finish settlement

        System.out.println(workerName + " finished all phases");
    }

    private void validatePayment(String workerName) {
        simulateWork(200);
        System.out.println(workerName + " completed validation (phase "
                + phaser.getPhase() + ")");
    }

    private void authorizePayment(String workerName) {
        simulateWork(300);
        System.out.println(workerName + " completed authorization (phase "
                + phaser.getPhase() + ")");
    }

    private void settlePayment(String workerName) {
        simulateWork(150);
        System.out.println(workerName + " completed settlement (phase "
                + phaser.getPhase() + ")");
    }

    private void simulateWork(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PhaserDemo sync = new PhaserDemo();
        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.submit(() -> sync.processPayment("Worker-A"));
        executor.submit(() -> sync.processPayment("Worker-B"));
        executor.submit(() -> sync.processPayment("Worker-C"));

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }
}