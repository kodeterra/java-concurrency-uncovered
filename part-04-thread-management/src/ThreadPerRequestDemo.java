import java.util.ArrayList;
import java.util.List;

public class ThreadPerRequestDemo {
    public static void main(String[] args) throws InterruptedException{
        List<Thread> workers = new ArrayList<>();
        for(int i=0;i<=10_000;i++)
        {
            PaymentRequest request = new PaymentRequest(i);
            Thread worker = new Thread(
                    ()->processPayment(request), "payment-worker - "+request.getPaymentId());
            worker.start();
            workers.add(worker);
        }
        for (Thread worker : workers) {
            worker.join();
        }
    }
    private static void processPayment(PaymentRequest request) {
        System.out.println(
                "Processing payment " + request.getPaymentId()
                        + " on " + Thread.currentThread().getName()
        );

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}