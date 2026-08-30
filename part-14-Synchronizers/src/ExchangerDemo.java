import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrates using an Exchanger to swap transaction batches between two
 * threads: a batch producer and a batch validator. Each thread hands off
 * its current batch and receives the other thread's batch in a single
 * atomic rendezvous point.
 */
public class ExchangerDemo {

    private final Exchanger<List<String>> exchanger = new Exchanger<>();

    private void runProducer() {
        List<String> batchA = prepareBatchA();
        try {
            System.out.println("Producer prepared: " + batchA);
            List<String> received = exchanger.exchange(batchA);
            System.out.println("Producer received back: " + received);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void runValidator() {
        List<String> batchB = prepareBatchB();
        try {
            System.out.println("Validator prepared: " + batchB);
            List<String> received = exchanger.exchange(batchB);
            System.out.println("Validator received: " + received);
            System.out.println("Validator processing exchanged batch...");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private List<String> prepareBatchA() {
        List<String> batch = new ArrayList<>();
        batch.add("TXN-101");
        batch.add("TXN-102");
        return batch;
    }

    private List<String> prepareBatchB() {
        List<String> batch = new ArrayList<>();
        batch.add("TXN-201");
        return batch;
    }

    public static void main(String[] args) throws InterruptedException {
        ExchangerDemo demo = new ExchangerDemo();
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(demo::runProducer);
        executor.submit(demo::runValidator);

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }
}