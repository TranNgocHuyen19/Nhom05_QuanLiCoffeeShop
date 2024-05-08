/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pc
 */
public class Beverage {

    private static final double PROFIT = 5000;

    private String beverageID;
    private String name;
    private LocalDateTime modifiedDate;
    private boolean discontinued;
    private double purchasePrice;
    private double tax;
    private String imgPath;
    private Category category;

    private static int sequence = 1;

    public Beverage(Category category, String name, double purchasePrice) {
        this.beverageID = generatID();
        this.name = name;
        this.modifiedDate = LocalDateTime.now();

        this.discontinued = false;
        this.purchasePrice = purchasePrice;
        this.tax = tax;
        this.category = category;
        this.imgPath = imgPath;
    }

    public Beverage(String beverageID, String name, LocalDateTime modifiedDate, boolean discontinued, double purchasePrice, double tax, String imgPath, Category category) {
        this.beverageID = beverageID;
        this.name = name;
        this.modifiedDate = modifiedDate;
        this.discontinued = discontinued;
        this.purchasePrice = purchasePrice;
        this.tax = tax;
        this.imgPath = imgPath;
        this.category = category;
    }

    public static String generatID() {
        String newBevID = null;
        String formattedSequence = null;
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String id = null;
        try {
            stm = con.prepareStatement("select top 1 BeverageID from Beverage order by BeverageID desc");
            rs = stm.executeQuery();
            if (rs.next()) {
                id = rs.getString("BeverageID");
            }
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        if (id != null) {
            sequence = Integer.parseInt(id.substring(1)) + 1;
        }
        formattedSequence = String.format("%03d", sequence++);
        newBevID = "B" + formattedSequence;
        return newBevID;
    }

    public String getBeverageID() {
        return beverageID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public boolean isDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(boolean discontinued) {
        this.discontinued = discontinued;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Beverage{" + "beverageID=" + beverageID + ", name=" + name + ", modifiedDate=" + modifiedDate + ", discontinued=" + discontinued + ", purchasePrice=" + purchasePrice + ", tax=" + tax + ", category=" + category + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.beverageID);
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
        final Beverage other = (Beverage) obj;
        return Objects.equals(this.beverageID, other.beverageID);
    }

    public double getSellingPrice() {
        return purchasePrice + (purchasePrice * tax) + 0;
    }

}
