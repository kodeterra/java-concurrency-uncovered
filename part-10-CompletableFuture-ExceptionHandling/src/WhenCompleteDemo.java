import java.util.concurrent.CompletableFuture;

public class WhenCompleteDemo {
    public static void main(String[] args) {
        CompletableFuture<String> payment = CompletableFuture.supplyAsync(() -> {
            System.out.println("Authorizing payment...");
            return "Payment Approved";
        }).whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Audit Log: " + result);
            } else {
                System.out.println("Audit Log: " + ex.getMessage());
            }
        });
        System.out.println(payment.join());
    }
}