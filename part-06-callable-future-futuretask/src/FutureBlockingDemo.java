import java.util.concurrent.*;

public class FutureBlockingDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService paymentPool = Executors.newFixedThreadPool(2);
        Callable<String> paymentTask = () -> {
            System.out.println("Payment processing on "+ Thread.currentThread().getName());
            Thread.sleep(3000);
            return "Payment Successful";
        };
        Future<String> future = paymentPool.submit(paymentTask);
        System.out.println("Payment submitted. Main thread continues...");

        // Do independent work while payment is processing
        System.out.println("Recording audit information...");
        Thread.sleep(1000);
        System.out.println("Updating payment metrics...");
        // Retrieve the result only when it is needed
        String result = future.get();
        System.out.println("Payment result: " + result);
        paymentPool.shutdown();
    }
}