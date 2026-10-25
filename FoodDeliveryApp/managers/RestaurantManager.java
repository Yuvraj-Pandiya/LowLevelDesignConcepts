package managers;

import model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantManager{

    private List<Restaurant> restaurantList;

    private static RestaurantManager instance;

    private RestaurantManager(){
        restaurantList = new ArrayList<>();
    }

    public static RestaurantManager getInstance(){
        if(instance == null){
            instance = new RestaurantManager();
        }
        return instance;
    }

    public void addRestaurant(Restaurant r){
        restaurantList.add(r);
    }

    public List<Restaurant> searchByAddress(String add){
        List<Restaurant> list = new ArrayList<>();

        for(Restaurant r : restaurantList){
            if(add.equalsIgnoreCase(r.getAddress())){
                list.add(r);
            }
        }

        return list;
    }
}