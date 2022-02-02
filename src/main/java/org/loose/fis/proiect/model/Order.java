package org.loose.fis.proiect.model;

import org.dizitart.no2.objects.Id;

import java.util.ArrayList;
import java.util.Objects;

public class Order
{
    private String username;

    private ArrayList <Product> order = new ArrayList <Product>();
    public Order(String username, ArrayList<Product> order)
    {
        this.username = username;
        this.order = order;
    }
    public Order() {
    }


    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public ArrayList<Product> getOrder()
    {
        return order;
    }

    public void setOrder(ArrayList<Product> order)
    {
        this.order = order;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order1 = (Order) o;
        return Objects.equals(username, order1.username) && Objects.equals(order, order1.order);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(username, order);
    }
}
