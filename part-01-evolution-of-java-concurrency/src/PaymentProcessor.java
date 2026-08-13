public class PaymentProcessor extends Thread{
    private final String transactionId;

    public PaymentProcessor(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public void run(){
        System.out.println("Processing transaction.."+transactionId);
    }

    public static void main(String[] args) {
        new PaymentProcessor("TXN-001").start();
        new PaymentProcessor("TXN-002").start();
        new PaymentProcessor("TXN-003").start();
    }
}
