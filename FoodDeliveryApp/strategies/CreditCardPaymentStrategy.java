package strategies;

public class CreditCardPaymentStrategy  implements PaymentStrategy {

    private String cardNumber;

    public CreditCardPaymentStrategy(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using credit card number  " + cardNumber);
    }
}