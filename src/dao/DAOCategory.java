/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.ConnectDB;
import entities.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pc
 */
public class DAOCategory implements BaseDAO<Category>{
    private List<Category> listOfCategories;
    Connection con = null;
    PreparedStatement stm = null;
    ResultSet rs = null;

    public DAOCategory() {
        this.listOfCategories = new ArrayList<Category>();
        //hi
    }

    @Override
    public List<Category> findAll() {
        listOfCategories = new ArrayList<Category>();
        con = ConnectDB.getConnection();
        this.listOfCategories = new ArrayList<Category>();
        try {
            stm = con.prepareStatement("select * from Category");
            rs = stm.executeQuery();

            while (rs.next()) {
                String cateID = rs.getString("CategoryID");
                String name = rs.getString("Name");
                String description = rs.getString("Description");

                listOfCategories.add(new Category(cateID, name, description));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listOfCategories;
    }

    @Override
    public Category findById(String id) {
        con = ConnectDB.getConnection();
        Category catgory = null;
        try {
            stm = con.prepareStatement("select * from Category where CategoryID = ?");
            stm.setString(1, id);
            rs = stm.executeQuery();

            if (rs.next()) {
                String cateID = rs.getString("CategoryID");
                String name = rs.getString("Name");
                String description = rs.getString("Description");

                catgory =  new Category(cateID, name, description);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return catgory;
    }

    @Override
    public boolean insert(Category t) {
        con = ConnectDB.getConnection();

        try {
            stm = con.prepareStatement("insert into Category values (?, ?, ?)");
            stm.setString(1, t.getCategoryID());
            stm.setString(2, t.getName());
            stm.setString(3, t.getDescription());

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Category t) {
        con = ConnectDB.getConnection();
        try {
            stm = con.prepareStatement("update Category set Name = ?, Description = ? where CategoryID = ?;");
            stm.setString(1, t.getName());
            stm.setString(2, t.getDescription());
            stm.setString(3, t.getCategoryID());

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        con = ConnectDB.getConnection();
        try {
            stm = con.prepareStatement("delete Category where CategoryID = ?");
            stm.setString(1, id);

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public Category findByName(String name) {
        con = ConnectDB.getConnection();
        Category catgory = null;
        try {
            stm = con.prepareStatement("select * from Category where Name = ?");
            stm.setString(1, name);
            rs = stm.executeQuery();

            if (rs.next()) {
                String cateID = rs.getString("CategoryID");
                String nameCat = rs.getString("Name");
                String description = rs.getString("Description");

                catgory =  new Category(cateID, name, description);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return catgory;
    }
    
    public List<Category> getListTopCate() {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        List<Category> listTopCate = new ArrayList<>();

        String cateID;
        Category cate;
        try {
            stm = con.prepareStatement("SELECT TOP 5 c.CategoryID\n"
                    + "FROM Category c \n"
                    + "JOIN Beverage b ON b.CategoryID = c.CategoryID \n"
                    + "JOIN OrderDetail o ON o.BeverageID = b.BeverageID\n"
                    + "GROUP BY c.CategoryID, c.Name, c.Description\n"
                    + "ORDER BY SUM(o.OrderQty)  DESC;");

            rs = stm.executeQuery();
            while (rs.next()) {
                cate = findById(rs.getString("CategoryID"));
                listTopCate.add(cate);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listTopCate;
    }
}
