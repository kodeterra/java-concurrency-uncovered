import java.util.concurrent.locks.ReentrantLock;

public class TryLockDemo {
    private final ReentrantLock paymentLock = new ReentrantLock();

    public void processPayment(String paymentId) {
        if (paymentLock.tryLock()) {
            try {
                System.out.println(Thread.currentThread().getName()
                        + " acquired the lock.");
                System.out.println("Processing " + paymentId);
                // Simulate a slow payment gateway
                Thread.sleep(3000);
                System.out.println(paymentId + " completed.");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                paymentLock.unlock();
                System.out.println(Thread.currentThread().getName()
                        + " released the lock.");
            }
        } else {
            System.out.println(Thread.currentThread().getName()
                    + " -> Gateway busy. Queuing "+ paymentId + " for retry.");
        }
    }
    public static void main(String[] args) {
        TryLockDemo service = new TryLockDemo();
        Thread t1 = new Thread(
                () -> service.processPayment("PAY-101"),
                "Thread-1");
        Thread t2 = new Thread(
                () -> service.processPayment("PAY-102"),
                "Thread-2");
        t1.start();
        // Give Thread-1 time to acquire the lock
        try {
            Thread.sleep(200);
        } catch (InterruptedException ignored) {
        }
        t2.start();
    }
}
