public class PaymentProcessorRunnable implements  Runnable{

    private final String transactionId;
    public PaymentProcessorRunnable(String transactionId)
    {
        this.transactionId = transactionId;
    }
    @Override
    public void run() {
        System.out.println("Processing transaction.."+transactionId);
    }

    public static void main(String[] args) {
        Thread payment1 = new Thread(new PaymentProcessorRunnable("TXN-001"));
        Thread payment2 = new Thread(new PaymentProcessorRunnable("TXN-002"));
        Thread payment3 = new Thread(new PaymentProcessorRunnable("TXN-003"));
        payment1.start();
        payment2.start();
        payment3.start();
    }
}
