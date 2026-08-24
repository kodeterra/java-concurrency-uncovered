import java.util.concurrent.CompletableFuture;

public class ThenAcceptBothDemo {

    public static void main(String[] args) {

        CompletableFuture<String> paymentFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Processing payment...");
            return "Payment Successful";
        });

        CompletableFuture<String> customerFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching customer details...");
            return "Customer-1";
        });

        CompletableFuture<Void> confirmationTask = paymentFuture.thenAcceptBoth(customerFuture, (paymentStatus, customerName) -> System.out.println("Confirmation: " + customerName + " - " + paymentStatus));

        System.out.println("Continuing with other tasks...");

        confirmationTask.join();
    }
}