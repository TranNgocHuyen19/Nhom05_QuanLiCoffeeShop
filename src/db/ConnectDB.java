/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daoducdanh
 */
public class ConnectDB {

    private static ConnectDB instance = new ConnectDB();
    public static Connection con = null;

    public static ConnectDB getInstance() {
        return instance;
    }

    public static void connect() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;databasename=CoffeeShop;encrypt=false;trustServerCertificate=true";
        String user = "sa";
        String password = "sapassword";
        con = DriverManager.getConnection(url, user, password);
    }

    public void disconnect() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() {
        try {
            connect();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return con;
    }

}
