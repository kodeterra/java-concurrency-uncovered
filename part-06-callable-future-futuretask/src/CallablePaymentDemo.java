import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallablePaymentDemo {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Callable<String> paymentTask = () -> {
            System.out.println("Processing payment on " + Thread.currentThread().getName());
            Thread.sleep(1000);
            return "Payment Successful";
        };
        Future<String> paymentResult = executor.submit(paymentTask);
        System.out.println("Payment submitted. Main thread continues...");
        System.out.println("Result: " + paymentResult.get()
        );
        executor.shutdown();
    }
}
