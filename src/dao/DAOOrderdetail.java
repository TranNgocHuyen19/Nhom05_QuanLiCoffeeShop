/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.ConnectDB;
import entities.*;
import java.sql.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pc
 */
public class DAOOrderdetail implements BaseDAO<Orderdetail> {

    private List<Orderdetail> listODs;
    Connection con = null;
    PreparedStatement stm = null;
    ResultSet rs = null;

    public DAOOrderdetail() {
        this.listODs = new ArrayList<>();
    }

    @Override
    public List<Orderdetail> findAll() {
        listODs = new ArrayList<>();
        Connection con = null;

        try {
            con = ConnectDB.getConnection();
            PreparedStatement stm = con.prepareStatement("SELECT * FROM Orderdetail");
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String orderID = rs.getString("OrderID");
                String beverageID = rs.getString("BeverageID");
                int orderQty = rs.getInt("OrderQty");
                String description = rs.getString("Description");

                DAOBeverage db = new DAOBeverage();
                // Tạo đối tượng Orderdetail và thêm vào danh sách
                Orderdetail od = new Orderdetail(db.findById(beverageID), orderQty, description);
                listODs.add(od);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listODs;
    }

    public Orderdetail findById(String orderID, String beverageID) {
        Connection con = null;
        Orderdetail orderDetail = null;

        try {
            con = ConnectDB.getConnection();
            PreparedStatement stm = con.prepareStatement("SELECT * FROM Orderdetail WHERE OrderID = ? AND BeverageID = ?");
            stm.setString(1, orderID);
            stm.setString(2, beverageID);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                int orderQty = rs.getInt("OrderQty");
                LocalDateTime modifiedDate = rs.getTimestamp("ModifiedDate").toLocalDateTime();
                String description = rs.getString("Description");
                DAOBeverage db = new DAOBeverage();
                // Tạo đối tượng Orderdetail với các thông tin từ ResultSet
                orderDetail = new Orderdetail(db.findById(beverageID), orderQty, description);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return orderDetail;
    }


    public boolean insert(Orderdetail t, String orderID) {
        con = ConnectDB.getConnection();

        try {
            stm = con.prepareStatement("insert into Orderdetail ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description]) values (?, ?, ?, ?, ?)");
            stm.setString(1, orderID);
            stm.setString(2, t.getBeverage().getBeverageID());
            stm.setDouble(3, t.getBeverage().getSellingPrice());
            stm.setInt(4, t.getOrderQty());
            stm.setString(5, t.getDescription());

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean update(Orderdetail t, String orderID) {
        Connection con = null;
        try {
            con = ConnectDB.getConnection();
            PreparedStatement stm = con.prepareStatement("UPDATE Orderdetail SET UnitPrice = ?, OrderQty = ?, LineTotal = ?, ModifiedDate = ?, Description = ? WHERE OrderID = ? AND BeverageID = ?");

            stm.setDouble(1, t.getBeverage().getSellingPrice());
            stm.setInt(2, t.getOrderQty());
//            stm.setDouble(3, t.getDiscount());
            stm.setDouble(3, t.getTotalPrice());
            stm.setString(4, t.getDescription());
            stm.setString(5, orderID);
            stm.setString(6, t.getBeverage().getBeverageID());

            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean delete(String orderID, String beverageID) {
        Connection con = null;
        try {
            con = ConnectDB.getConnection();
            PreparedStatement stm = con.prepareStatement("DELETE FROM Orderdetail WHERE OrderID = ? AND BeverageID = ?");
            stm.setString(1, orderID);
            stm.setString(2, beverageID);

            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Orderdetail findById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean insert(Orderdetail t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update(Orderdetail t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
