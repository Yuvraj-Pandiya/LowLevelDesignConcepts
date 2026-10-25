package factories;

import model.*;
import strategies.PaymentStrategy;

import java.util.List;

public interface   OrderFactory
{
    public Order createOrder(User user, Cart cart, Restaurant restaurant, List<MenuItem> menuItemList, PaymentStrategy paymentStrategy,double price, String orderType);
}