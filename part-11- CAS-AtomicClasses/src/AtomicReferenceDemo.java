import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDemo {
    private final AtomicReference<String> accessToken =
            new AtomicReference<>("token-v1");

    public void refreshToken(String expectedToken, String newToken) {
        if (accessToken.compareAndSet(expectedToken, newToken)) {
            System.out.println(Thread.currentThread().getName()
                    + " refreshed the token.");
        } else {
            System.out.println(Thread.currentThread().getName()
                    + " detected that another thread already refreshed the token.");
        }
    }

    public String getAccessToken() {
        return accessToken.get();
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicReferenceDemo service = new AtomicReferenceDemo();
        String currentToken = service.getAccessToken();
        int threadCount = 5;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> service.refreshToken(currentToken, "token-v2"));
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
        System.out.println("Final token: " + service.getAccessToken());
    }
}