package model;

import strategies.PaymentStrategy;

import java.util.List;

public abstract class Order {

    private static int nextOrderId;
    private  int id;
    private User user;
    private Restaurant restaurant;
    private List<MenuItem> menuItems;
    private PaymentStrategy paymentStrategy;
    private double total;
    private String scheduled;

    public Order() {
        id = ++nextOrderId;
    }

    public boolean processPayment(){
        if(paymentStrategy!=null){
            paymentStrategy.pay(total);
            return true;
        }
        else{
            System.out.println("Payment failed");
            return false;
        }
    }

    public abstract String getType();

    public int getId() {
        return id;
    }

    public void setUser(User u){
        this.user = u;
    }

    public User getUser() {
        return user;
    }

    public void setRestaurant(Restaurant r){
        this.restaurant = r;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
        total = 0;

        for (MenuItem item : menuItems) {
            total+=item.getPrice();
        }
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy){
        this.paymentStrategy = paymentStrategy;
    }

    public void setScheduled(String scheduled){
        this.scheduled = scheduled;
    }
    public String getScheduled() {
        return scheduled;
    }
    public double getTotal() {
        return total;
    }
}