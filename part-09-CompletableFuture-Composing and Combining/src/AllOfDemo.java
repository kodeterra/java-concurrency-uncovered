import java.util.concurrent.CompletableFuture;

public class AllOfDemo {

    public static void main(String[] args) {

        CompletableFuture<String> paymentFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Processing payment...");
            return "Payment Successful";
        });
        CompletableFuture<String> invoiceFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Generating invoice...");
            return "Invoice Generated";
        });
        CompletableFuture<String> emailFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Sending confirmation email...");
            return "Email Sent";
        });
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(paymentFuture, invoiceFuture, emailFuture);
        System.out.println("Continuing with other tasks...");
        allTasks.join();

        System.out.println(paymentFuture.join());
        System.out.println(invoiceFuture.join());
        System.out.println(emailFuture.join());
    }
}