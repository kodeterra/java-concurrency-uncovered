public class ManualThreadCoordinationDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread paymentCheck =
                new Thread(() -> validatePayment(), "payment-check");
        Thread fraudCheck =
                new Thread(() -> performFraudCheck(), "fraud-check");
        Thread accountCheck =
                new Thread(() -> checkAccountStatus(), "account-check");

        paymentCheck.start();
        fraudCheck.start();
        accountCheck.start();

        paymentCheck.join(2000);
        fraudCheck.join(2000);
        accountCheck.join(2000);
        System.out.println("All checks have been processed.");
    }

    private static void validatePayment() {
        simulateWork("Payment validation");
    }
    private static void performFraudCheck() {
        simulateWork("Fraud check");
        throw new RuntimeException("Fraud check service unavailable");
    }
    private static void checkAccountStatus() {
        simulateWork("Account status check");
    }
    private static void simulateWork(String operation) {
        System.out.println(operation + " running on " + Thread.currentThread().getName()
        );
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}