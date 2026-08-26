import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerOperationDemo {
    public static void main(String[] args) {
        AtomicInteger counter = new AtomicInteger(10);
        System.out.println("Get And Increment Operation -getAndIncrement :"+counter.getAndIncrement());
        System.out.println("Get Operation - get() :"+ counter.get());
        System.out.println("Increment And Get Operation- incrementAndGet() :"+ counter.incrementAndGet());
        System.out.println("Get Operation - get() :"+ counter.get());
        System.out.println("Get And Add Operation- getAndAdd(delta) :"+ counter.getAndAdd(5));
        System.out.println("Get Operation - get() :"+ counter.get());
        System.out.println("Add And Get Operation- addAndGet(delta) :"+ counter.addAndGet(5));
        System.out.println("Get Operation - get() :"+ counter.get());
    }
}
