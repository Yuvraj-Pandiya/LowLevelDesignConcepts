package strategies;

public class UpiPaymentStrategy  implements PaymentStrategy {

    private String mobile;

    public UpiPaymentStrategy(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using mobile number  " + mobile);
    }
}