import model.Order;
import model.Restaurant;
import model.User;
import strategies.UpiPaymentStrategy;

import java.util.List;

public class Main {
        public static void main(String[] args)
        {
                YomatoApp app = new YomatoApp();

                User user = new User(101,"rohan","indore");
                System.out.println("User" + user.getName() + " is active.");

                List<Restaurant> restaurantList = app.searchRestaurants("indore");

                if(restaurantList.isEmpty())
                {
                        System.out.println("No Restaurant found");
                        return;
                }

                System.out.println("Founded Restaurant : ");

                for(var restaurant : restaurantList){
                        System.out.println(restaurant.getName());
                }

                System.out.println("Selected a Restaurant : ");

                app.selectRestaurant(user,restaurantList.get(0));

                System.out.println("Selected a Restaurant : " + restaurantList.get(0).getName());

                app.addToCart(user, 2);
                app.addToCart(user, 3);

                app.printUserCart(user);

                Order order = app.checkOutNow(user,"Delivery",new UpiPaymentStrategy("1222222"));
                if (order == null) {
                        System.out.println("Unable to place order: cart is empty.");
                        return;
                }
                app.payForOrder(user,order);

        }
}
