import java.util.concurrent.CompletableFuture;

public class RunAsyncDemo {
    public static void main(String[] args) {
        CompletableFuture<Void> emailTask = CompletableFuture.runAsync(()->{
            System.out.println("Sending payment confirmation email");
            try{
                Thread.sleep(2000);
            }catch (InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
            System.out.println("Payment confirmation email sent");
        });
        System.out.println("Continuing with order fulfillment");
        emailTask.join();
    }

}