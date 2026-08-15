public class SavingsAccount {
    private int balance;
    public SavingsAccount(int balance) {
        this.balance = balance;
    }
    public void  withdraw(int amount) {
        if (balance >= amount) {
            // Simulate real-world delay (e.g., DB call, network I/O)
            try {
                Thread.sleep(10);
            } catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
            balance = balance - amount;
        }
    }
    public synchronized void withdrawWithSynchronization(int amount) {
        if (balance >= amount) {

            // Introduces a small delay to demonstrate that
            // synchronization prevents thread interference.
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            balance -= amount;
        }
    }
    public int getBalance() {
        return balance;
    }
}
