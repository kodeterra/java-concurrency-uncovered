import java.util.concurrent.locks.ReentrantLock;

public class ReentrantDemo{
    private final ReentrantLock lock = new ReentrantLock();

    public void processPayment() {
        System.out.println("processPayment() -> acquiring lock");
        lock.lock();
        System.out.println("processPayment() Hold Count : "
                + lock.getHoldCount());
        try {
            validatePayment();
            System.out.println("processPayment() -> payment completed");
        } finally {
            System.out.println("processPayment() -> releasing lock");
            lock.unlock();
            System.out.println("processPayment() Hold Count : "
                    + lock.getHoldCount());
        }
    }

    private void validatePayment() {
        System.out.println("validatePayment() -> acquiring SAME lock again");
        lock.lock();
        System.out.println("validatePayment() Hold Count : "
                + lock.getHoldCount());
        try {
            System.out.println("validatePayment() -> lock acquired");
        } finally {
            System.out.println("validatePayment() -> releasing lock");
            lock.unlock();
            System.out.println("validatePayment() Hold Count : "
                    + lock.getHoldCount());
        }
    }

    public static void main(String[] args) {
        new ReentrantDemo().processPayment();
    }
}
