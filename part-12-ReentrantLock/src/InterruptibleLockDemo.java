import java.util.concurrent.locks.ReentrantLock;

public class InterruptibleLockDemo {
    private final ReentrantLock lock = new ReentrantLock();
    public void processPayment() {
        try {
            System.out.println(Thread.currentThread().getName()
                    + " waiting for the lock...");
            lock.lockInterruptibly();
            try {
                System.out.println(Thread.currentThread().getName()
                        + " acquired the lock.");
                Thread.sleep(5000);
                System.out.println("Payment processed.");
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName()
                        + " released the lock.");
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName()
                    + " was interrupted while waiting.");
            Thread.currentThread().interrupt();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        InterruptibleLockDemo service = new InterruptibleLockDemo();
        Thread t1 = new Thread(service::processPayment, "Thread-1");
        Thread t2 = new Thread(service::processPayment, "Thread-2");
        t1.start();
        Thread.sleep(200);      // Thread-1 acquires the lock
        t2.start();
        Thread.sleep(1000);     // Thread-2 waits
        t2.interrupt();         // Cancel the waiting thread
    }
}
