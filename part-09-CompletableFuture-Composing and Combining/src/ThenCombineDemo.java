import java.util.concurrent.CompletableFuture;

public class ThenCombineDemo {
    public static void main(String[] args) {
        CompletableFuture<String> paymentFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching payment details...");
            return "Payment Successful";
        });
        CompletableFuture<String> customerFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching customer details...");
            return "Customer-1";
        });
        CompletableFuture<String> confirmationFuture = paymentFuture.thenCombine(customerFuture, (paymentStatus, customerName) -> "Confirmation: " + customerName + " - " + paymentStatus);
        System.out.println("Continuing with other tasks...");
        System.out.println(confirmationFuture.join());
    }
}