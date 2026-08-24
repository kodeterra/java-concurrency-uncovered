import java.util.concurrent.CompletableFuture;

public class AnyOfDemo {
    public static void main(String[] args) {
        CompletableFuture<String> gatewayA = CompletableFuture.supplyAsync(() -> {
            sleep(3000);
            return "Payment processed by Gateway A";
        });
        CompletableFuture<String> gatewayB = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return "Payment processed by Gateway B";
        });
        CompletableFuture<String> gatewayC = CompletableFuture.supplyAsync(() -> {
            sleep(2000);
            return "Payment processed by Gateway C";
        });
        CompletableFuture<Object> firstResponse = CompletableFuture.anyOf(gatewayA, gatewayB, gatewayC);
        System.out.println("Waiting for the first payment gateway...");
        System.out.println(firstResponse.join());
    }

    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
