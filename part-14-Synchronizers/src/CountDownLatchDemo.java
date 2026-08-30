import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrates using a CountDownLatch to run three independent payment
 * pre-checks (fraud check, account validation, balance verification)
 * concurrently, and waiting for all three to finish before processing
 * the payment.
 */
public class CountDownLatchDemo {

    public void processPayment(String paymentId, BigDecimal amount) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);
        ExecutorService executor = Executors.newFixedThreadPool(3);

        try {
            executor.submit(() -> validateFraud(paymentId, latch));
            executor.submit(() -> validateAccount(paymentId, latch));
            executor.submit(() -> verifyBalance(paymentId, amount, latch));

            latch.await(); // blocks until all 3 checks call countDown()
            System.out.println("Processing payment: " + paymentId);
        } finally {
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        }
    }

    private void validateFraud(String paymentId, CountDownLatch latch) {
        try {
            simulateWork(300);
            System.out.println("[" + paymentId + "] Fraud check completed");
        } finally {
            latch.countDown();
        }
    }

    private void validateAccount(String paymentId, CountDownLatch latch) {
        try {
            simulateWork(200);
            System.out.println("[" + paymentId + "] Account validation completed");
        } finally {
            latch.countDown();
        }
    }

    private void verifyBalance(String paymentId, BigDecimal amount, CountDownLatch latch) {
        try {
            simulateWork(400);
            System.out.println("[" + paymentId + "] Balance verification completed for amount: " + amount);
        } finally {
            latch.countDown();
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
        CountDownLatchDemo service = new CountDownLatchDemo();
        service.processPayment("PAY-1001", new BigDecimal("2500.00"));
    }
}