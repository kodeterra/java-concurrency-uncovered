import java.util.concurrent.CompletableFuture;

public class ThenComposeAsyncDemo {
    public static void main(String[] args) {
        CompletableFuture<String> paymentTask = CompletableFuture.supplyAsync(() -> {
            System.out.println("Validating order");
            return "Order valid";
        }).thenComposeAsync(status -> CompletableFuture.supplyAsync(() -> {
            System.out.println("Processing Payment : " + status);
            try {
                Thread.sleep(2000); // Simulate a call to an external payment gateway
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            return "Payment Received";
        }));

        System.out.println("Continuing with other tasks...");
        System.out.println(paymentTask.join());
    }
}
