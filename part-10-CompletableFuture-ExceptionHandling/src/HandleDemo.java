import java.util.concurrent.CompletableFuture;

public class HandleDemo {
    public static void main(String[] args) {
        CompletableFuture<String> payment = CompletableFuture.<String>supplyAsync(() -> {
            System.out.println("Authorizing payment...");
            throw new RuntimeException("Payment gateway unavailable");
        }).handle((result, ex) -> {
            if (ex != null) {
                return "Payment Pending";
            }
            return result.toUpperCase();
        });

        System.out.println(payment.join());;
    }
}