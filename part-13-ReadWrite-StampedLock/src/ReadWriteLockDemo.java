import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private Account account;

    public ReadWriteLockDemo(Account account) {
        this.account = account;
    }

    public Account viewAccount() {
        lock.readLock().lock();
        try {
            // simulate some read work
            System.out.println(Thread.currentThread().getName() + " reading balance: " + account.getBalance());
            return account;
        } finally {
            lock.readLock().lock();
        }
    }

    public void processPayment(double amount) {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " processing payment: " + amount);
            account.withdraw(amount);
            System.out.println(Thread.currentThread().getName() + " new balance: " + account.getBalance());
        } finally {
            lock.writeLock().lock();
        }
    }

    // Simple Account class for demo
    static class Account {
        private double balance;

        public Account(double balance) {
            this.balance = balance;
        }

        public synchronized double getBalance() {
            return balance;
        }

        public void withdraw(double amount) {
            if (amount > balance) {
                throw new IllegalStateException("Insufficient funds");
            }
            balance -= amount;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Account account = new Account(10000.0);
        ReadWriteLockDemo service = new ReadWriteLockDemo(account);

        ExecutorService executor = Executors.newFixedThreadPool(6);

        // multiple readers
        for (int i = 0; i < 3; i++) {
            executor.submit(service::viewAccount);
        }

        // multiple writers
        for (int i = 0; i < 3; i++) {
            executor.submit(() -> service.processPayment(500.0));
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Final balance: " + account.getBalance());
    }
}