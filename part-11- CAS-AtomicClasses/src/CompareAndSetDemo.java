import java.security.SecureRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CompareAndSetDemo {

    private static final int ACTIVE = 0;
    private static final int USED = 1;
    private final AtomicInteger otpStatus = new AtomicInteger(0);
    private final String generatedOtp;

    private static final SecureRandom RANDOM = new SecureRandom();
    public CompareAndSetDemo() {
        this.generatedOtp = generateOtp();
    }
    private String generateOtp() {
        int otp = 100000 + RANDOM.nextInt(900000); // always 6 digits, 100000-999999
        return String.valueOf(otp);
    }
    public void verifyOtp(String otp) {
        if (!generatedOtp.equals(otp)) {
            System.out.println(Thread.currentThread().getName() + " -> Invalid OTP");
            return;
        }
        if (otpStatus.compareAndSet(ACTIVE, USED)) {
            System.out.println(Thread.currentThread().getName() + " -> OTP verified successfully.");
        } else {
            System.out.println(Thread.currentThread().getName() + " -> OTP has already been used.");
        }
    }
    public static void main(String[] args) throws InterruptedException {
        CompareAndSetDemo serviceDemo = new CompareAndSetDemo();
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 10; i++) {
            executor.submit(() -> serviceDemo.verifyOtp(serviceDemo.generatedOtp));
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
    }
}
