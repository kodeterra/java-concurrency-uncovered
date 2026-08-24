import java.util.concurrent.CompletableFuture;

public class SupplyAsyncDemo {
    public static void main(String[] args) {
        CompletableFuture<String> paymentStatus = CompletableFuture.supplyAsync(() -> {
            System.out.println("Authorizing payment");
            try {
                Thread.sleep(2000); // Simulate payment authorization
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Payment Approved";
        });
        System.out.println("Continuing with order processing...");
        String status = paymentStatus.join();
        System.out.println("Payment Status: " + status);
    }
}