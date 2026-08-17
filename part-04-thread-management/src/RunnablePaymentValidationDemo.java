public class RunnablePaymentValidationDemo {
    public static void main(String[] args) throws InterruptedException {
        Runnable paymentValidationTask = () -> {
            boolean approved = validatePayment();
            System.out.println(
                    "Payment validation result: " + approved
            );
        };
        Thread worker = new Thread(paymentValidationTask,"payment-validation-worker");
        worker.start();
        worker.join();
    }

    private static boolean validatePayment() {
        // Simulate payment validation
        return true;
    }
}