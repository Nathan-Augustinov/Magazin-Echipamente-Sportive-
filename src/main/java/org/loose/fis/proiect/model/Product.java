package org.loose.fis.proiect.model;

import org.dizitart.no2.objects.Id;
import java.util.Objects;

public class Product
{
    @Id
    private String name;
    private String price;
    private String stock;
    private String category;
    private String company;

    public Product(String name, String price, String stock, String category, String company) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.company = company;
    }

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(stock, product.stock) && Objects.equals(category, product.category) && Objects.equals(company, product.company);
    }

    @Override
    public String toString() {
        return "Name: "+ name + "         Price: "  + price +"         Stock: " + stock + "         Category: " + category +"         Company: "+ company;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, stock, category, company);
    }
}
