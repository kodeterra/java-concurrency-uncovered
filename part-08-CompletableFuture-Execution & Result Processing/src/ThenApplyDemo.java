import java.util.concurrent.CompletableFuture;

public class ThenApplyDemo {
    public static void main(String[] args) {
        CompletableFuture<Double> finalAmount = CompletableFuture.supplyAsync(()->{
            System.out.println("Retrieving Order Amount");
            return 100.0;
        }).thenApply(amount->amount*.90);
        System.out.println("Continuing with order processing");
        System.out.println("Final amount: $"+ finalAmount.join());
    }
}