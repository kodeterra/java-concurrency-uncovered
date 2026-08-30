import java.util.concurrent.*;

/**
 * Demonstrates using a CyclicBarrier to synchronize multiple worker threads
 * processing transaction batches. All 3 workers must finish their batch
 * before any of them is allowed to proceed to settlement.
 */
public class CyclicBarrierDemo {
    private final CyclicBarrier barrier;

    public CyclicBarrierDemo(int parties) {
        // optional barrier action, runs once when the last thread arrives
        this.barrier = new CyclicBarrier(parties,
                () -> System.out.println(">>> All batches complete — releasing for settlement <<<"));
    }

    private void processBatch(String workerName, long batchTimeMillis) {
        try {
            simulateWork(batchTimeMillis);
            System.out.println(workerName + " completed transaction batch");
            barrier.await(); // waits until all 3 parties call await()
            System.out.println(workerName + " starting settlement");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (BrokenBarrierException e) {
            System.out.println(workerName + " barrier broken, aborting settlement");
        }
    }

    private void simulateWork(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int parties = 3;
        CyclicBarrierDemo settlement = new CyclicBarrierDemo(parties);
        ExecutorService executor = Executors.newFixedThreadPool(parties);

        // simulate 3 workers with different batch processing times
        executor.submit(() -> settlement.processBatch("Worker-A", 500));
        executor.submit(() -> settlement.processBatch("Worker-B", 900));
        executor.submit(() -> settlement.processBatch("Worker-C", 300));

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }
}