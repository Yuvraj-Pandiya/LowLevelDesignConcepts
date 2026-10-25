package model;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {

    static int restaurantId;

    private int id;
    private String name;
    private String address;
    private List<MenuItem> menuItems;

    public Restaurant(String name, String address) {
        this.name = name;
        this.address = address;
        this.id = ++restaurantId; // id logic for autogeneration
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}