import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo {
    private static  final int MAX_CONCURRENT_PAYMENTS=5;
    private final AtomicInteger activeTransactions =  new AtomicInteger(0);

    public void onPaymentStarted(){
        int current = activeTransactions.incrementAndGet();
        System.out.println("Payment started.. Active transaction :" +current);

        if(current >MAX_CONCURRENT_PAYMENTS)
        {
            activeTransactions.decrementAndGet();
            throw new IllegalThreadStateException("Too many concurrent payments. Please try again later");
        }
    }
    public void onPaymentCompleted(){
        int current = activeTransactions.decrementAndGet();
        System.out.println("Payment completed. Active transactions"+current);

    }
    public int getActiveTransactions() {
        return activeTransactions.get();
    }
    public static void main(String[] args) {
        AtomicIntegerDemo paymentService = new AtomicIntegerDemo();
        try {
            paymentService.onPaymentStarted();
            paymentService.onPaymentStarted();
            paymentService.onPaymentStarted();
            // Simulate payment completion
            paymentService.onPaymentCompleted();
            paymentService.onPaymentCompleted();
            System.out.println("Current active transactions: "+ paymentService.getActiveTransactions());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
