import java.util.concurrent.*;

public class FutureExceptionDemo {
    public static void main(String[] args) {
        ExecutorService paymentPool = Executors.newFixedThreadPool(2);
        Callable<String> paymentTask = () -> {
            System.out.println("Processing payment on " + Thread.currentThread().getName());
            throw new RuntimeException("Payment gateway unavailable");
        };
        Future<String> future = paymentPool.submit(paymentTask);
        try {
            String result = future.get();
            System.out.println("Payment result: " + result);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Payment processing was interrupted.");
        } catch (ExecutionException e) {
            System.out.println("Payment failed: " + e.getCause().getMessage());
        }
        paymentPool.shutdown();
    }
}