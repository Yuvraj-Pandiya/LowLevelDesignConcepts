package factories;

import model.*;
import strategies.PaymentStrategy;

import java.util.List;

public class ScheduleOrderFactory implements OrderFactory{

    private String scheduled;

    public ScheduleOrderFactory(String scheduleTime){
        this.scheduled = scheduleTime;
    }

    @Override
    public Order createOrder(User user, Cart cart, Restaurant restaurant, List<MenuItem> menuItemList, PaymentStrategy paymentStrategy,double price,String orderType) {
        return null;
    }
}