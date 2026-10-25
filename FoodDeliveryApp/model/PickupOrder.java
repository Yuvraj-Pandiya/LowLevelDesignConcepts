package model;

public class PickupOrder extends Order
{
    private String restaurantAdd;

    public String getType()
    {
        return "Pickup";
    }

    public void  setRestaurantAdd(String resAdd)
    {
        this.restaurantAdd = resAdd;
    }

    public String getRestaurantAdd()
    {
        return restaurantAdd;
    }
}