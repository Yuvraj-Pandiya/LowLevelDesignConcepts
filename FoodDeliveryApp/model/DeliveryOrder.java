package model;

public class DeliveryOrder extends Order
{
    private String userAdd;

    public String getType()
    {
        return "Delivery";
    }

    public void  setUserAdd(String resAdd)
    {
        this.userAdd = resAdd;
    }

    public String getUserAdd()
    {
        return userAdd;
    }
}