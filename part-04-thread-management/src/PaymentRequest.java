public class PaymentRequest {
    private final int paymentId;

    public PaymentRequest(int paymentId)
    {
        this.paymentId = paymentId;
    }

    public int getPaymentId() {
        return paymentId;
    }
}
