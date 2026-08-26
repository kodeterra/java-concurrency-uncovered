import java.util.concurrent.locks.StampedLock;
import java.util.concurrent.TimeUnit;

public class PaymentServiceStampedLock {

    private final StampedLock lock = new StampedLock();
    private double balance = 10000.0;

    // Reader that deliberately takes time mid-read, so a writer can sneak in
    public void readBalanceSlowly(String readerName) {
        long stamp = lock.tryOptimisticRead();
        double localBalance = balance; // read #1
        System.out.println(readerName + ": optimistic stamp acquired = " + stamp);
        try {
            TimeUnit.MILLISECONDS.sleep(200); // simulate slow read, gives writer a window
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        boolean valid = lock.validate(stamp);
        System.out.println(readerName + ": validate(stamp) = " + valid);
        if (!valid) {
            System.out.println(readerName + ": optimistic read FAILED, falling back to readLock()");
            stamp = lock.readLock();
            try {
                localBalance = balance;
                System.out.println(readerName + ": re-read balance under readLock = " + localBalance);
            } finally {
                lock.unlockRead(stamp);
            }
        } else {
            System.out.println(readerName + ": optimistic read SUCCEEDED, balance = " + localBalance);
        }
    }

    public void write(String writerName, double amount) {
        long stamp = lock.writeLock();
        try {
            System.out.println(">>> " + writerName + ": acquired writeLock, modifying balance");
            balance -= amount;
        } finally {
            lock.unlockWrite(stamp);
            System.out.println(">>> " + writerName + ": released writeLock, new balance = " + balance);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PaymentServiceStampedLock demo = new PaymentServiceStampedLock();
        // Case 1: no writer interferes -> optimistic read should succeed
        System.out.println("--- Case 1: No concurrent write ---");
        demo.readBalanceSlowly("Reader-1");

        System.out.println();

        // Case 2: writer interferes mid-read -> optimistic read should fail and fall back
        System.out.println("--- Case 2: Concurrent write during read ---");
        Thread reader = new Thread(() -> demo.readBalanceSlowly("Reader-2"));
        Thread writer = new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(50); // let reader grab optimistic stamp first
            } catch (InterruptedException ignored) {}
            demo.write("Writer-1", 1000.0);
        });

        reader.start();
        writer.start();
        reader.join();
        writer.join();
    }
}