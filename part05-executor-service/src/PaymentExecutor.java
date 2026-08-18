import java.util.concurrent.*;

public class PaymentExecutor {

    private final ThreadPoolExecutor executor;
    public PaymentExecutor() {
        ThreadFactory threadFactory =
                new PaymentThreadFactory();
        this.executor = new ThreadPoolExecutor(
                4,                              // corePoolSize
                8,                              // maximumPoolSize
                30,                             // keepAliveTime
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),  // bounded queue
                threadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }
    public void submit(PaymentRequest payment) {
        executor.execute(() -> process(payment));
    }
    private void process(PaymentRequest payment) {
        long start = System.currentTimeMillis();
        System.out.printf(
                "%s processing %s ($%.2f)%n",
                Thread.currentThread().getName(),
                payment.paymentId(),
                payment.amount()
        );

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        long duration =
                System.currentTimeMillis() - start;
        System.out.printf(
                "%s completed %s in %d ms%n",
                Thread.currentThread().getName(),
                payment.paymentId(),
                duration
        );
    }
    public void printMetrics() {
        System.out.printf(
                "Pool Size: %d | Active: %d | Queue: %d | Completed: %d%n",
                executor.getPoolSize(),
                executor.getActiveCount(),
                executor.getQueue().size(),
                executor.getCompletedTaskCount()
        );
    }
    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(
                    30, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}