import java.util.concurrent.CompletableFuture;

public class ExceptionallyDemo {
    public static void main(String[] args) {
        CompletableFuture<Object> payment =
                CompletableFuture.supplyAsync(() -> {
                    System.out.println("Authorizing payment...");
                    throw new RuntimeException("Payment gateway unavailable");
                }).exceptionally(ex -> {
                    System.out.println("Exception: " + ex.getMessage());
                    return "Payment Pending";
                });
        System.out.println(payment.join());
    }
}