package model;

import java.util.*;

public class Cart{

    private Restaurant restaurant;
    private final List<MenuItem> menuItems = new ArrayList<>();
    private double price;

    public void addItem(MenuItem item){
        if(restaurant==null){
            System.out.println("restaurant is not set");
            return;
        }

        menuItems.add(item);
    }

    public double getPrice(){
        double sum = 0;
        for(MenuItem item:menuItems){
            sum += item.getPrice();
        }
        return sum;
    }

    public boolean isEmpty()
    {
        return restaurant == null || menuItems.isEmpty();
    }

    public void clear(){
        restaurant = null;
        menuItems.clear();
    }

    public void setRestaurant(Restaurant res)
    {
        this.restaurant = res;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }
}
