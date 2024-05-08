package entities;

import db.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Customer {
    private String CustomerID;
    private String name;
    private String phoneNumber;
    private int rewardPoint;
    
    private static int sequence = 1;

    public Customer(String CustomerID) {
        this.CustomerID = CustomerID;
    }

    public Customer(String name, String phoneNumber) {
        this.CustomerID = generatID();
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Customer(String CustomerID, String name, String phoneNumber) {
        this.CustomerID = CustomerID;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Customer(String CustomerID, String name, String phoneNumber, int rewardPoint) {
        this.CustomerID = CustomerID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.rewardPoint = rewardPoint;
    }
    
    

    public static String generatID() {
        String newCusID = null;
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String formattedSequence = null;
        String id = null;
        try {
            stm = con.prepareStatement("select top 1 CustomerID from Customer order by CustomerID desc");
            rs = stm.executeQuery();
            if (rs.next()) {
                id = rs.getString("CustomerID");
            }
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        if (id != null) {
            sequence = Integer.parseInt(id.substring(4)) + 1;
        }
        formattedSequence = String.format("%04d", sequence++);
        newCusID = "Cust" + formattedSequence;
        return newCusID;
    }
    
    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String CustomerID) {
        this.CustomerID = CustomerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(int rewardPoint) {
        this.rewardPoint = rewardPoint;
    }
    
    

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Customer{" + "CustomerID=" + CustomerID + ", name=" + name + ", phoneNumber=" + phoneNumber + ", rewardPoint=" + rewardPoint + '}';
    }

    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.CustomerID);
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
        final Customer other = (Customer) obj;
        return Objects.equals(this.CustomerID, other.CustomerID);
    }
}
