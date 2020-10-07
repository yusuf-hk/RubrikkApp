package com.harmankaya.rubrikkapp3.model;

public class Item
{
    private int id;

    private String itemName;

    private String created;

    private int price;

    private String description;

    private boolean sold;

    private int userid;

    public Item(String itemName, int price, String description)
    {
        this.itemName = itemName;
        this.price = price;
        this.description = description;
    }

    public Item(int id, String itemName, String created, int price, String description, boolean sold, int userid)
    {
        this.id = id;
        this.itemName = itemName;
        this.created = created;
        this.price = price;
        this.description = description;
        this.sold = sold;
        this.userid = userid;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public String getCreated()
    {
        return created;
    }

    public void setCreated(String created)
    {
        this.created = created;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean isSold()
    {
        return sold;
    }

    public void setSold(boolean sold)
    {
        this.sold = sold;
    }

    public int getUserid()
    {
        return userid;
    }

    public void setUserid(int userid)
    {
        this.userid = userid;
    }

    @Override
    public String toString()
    {
        return "Item{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", created='" + created + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", sold=" + sold +
                ", userid=" + userid +
                '}';
    }
}
