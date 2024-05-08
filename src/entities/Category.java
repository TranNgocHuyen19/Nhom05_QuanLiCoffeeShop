package entities;

import db.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Category {
    private String categoryID;
    private String name;
    private String description;
    
    private static int sequence = 1;

    public Category(String categoryID) {
        this.categoryID = categoryID;
    }

    public Category(String name, String description) {
        this.categoryID = generatID();
        this.name = name;
        this.description = description;
    }

    public Category(String categoryID, String name, String description) {
        this.categoryID = categoryID;
        this.name = name;
        this.description = description;
    }

    public static String generatID() {
        String newBevID = null;
        String formattedSequence = null;
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String id = null;
        try {
            stm = con.prepareStatement( "select top 1 CategoryID from Category order by CategoryID desc");
            rs = stm.executeQuery();
            if(rs.next()){
                id = rs.getString("CategoryID");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }    
        if(id != null){
            sequence = Integer.parseInt(id.substring(1)) + 1;
        }
        formattedSequence = String.format("%03d", sequence++);
        newBevID = "C" + formattedSequence;
        return newBevID;
    }
    
    public String getCategoryID() {
        return categoryID;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Category{" + "categoryID=" + categoryID + ", name=" + name + ", description=" + description + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.categoryID);
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
        final Category other = (Category) obj;
        return Objects.equals(this.categoryID, other.categoryID);
    }
    
    
}
