public class PaymentApplication {
    public static void main(String[] args) {
        PaymentExecutor paymentExecutor = new PaymentExecutor();
        for (int i = 1; i <= 25; i++) {
            PaymentRecord payment = new PaymentRecord("PAY-" + (1000 + i),
                    100 + i * 10);
            paymentExecutor.submit(payment);
        }
        paymentExecutor.printMetrics();
        paymentExecutor.shutdown();
    }
}