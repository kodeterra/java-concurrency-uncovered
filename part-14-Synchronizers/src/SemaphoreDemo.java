import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrates throttling concurrent payment gateway calls using a Semaphore.
 * Only 3 transactions are allowed to hit the "gateway" at the same time;
 * any additional transactions block until a permit is released.
 */
public class SemaphoreDemo {
    // only 3 transactions can be processed concurrently
    private final Semaphore semaphore = new Semaphore(3);
    public void processPayment(String txnId, BigDecimal amount) throws InterruptedException {
        semaphore.acquire(); // blocks if no permit available
        try {
            System.out.printf("%s [%s] processing... (permits available: %d)%n", txnId,
                    Thread.currentThread().getName(), semaphore.availablePermits());
            simulateGatewayCall(amount);
            System.out.printf("%s [%s] completed%n", txnId, Thread.currentThread().getName());
        } finally {
            semaphore.release(); // always release, even on exception
        }
    }
    private void simulateGatewayCall(BigDecimal amount) throws InterruptedException {
        // simulate network latency to a payment gateway
        Thread.sleep(1000);
    }
    public static void main(String[] args) throws InterruptedException {
        SemaphoreDemo gateway = new SemaphoreDemo();

        // simulate 8 incoming transactions submitted concurrently
        List<String> txnIds = List.of("TXN-001", "TXN-002", "TXN-003", "TXN-004", "TXN-005", "TXN-006", "TXN-007", "TXN-008");

        ExecutorService executor = Executors.newFixedThreadPool(8);

        for (String txnId : txnIds) {
            executor.submit(() -> {
                try {
                    gateway.processPayment(txnId, new BigDecimal("100.00"));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println(txnId + " was interrupted");
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);
        System.out.println("All transactions processed.");
    }
}