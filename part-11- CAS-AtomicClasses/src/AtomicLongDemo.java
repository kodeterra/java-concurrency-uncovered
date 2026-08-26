import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.ThreadLocalRandom;

public class AtomicLongDemo {
    // Not thread-safe
    private long totalTransactionVolume = 0;
    public void updateTotal(int amount) {
        totalTransactionVolume += amount; // read, NOT atomic
    }
    //Thread-safe
    private final AtomicLong atomicTransactionVolume = new AtomicLong(0);
    public void updateTotalAtomic(int amount) {
        atomicTransactionVolume.addAndGet(amount); // atomic read-modify-write
    }
    public static void main(String[] args) throws InterruptedException {
        int threadCount = 10;
        int callsPerThread = 1000;

        // Pre-generate the same random amounts so both counters
        // are working with identical input for a fair comparison
        int[][] amounts = new int[threadCount][callsPerThread];
        long expectedTotal = 0;
        for (int i = 0; i < threadCount; i++) {
            for (int j = 0; j < callsPerThread; j++) {
                int amt = ThreadLocalRandom.current().nextInt(1, 11); // 1-10
                amounts[i][j] = amt;
                expectedTotal += amt;
            }
        }
        AtomicLongDemo service = new AtomicLongDemo();
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final int threadIndex = i;
            executor.submit(() -> {
                for (int amt : amounts[threadIndex]) {
                    service.updateTotal(amt);
                    service.updateTotalAtomic(amt);
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Expected Total     : " + expectedTotal);
        System.out.println("Broken (long) Total : " + service.totalTransactionVolume);
        System.out.println("Atomic Total        : " + service.atomicTransactionVolume.get());
    }
}