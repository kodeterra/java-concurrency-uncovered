
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceDemo {
    private final AtomicStampedReference<String> config =
            new AtomicStampedReference<>("https://api.v1.example.com", 0);

    public boolean updateConfiguration(String expected,
                                       String updated,
                                       int expectedVersion) {
        return config.compareAndSet(expected,updated,expectedVersion,expectedVersion + 1);
    }
    public static void main(String[] args) {
        AtomicStampedReferenceDemo service = new AtomicStampedReferenceDemo();

        // Thread A reads the configuration and plans to update later
        String staleValue = service.config.getReference();
        int staleVersion = service.config.getStamp();

        System.out.println("Thread A read:");
        System.out.println("Value   : " + staleValue);
        System.out.println("Version : " + staleVersion);

        // Thread B updates Config V1 -> Config V2
        service.updateConfiguration(staleValue,
                "https://api.v2.example.com",0);

        System.out.println("\nThread B updated configuration");
        System.out.println("Value   : " + service.config.getReference());
        System.out.println("Version : " + service.config.getStamp());

        // Thread C restores Config V2 -> Config V1
        service.updateConfiguration(
                "https://api.v2.example.com",
                staleValue,1);

        System.out.println("\nThread C restored the original configuration");
        System.out.println("Value   : " + service.config.getReference());
        System.out.println("Version : " + service.config.getStamp());

        System.out.println("\nThe value looks unchanged, but the version has changed.");

        // Thread A attempts to use its stale information
        boolean success = service.updateConfiguration(
                staleValue,
                "https://api.v3.example.com",
                staleVersion);

        System.out.println("\nThread A attempts its original update...");
        System.out.println("Update succeeded: " + success);
        if (!success) {

            System.out.println("\nStale update rejected!");
            System.out.println("The configuration changed while Thread A was working.");
            System.out.println("Thread A must reload the latest configuration before retrying.");
        }

        System.out.println("\nFinal Configuration");
        System.out.println("Value   : " + service.config.getReference());
        System.out.println("Version : " + service.config.getStamp());
    }
}