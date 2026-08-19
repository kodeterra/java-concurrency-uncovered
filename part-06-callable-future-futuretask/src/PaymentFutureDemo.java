import java.util.concurrent.*;

public class PaymentFutureDemo {
    public static void main(String[] args) {

        ExecutorService paymentPool = Executors.newFixedThreadPool(2);
        Callable<String> paymentTask = () -> {
            System.out.println("Processing payment on " + Thread.currentThread().getName());
            Thread.sleep(3000);
            return "Payment Successful";
        };
        Future<String> future = paymentPool.submit(paymentTask);
        if (!future.isDone()) {
            System.out.println("Payment is still being processed..."
            );
        }
        try {
            String result = future.get(10, TimeUnit.SECONDS);
            System.out.println("Payment result: " + result);
        } catch (TimeoutException e) {
            future.cancel(true);
            System.out.println("Payment processing timed out."
            );
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Payment processing was interrupted.");
        } catch (ExecutionException e) {
            System.out.println("Payment processing failed: " + e.getCause().getMessage());
        }
        paymentPool.shutdown();
    }
}