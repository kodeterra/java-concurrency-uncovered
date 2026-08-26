import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

    private final ReentrantLock lock = new ReentrantLock();
    private double accountBalance = 1000;

    public void processPayment(double amount) {
        lock.lock();
        System.out.println(Thread.currentThread().getName()
                + " acquired the lock.");
        try {
            if (amount > accountBalance) {
                System.out.println(Thread.currentThread().getName()
                        + " - Payment declined. Insufficient balance for amount: " + amount);
                return; // finally still runs, lock still releases
            }
            accountBalance -= amount;
            System.out.println(Thread.currentThread().getName()
                    + " - Payment processed: " + amount + " | Balance: " + accountBalance);
        } finally {
            System.out.println(Thread.currentThread().getName()
                    + " released the lock.");

            lock.unlock();
        }
    }
    public double getBalance() {
        lock.lock();
        try {
            return accountBalance;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo paymentService = new ReentrantLockDemo();
        Runnable task = () -> paymentService.processPayment(600);
        // Simulate 8 concurrent payment requests hitting the same account
        Thread[] threads = new Thread[2];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(task, "Thread-" + i);
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
        System.out.println("Final balance: " + paymentService.getBalance());
    }
}