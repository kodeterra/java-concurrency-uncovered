import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PaymentProcessorUsingExecute {
    public static void main(String[] args) {
        ExecutorService executors = Executors.newFixedThreadPool(3);
        List<String> pendingPayments = List.of("PAY-101", "PAY-102", "PAY-103", "PAY-104", "PAY-105");

        for(String paymentRef : pendingPayments)
        {
            executors.execute(() -> {
                System.out.println(Thread.currentThread().getName()+"is processing "+ paymentRef);
                    });
        }
        executors.shutdown();
    }
}
