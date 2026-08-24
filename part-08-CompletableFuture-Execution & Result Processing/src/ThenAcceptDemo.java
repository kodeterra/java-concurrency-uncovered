import java.util.concurrent.CompletableFuture;

public class ThenAcceptDemo {
    public static void main(String[] args) {
        CompletableFuture<Void> emailTask = CompletableFuture.supplyAsync(() -> {
            System.out.println("Authorizing Payment");
            return "Payment Approved"; // Simulate a successful authorization
        }).thenAccept(status -> {
            if (status.equals("Payment Approved")) {
                System.out.println("Sending payment confirmation email");
            } else {
                System.out.println("Sending payment failure email");
            }
        });
        System.out.println("Continuing with order processing");
        emailTask.join();
    }
}