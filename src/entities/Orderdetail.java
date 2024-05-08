package entities;

import dao.*;
import java.time.LocalDateTime;
import java.util.Objects;

public class Orderdetail {

    private Beverage beverage;
    private int orderQty;
    private double unitPrice;
    private String description;
    private DAOBeverage db = new DAOBeverage();

    public Orderdetail(Beverage beverage, int orderQty, String description) {
        this.beverage = beverage;
        this.orderQty = orderQty;
        this.description = description;
        this.unitPrice = beverage.getSellingPrice();
        
    }

    public double getUnitPrice() {
        return unitPrice;
    }
    
    public Beverage getBeverage() {
        return beverage;
    }

    public void setBeverage(Beverage beverage) {
        this.beverage = beverage;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public double getTotalPrice() {
        Beverage bev = db.findById(beverage.getBeverageID());
        return bev.getSellingPrice() * orderQty;
    }

}
