import java.util.concurrent.CompletableFuture;
public class ThenRunDemo {
    public static void main(String[] args) {
        CompletableFuture<Void> auditTask = CompletableFuture.supplyAsync(() -> {
            System.out.println("Authorizing Payment");
            return "Payment Approved"; // Result is available but not used by thenRun()
        }).thenRun(() ->
                System.out.println("Audit log recorded: payment processing completed"));

        System.out.println("Continuing with order processing");
        auditTask.join();
    }
}