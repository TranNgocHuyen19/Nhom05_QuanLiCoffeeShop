/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.ConnectDB;
import entities.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
/**
 *
 * @author pc
 */
public class DAOPayment implements BaseDAO<Payment>{
    private List<Payment> listOfPayment;
    Connection con = null;
    PreparedStatement stm = null;
    ResultSet rs = null;

    public DAOPayment() {
        this.listOfPayment = new ArrayList<Payment>();
    }

    @Override
    public List<Payment> findAll() {
        listOfPayment = new ArrayList<Payment>();
        con = ConnectDB.getConnection();
        try {
            stm = con.prepareStatement("select * from Payment");
            rs = stm.executeQuery();

            while (rs.next()) {
                String paymentID = rs.getString("PaymentID");
                LocalDateTime paymentDate = rs.getTimestamp("PaymentDate").toLocalDateTime();
                String type = rs.getString("Type");
                
                listOfPayment.add(new Payment(paymentID, paymentDate, type));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listOfPayment;
    }

    @Override
    public Payment findById(String id) {
        con = ConnectDB.getConnection();
        Payment p = null;
        try {
            stm = con.prepareStatement("select * from Payment where PaymentID = ?");
            stm.setString(1, id);
            rs = stm.executeQuery();

            if (rs.next()) {
                String paymentID = rs.getString("PaymentID");
                LocalDateTime paymentDate = rs.getTimestamp("PaymentDate").toLocalDateTime();
                String type = rs.getString("Type");
                
                p = new Payment(paymentID, paymentDate, type);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return p;
    }

    @Override
    public boolean insert(Payment t) {
        con = ConnectDB.getConnection();

        try {
            stm = con.prepareStatement("insert into Payment values (?, ?, ?)");
            stm.setString(1, t.getPaymentID());
            
            Timestamp paymentTimeStamp = Timestamp.valueOf(t.getPaymentDate());
            stm.setTimestamp(2, paymentTimeStamp);
            stm.setString(3, t.getType());

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Payment t) {
        con = ConnectDB.getConnection();
        try {
            stm = con.prepareStatement("update Payment "
                    + "set PaymentDate = ?, Type = ?"
                    + "where PaymentID = ?");
            stm.setString(3, t.getPaymentID());
            Timestamp paymentTimestamp = Timestamp.valueOf(t.getPaymentDate());
            stm.setTimestamp(1, paymentTimestamp);
            stm.setString(2, t.getType());

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
            stm = con.prepareStatement("delete Payment where PaymentID = ?");
            stm.setString(1, id);

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
