import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PaymentProcessorUsingSubmit {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Future<String> paymentResult = executor.submit(()->{
            System.out.println("Processing Payment on"+Thread.currentThread().getName());
            Thread.sleep(1000);
            return "Payment Successful";
        });
        String result = paymentResult.get();
        System.out.println("Payment task submitted."+result);
        executor.shutdown();

    }
}
