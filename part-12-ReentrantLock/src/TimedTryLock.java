import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TimedTryLock {

    private final ReentrantLock gatewayLock = new ReentrantLock();
    public void processPayment(String paymentId) {
        try {
            if (gatewayLock.tryLock(3, TimeUnit.SECONDS)) {
                try {
                    System.out.println(Thread.currentThread().getName()
                            + " acquired the gateway.");
                    System.out.println("Processing " + paymentId);
                    // Simulate a slow external payment gateway
                    Thread.sleep(5000);
                    System.out.println(paymentId + " completed.");
                } finally {
                    gatewayLock.unlock();
                    System.out.println(Thread.currentThread().getName()
                            + " released the gateway.");
                }
            } else {
                System.out.println(Thread.currentThread().getName()
                        + " -> Gateway busy. Service Busy, please retry.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {

        TimedTryLock gateway = new TimedTryLock();
        Thread t1 = new Thread(
                () -> gateway.processPayment("PAY-101"),"Thread-1");
        Thread t2 = new Thread(
                () -> gateway.processPayment("PAY-102"),"Thread-2");
        t1.start();
        try {
            Thread.sleep(200);   // Let Thread-1 acquire the lock first
        } catch (InterruptedException ignored) {
        }
        t2.start();
    }
}
