import java.util.List;
import java.util.concurrent.*;

public class MultipleFutureDemo {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService paymentPool = Executors.newFixedThreadPool(3);
        List<Future<String>> futures = List.of(
                paymentPool.submit(() -> processPayment("Payment A", 8)),
                paymentPool.submit(() -> processPayment("Payment B", 2)),
                paymentPool.submit(() -> processPayment("Payment C", 1))
        );
        for (Future<String> future : futures) {
            try {
                System.out.println("Result: " + future.get());
            } catch (ExecutionException e) {
                System.out.println( "Payment failed: " + e.getCause().getMessage()
                );
            }
        }
        paymentPool.shutdown();
    }
    private static String processPayment(String payment,int seconds) throws InterruptedException {
        System.out.println( payment + " started on " + Thread.currentThread().getName());
        Thread.sleep(seconds * 1000L);
        System.out.println(payment + " completed");
        return payment + " successful";
    }
}
