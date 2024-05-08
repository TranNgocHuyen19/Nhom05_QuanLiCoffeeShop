package entities;

import db.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {

    private String orderID;
    private LocalDateTime orderDate;
    private Payment payment;
    private List<Orderdetail> listOfODs;
    private Customer cust;
    private Employee emp;
    private double discount;
    private double totalDue = 0;

    private static LocalDate currentDate = LocalDate.now();
    private static int sequence = 1;

    public Order() {
        this.orderID = generatID();
    }

    public Order(Customer cust, Employee emp) {
        this.orderID = generatID();
        this.orderDate = LocalDateTime.now();
        this.listOfODs = new ArrayList<>();
        this.cust = cust;
        this.emp = emp;
    }

    public Order(Payment payment, List<Orderdetail> listOfODs, Customer cust, Employee emp, double discount) {
        this.orderID = generatID();
        this.orderDate = LocalDateTime.now();
        this.payment = payment;
        this.listOfODs = listOfODs;
        this.cust = cust;
        this.emp = emp;
        this.discount = discount;
        this.totalDue = setTotalDue();
    }
    
    

    public Order(String orderID, LocalDateTime orderDate, Payment payment, List<Orderdetail> listOfODs, Customer cust, Employee emp, double discount) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.payment = payment;
        this.listOfODs = listOfODs;
        this.cust = cust;
        this.emp = emp;
        this.discount = discount;
        this.totalDue = setTotalDue();
    }

    public Order(String orderID, LocalDateTime orderDate,
            Payment payment, List<Orderdetail> listOfODs, Customer cust, Employee emp) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.payment = payment;
        this.listOfODs = listOfODs;
        this.cust = cust;
        this.emp = emp;
        
        this.totalDue = setTotalDue();
    }

    public static String generatID() {
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("ddMMyy"));
        String newOrderID = null;
        String formattedSequence = null;
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String id = null;
        try {
            stm = con.prepareStatement("select top 1 OrderID \n"
                    + "from [Order] \n"
                    + "where year(OrderDate) = year(getdate()) and month(OrderDate) = month(getdate()) and day(OrderDate) = day(getdate())  \n"
                    + "order by OrderID desc");
            rs = stm.executeQuery();
            if (rs.next()) {
                id = rs.getString("OrderID");
            }
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        if (id != null) {
            String orderDate = id.substring(1, 7);
            if (!orderDate.equals(formattedDate)) {
                formattedSequence = String.format("%04d", sequence++);
            } else {
                sequence = Integer.parseInt(id.substring(7));
                formattedSequence = String.format("%04d", ++sequence);
            }
        } else {
            formattedSequence = String.format("%04d", sequence++);
        }
        newOrderID = "O" + formattedDate + formattedSequence;
        return newOrderID;
    }

    public String getOrderID() {
        return orderID;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public List<Orderdetail> getListOfODs() {
        return listOfODs;
    }

    public void setListOfOD(List<Orderdetail> listOfODs) {
        this.listOfODs = listOfODs;
        this.totalDue = setTotalDue();
    }

    public Customer getCust() {
        return cust;
    }

    public void setCust(Customer cust) {
        this.cust = cust;
    }

    public Employee getEmp() {
        return emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    public double getTotalDue() {
        return this.totalDue;
    }

    private double setTotalDue() {
        double totalDue = 0;
        for (Orderdetail od : listOfODs) {
            totalDue += od.getTotalPrice();
        }
        return totalDue - discount;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.orderID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        return Objects.equals(this.orderID, other.orderID);
    }

    @Override
    public String toString() {
        return "Order{" + "orderID=" + orderID + ", orderDate=" + orderDate + ", payment=" + payment + ", listOfODs=" + listOfODs + ", cust=" + cust + ", emp=" + emp + ", discount=" + discount + ", totalDue=" + totalDue + '}';
    }

    
}
