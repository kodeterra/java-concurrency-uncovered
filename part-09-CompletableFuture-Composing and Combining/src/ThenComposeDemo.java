import java.util.concurrent.CompletableFuture;

public class ThenComposeDemo {
    public static void main(String[] args) {
        CompletableFuture<String> paymentTask = CompletableFuture.supplyAsync(()->{
            System.out.println("Validating order");
            return "Order valid";
        }).thenCompose(status ->
                CompletableFuture.supplyAsync(()->{
                    System.out.println("Processing Payment : "+status);
                    return "Payment Received";
                }));
        System.out.println("Continuing with other tasks...");
        System.out.println(paymentTask.join());
    }
}
