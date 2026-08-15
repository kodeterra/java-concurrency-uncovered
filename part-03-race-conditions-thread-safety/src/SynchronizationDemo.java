public class SynchronizationDemo {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            SavingsAccount account = new SavingsAccount(100);
            // Both threads operate on the same SavingsAccount instance.
            Runnable task = () -> account.withdrawWithSynchronization(50);
            Thread t1 = new Thread(task);
            Thread t2 = new Thread(task);
            t1.start();
            t2.start();
            // Wait for both threads to complete before
            // printing the final account balance.
            t1.join();
            t2.join();

            System.out.printf("Run %d -> Final Balance: %d%n",
                    i + 1,
                    account.getBalance());
        }
    }
}