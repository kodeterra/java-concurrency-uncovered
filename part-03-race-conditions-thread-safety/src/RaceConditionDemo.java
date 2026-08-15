
public class RaceConditionDemo {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {       // Run 5 times to see inconsistency
            SavingsAccount account = new SavingsAccount(100);
            // Both threads operate on the same SavingsAccount instance.
            Runnable task = () -> account.withdraw(50);

            Thread t1 = new Thread(task);
            Thread t2 = new Thread(task);
            t1.start();
            t2.start();
            //join() ensures the main thread waits until both worker threads complete before printing the final balance.
            t1.join();
            t2.join();

            System.out.println("Final Balance:"+i + 1+"- "+account.getBalance());
        }
    }
}
