package factories;

import model.*;
import strategies.PaymentStrategy;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class NowOrderFactory  implements OrderFactory{

    @Override
    public Order createOrder(User user, Cart cart, Restaurant restaurant, List<MenuItem> menuItemList, PaymentStrategy paymentStrategy,double price, String orderType) {
        Order order;

        if(orderType.equalsIgnoreCase("Delivery")){
            DeliveryOrder deliveryOrder = new DeliveryOrder();
            deliveryOrder.setUserAdd(user.getAddress());
            order = deliveryOrder;
        }
        else{
            PickupOrder pickupOrder = new PickupOrder();
            pickupOrder.setRestaurantAdd(restaurant.getAddress());
            order = pickupOrder;
        }

        order.setUser(user);
        order.setRestaurant(restaurant);
        order.setMenuItems(menuItemList);
        order.setPaymentStrategy(paymentStrategy);
        order.setScheduled(LocalDateTime.now().toString());
        order.setTotal(cart.getPrice());
        return order;
    }
}