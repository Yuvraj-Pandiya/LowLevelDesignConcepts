import factories.NowOrderFactory;
import factories.OrderFactory;
import factories.ScheduleOrderFactory;
import managers.OrderManager;
import managers.RestaurantManager;
import model.*;
import services.NotificationService;
import strategies.PaymentStrategy;

import java.util.ArrayList;
import java.util.List;

public class YomatoApp {
    YomatoApp(){
        initializeRestaurants();
    }

    public void initializeRestaurants(){
        Restaurant restaurant1 = new Restaurant("Guru Kripa","Indore");
        List<MenuItem> menuItems1 = new ArrayList<>();
        menuItems1.add(new MenuItem(1,"PavBhaji",230.0));
        menuItems1.add(new MenuItem(2,"MisalPav",240.0));
        menuItems1.add(new MenuItem(3,"BadaPav",80.0));
        restaurant1.setMenuItems(menuItems1);

        Restaurant restaurant2 = new Restaurant("Vrindavan","Indore");
        List<MenuItem> menuItems2 = new ArrayList<>();
        menuItems2.add(new MenuItem(1,"PavBhaji",130.0));
        menuItems2.add(new MenuItem(2,"MisalPav",140.0));
        menuItems2.add(new MenuItem(3,"BadaPav",40.0));
        restaurant2.setMenuItems(menuItems2);

        Restaurant restaurant3 = new Restaurant("Panjtara","Indore");
        List<MenuItem> menuItems3 = new ArrayList<>();
        menuItems3.add(new MenuItem(1,"Panner Masala",230.0));
        menuItems3.add(new MenuItem(2,"Kaju Kari",350.0));
        menuItems3.add(new MenuItem(3,"Laccha Paratha",150.0));
        restaurant3.setMenuItems(menuItems3);

        RestaurantManager restaurantManager = RestaurantManager.getInstance();
        restaurantManager.addRestaurant(restaurant1);
        restaurantManager.addRestaurant(restaurant2);
        restaurantManager.addRestaurant(restaurant3);

    }

    public List<Restaurant> searchRestaurants(String location){
        return RestaurantManager.getInstance().searchByAddress(location);
    }

    public void selectRestaurant(User user, Restaurant restaurant){
        Cart dart =  user.getCart();
        dart.setRestaurant(restaurant);
    }

    public void addToCart(User user, int itemCode){
        Restaurant restaurant = user.getCart().getRestaurant();

        if(restaurant == null){
            System.out.println("Please select a restaurant first");
            return;
        }

        for (var item : restaurant.getMenuItems()) {
            if(itemCode == item.getCode()){
                user.getCart().addItem(item);
                break;
            }
        }

    }

    public Order checkOutNow(User user, String orderType, PaymentStrategy paymentStrategy){
        return checkout(user,orderType,paymentStrategy,new NowOrderFactory());
    }

    public Order checkOutScheduled(User user, String orderType, PaymentStrategy paymentStrategy,String scheduleTime){
        return checkout(user,orderType,paymentStrategy,new ScheduleOrderFactory(scheduleTime));
    }

    public Order checkout(User user, String orderType, PaymentStrategy paymentStrategy, OrderFactory orderFactory){
        if(user.getCart().isEmpty()){
            return null;
        }

        Cart cart = user.getCart();
        Restaurant restaurant = user.getCart().getRestaurant();
        List<MenuItem> menuItems = user.getCart().getMenuItems();
        double price = cart.getPrice();

        Order order = orderFactory.createOrder(user,cart,restaurant,menuItems,paymentStrategy,price,orderType);
        OrderManager.getInstance().addOrder(order);
        return order;

    }

    public void payForOrder(User user,Order order){
        if(order == null)return;
        boolean isPaymentSuccess = order.processPayment();
        if(isPaymentSuccess){
            NotificationService notificationService = new NotificationService();
            NotificationService.notify(order);
            user.getCart().clear();
        }
    }

    public void printUserCart(User user){
        System.out.println("Items in cart:");
        System.out.println("-----------------------------");

        for(var item : user.getCart().getMenuItems()){
            System.out.println(item.getCode() + " " + item.getName() + ": " + item.getPrice());
        }
        System.out.println("-----------------------------");
        System.out.println("Grand Total : " + user.getCart().getPrice());
    }
}
