package services;

import model.Order;
import strategies.PaymentStrategy;

public class NotificationService{

    public static void notify(Order order){
        System.out.println("Notification: New " + order.getType() + "order placed");
        System.out.println("Order ID : " + order.getId());
        System.out.println("Customer : " + order.getUser().getName());
        System.out.println("Restaurant : " + order.getRestaurant().getName());
        System.out.println("Items Ordered :\n");

        for(var item : order.getMenuItems())
        {
            System.out.println("Item : " + item.getName()
            + "\nPrice : " + item.getPrice());
        }

        System.out.println("Total: " + order.getTotal());
        System.out.println("Scheduled For:" + order.getScheduled());
        System.out.println("Payment: Done");

        System.out.println("-------------------------------------------------------------");
    }
}