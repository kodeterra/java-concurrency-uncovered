import java.util.concurrent.locks.ReentrantLock;

public class FairLockDemo {

    private final ReentrantLock lock;
    public FairLockDemo(boolean fair) {
        this.lock = new ReentrantLock(fair);
    }
    public void accessResource() {
        System.out.println(Thread.currentThread().getName() + " waiting for the lock...");
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " acquired the lock.");
            // Sleep here widens the contention window so the fairness
            // (or lack of it) is actually visible in the output.
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(Thread.currentThread().getName() + " released the lock.");
            lock.unlock();
        }
    }

    private static void runDemo(String label, boolean fair) throws InterruptedException {
        System.out.println("\n--- " + label + " ---");
        FairLockDemo demo = new FairLockDemo(fair);
        for (int i = 1; i <= 20; i++) {
            Thread thread = new Thread(demo::accessResource, "Thread-" + i);
            thread.start();
            Thread.sleep(50); // stagger starts so ordering is easy to observe
        }
        Thread.sleep(2000); // let the batch finish before starting the next one
    }

    public static void main(String[] args) throws InterruptedException {
        runDemo("UNFAIR LOCK (default)", false);
        runDemo("FAIR LOCK", true);
    }
}
