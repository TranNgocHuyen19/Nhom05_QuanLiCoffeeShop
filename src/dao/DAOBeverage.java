/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.ConnectDB;
import entities.*;
import java.util.List;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author pc
 */
public class DAOBeverage implements BaseDAO<Beverage> {

    private List<Beverage> listOfBeverage;

    public DAOBeverage() {
        this.listOfBeverage = new ArrayList<>();
    }

    @Override
    public List<Beverage> findAll() {
        listOfBeverage = new ArrayList<>();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement("select [BeverageID], [Name], [PurchasePrice], [ModifiedDate], [CategoryID], [Discontinued], [Tax], [Image] from Beverage");
            rs = stm.executeQuery();

            while (rs.next()) {
                String id = rs.getString("BeverageID");
                String name = rs.getString("Name");
                double purchasePrice = rs.getDouble("PurchasePrice");
                LocalDateTime modifiedDate = rs.getTimestamp("ModifiedDate").toLocalDateTime();
                String categoryID = rs.getString("CategoryID");
                boolean discontinued = rs.getBoolean("Discontinued");
                double tax = rs.getDouble("Tax");
                String imgPath = rs.getString("Image");

                DAOCategory dc = new DAOCategory();
                Category category = dc.findById(categoryID);
                listOfBeverage.add(new Beverage(id, name, modifiedDate, discontinued, purchasePrice, tax, imgPath, category));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listOfBeverage;
    }

    @Override
    public Beverage findById(String id) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        Beverage b = null;
        try {
            stm = con.prepareStatement("select [BeverageID], [Name], [PurchasePrice], [ModifiedDate], [CategoryID], [Discontinued], [Tax], [Image] from Beverage where BeverageID = ?");
            stm.setString(1, id);
            rs = stm.executeQuery();

            if (rs.next()) {
                String BeverageID = rs.getString("BeverageID");
                String name = rs.getString("Name");
                double purchasePrice = rs.getDouble("PurchasePrice");
                LocalDateTime modifiedDate = rs.getTimestamp("ModifiedDate").toLocalDateTime();
                String categoryID = rs.getString("CategoryID");
                boolean discontinued = rs.getBoolean("Discontinued");
                double tax = rs.getDouble("Tax");
                String imgPath = rs.getString("Image");

                DAOCategory dc = new DAOCategory();
                Category category = dc.findById(categoryID);

                b = new Beverage(BeverageID, name, modifiedDate, discontinued, purchasePrice, tax, imgPath, category);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return b;
    }

    public Beverage findIdByName(String nameBev) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        Beverage b = null;
        try {
            stm = con.prepareStatement("select [BeverageID], [Name], [PurchasePrice], [ModifiedDate], [CategoryID], [Discontinued], [Tax], [Image] from Beverage where Name = ?");
            stm.setString(1, nameBev);
            rs = stm.executeQuery();

            if (rs.next()) {
                String BeverageID = rs.getString("BeverageID");
                String name = rs.getString("Name");
                double purchasePrice = rs.getDouble("PurchasePrice");
                LocalDateTime modifiedDate = rs.getTimestamp("ModifiedDate").toLocalDateTime();
                String categoryID = rs.getString("CategoryID");
                boolean discontinued = rs.getBoolean("Discontinued");
                double tax = rs.getDouble("Tax");
                String imgPath = rs.getString("Image");

                DAOCategory dc = new DAOCategory();
                Category category = dc.findById(categoryID);
                b = new Beverage(BeverageID, name, modifiedDate, discontinued, purchasePrice, tax, imgPath, category);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return b;
    }

    @Override
    public boolean insert(Beverage t) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = con.prepareStatement("insert into Beverage ([BeverageID], [Name], [PurchasePrice], [ModifiedDate], [CategoryID], [Discontinued], [Tax], [Image]) values (?, ?, ?, ?, ?, ?, ?, ?)");
            stm.setString(1, t.getBeverageID());
            stm.setString(2, t.getName());
            stm.setDouble(3, t.getPurchasePrice());
            Timestamp modifiedDate = Timestamp.valueOf(t.getModifiedDate());
            stm.setTimestamp(4, modifiedDate);
            stm.setString(5, t.getCategory().getCategoryID());
            stm.setBoolean(6, t.isDiscontinued());
            stm.setDouble(7, t.getTax());
            stm.setString(8, t.getImgPath());

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Beverage t) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement("update Beverage "
                    + "set Name = ?, PurchasePrice = ?, ModifiedDate = ?, CategoryID = ?, Discontinued = ?, Tax = ?, Image = ? "
                    + "where BeverageID = ?");
            stm.setString(1, t.getName());
            stm.setDouble(2, t.getPurchasePrice());
            stm.setTimestamp(3, Timestamp.valueOf(t.getModifiedDate()));
            stm.setString(4, t.getCategory().getCategoryID());
            stm.setBoolean(5, t.isDiscontinued());
            stm.setDouble(6, t.getTax());
            stm.setString(7, t.getImgPath());
            stm.setString(8, t.getBeverageID());
//        System.out.println(t.getBeverageID());
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement("delete Beverage where BeverageID = ?");
            stm.setString(1, id);

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Beverage> getListTopBev() {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        List<Beverage> listTopBev = new ArrayList<>();

        String bevID;
        Beverage beverage;
        try {
            stm = con.prepareStatement("select top 5 b.BeverageID ,COUNT(*) as soLuong "
                    + "from [dbo].[Beverage] b "
                    + "	join [dbo].[OrderDetail] od on od.BeverageID = b.BeverageID "
                    + "	join [dbo].[Category] c on c.CategoryID = b.CategoryID "
                    + " and Discontinued = 0 "
                    + " group by b.BeverageID "
                    + " order by soLuong desc");

            rs = stm.executeQuery();
            while (rs.next()) {
                beverage = findById(rs.getString("BeverageID"));
                listTopBev.add(beverage);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listTopBev;
    }

    public List<String[]> getBestSellingBev(int year) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        List<String[]> listOfBev = new ArrayList<>();
        String[] row;

        try {
            stm = con.prepareStatement("select top 5 b.Name, c.Name, sum(od.OrderQty) as BevOrderQty "
                    + "from Beverage b "
                    + "join Category c on c.CategoryID = b.CategoryID "
                    + "join OrderDetail od on od.BeverageID = b.BeverageID "
                    + "join [Order] o on o.OrderID = od.OrderID "
                    + "where YEAR(OrderDate) = ? and Discontinued = 0 "
                    + "group by b.Name, c.Name "
                    + "order by sum(od.OrderQty) desc");
            stm.setInt(1, year);
            rs = stm.executeQuery();

            while (rs.next()) {
                row = new String[3];

                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);

                listOfBev.add(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listOfBev;
    }

    public List<String[]> getBestSellingBev(int year, int month) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        List<String[]> listOfBev = new ArrayList<>();
        String[] row;

        try {
            stm = con.prepareStatement("select top 5 b.Name, c.Name, sum(od.OrderQty) as BevOrderQty "
                    + "from Beverage b "
                    + "join Category c on c.CategoryID = b.CategoryID "
                    + "join OrderDetail od on od.BeverageID = b.BeverageID "
                    + "join [Order] o on o.OrderID = od.OrderID "
                    + "where YEAR(OrderDate) = ? and month(OrderDate) = ? and Discontinued = 0 "
                    + "group by b.Name, c.Name "
                    + "order by sum(od.OrderQty) desc");

            stm.setInt(1, year);
            stm.setInt(2, month);

            rs = stm.executeQuery();

            while (rs.next()) {
                row = new String[3];

                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);

                listOfBev.add(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listOfBev;
    }

    public List<String[]> getBestSellingBev(int year, int month, int day) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        List<String[]> listOfBev = new ArrayList<>();
        String[] row;

        try {
            stm = con.prepareStatement("select top 5 b.Name, c.Name, sum(od.OrderQty) as BevOrderQty "
                    + "from Beverage b "
                    + "join Category c on c.CategoryID = b.CategoryID "
                    + "join OrderDetail od on od.BeverageID = b.BeverageID "
                    + "join [Order] o on o.OrderID = od.OrderID "
                    + "where YEAR(OrderDate) = ? and month(OrderDate) = ?  and day(OrderDate) = ? and Discontinued = 0 "
                    + "group by b.Name, c.Name "
                    + "order by sum(od.OrderQty) desc");
            stm.setInt(1, year);
            stm.setInt(2, month);
            stm.setInt(3, day);
            rs = stm.executeQuery();

            while (rs.next()) {
                row = new String[3];

                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);

                listOfBev.add(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listOfBev;
    }

    //Load data lên PanelBeverage
    public List<Beverage> getListBevByCatName(String nameCat) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        List<Beverage> listBev = new ArrayList<>();
        try {
            String sql = "SELECT * "
                    + "FROM Beverage b "
                    + "JOIN Category c on b.CategoryID = c.CategoryID "
                    + "WHERE c.Name = ? and b.Discontinued = 0 ";
            stm = con.prepareStatement(sql);
            stm.setString(1, nameCat);
            rs = stm.executeQuery();
            while (rs.next()) {
                String BeverageID = rs.getString("BeverageID");
                String name = rs.getString("Name");
                double purchasePrice = rs.getDouble("PurchasePrice");
                LocalDateTime modifiedDate = rs.getTimestamp("ModifiedDate").toLocalDateTime();
                String categoryID = rs.getString("CategoryID");
                boolean discontinued = rs.getBoolean("Discontinued");
                double tax = rs.getDouble("Tax");
                String imgPath = rs.getString("Image");

                DAOCategory dc = new DAOCategory();
                Category category = dc.findById(categoryID);

                listBev.add(new Beverage(BeverageID, name, modifiedDate, discontinued, purchasePrice, tax, imgPath, category));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listBev;
    }

    public List<Beverage> getListBevByKeyWord(String keyWord, String catName) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        List<Beverage> listBev = new ArrayList<>();
        try {
            String sql = "SELECT * "
                    + "FROM Beverage b "
                    + "JOIN Category c on b.CategoryID = c.CategoryID "
                    + "WHERE b.Name like ? and c.Name = ? and Discontinued = 0";
            stm = con.prepareStatement(sql);
            
            stm.setString(1, '%' + keyWord + '%');
            stm.setString(2, catName);
            
            rs = stm.executeQuery();
            while (rs.next()) {
                String BeverageID = rs.getString("BeverageID");
                String name = rs.getString("Name");
                double purchasePrice = rs.getDouble("PurchasePrice");
                LocalDateTime modifiedDate = rs.getTimestamp("ModifiedDate").toLocalDateTime();
                String categoryID = rs.getString("CategoryID");
                boolean discontinued = rs.getBoolean("Discontinued");
                double tax = rs.getDouble("Tax");
                String imgPath = rs.getString("Image");

                DAOCategory dc = new DAOCategory();
                Category category = dc.findById(categoryID);

                listBev.add(new Beverage(BeverageID, name, modifiedDate, discontinued, purchasePrice, tax, imgPath, category));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listBev;
    }
    
    public List<String[]> getBevInfor(int year, int month, int day) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        List<String[]> listOfBev = new ArrayList<>();
        String[] row;
        try {
            stm = con.prepareStatement("select top 5 b.Name, c.Name, sum(od.OrderQty) as BevOrderQty "
                    + "from Beverage b "
                    + "join Category c on c.CategoryID = b.CategoryID "
                    + "join OrderDetail od on od.BeverageID = b.BeverageID "
                    + "join [Order] o on o.OrderID = od.OrderID "
                    + "where YEAR(OrderDate) = ? and month(OrderDate) = ?  and day(OrderDate) = ? "
                    + "group by b.Name, c.Name "
                    + "order by sum(od.OrderQty) desc");
            stm.setInt(1, year);
            stm.setInt(2, month);
            stm.setInt(3, day);
            rs = stm.executeQuery();
            
            while(rs.next()){
                row = new String[3];
                
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);

                listOfBev.add(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return listOfBev;
    }
    
    public List<String[]> getBevInfor(int year, int month) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        List<String[]> listOfBev = new ArrayList<>();
        String[] row;
        try {
            stm = con.prepareStatement("select top 5 b.Name, c.Name, sum(od.OrderQty) as BevOrderQty "
                    + "from Beverage b "
                    + "join Category c on c.CategoryID = b.CategoryID "
                    + "join OrderDetail od on od.BeverageID = b.BeverageID "
                    + "join [Order] o on o.OrderID = od.OrderID "
                    + "where YEAR(OrderDate) = ? and month(OrderDate) = ?"
                    + "group by b.Name, c.Name "
                    + "order by sum(od.OrderQty) desc");
            stm.setInt(1, year);
            stm.setInt(2, month);
            rs = stm.executeQuery();
            
            while(rs.next()){
                row = new String[3];
                
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);

                listOfBev.add(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return listOfBev;
    }
    
    public List<String[]> getBevInfor(int year) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        List<String[]> listOfBev = new ArrayList<>();
        String[] row;
        try {
stm = con.prepareStatement("select top 5 b.Name, c.Name, sum(od.OrderQty) as BevOrderQty "
                    + "from Beverage b "
                    + "join Category c on c.CategoryID = b.CategoryID "
                    + "join OrderDetail od on od.BeverageID = b.BeverageID "
                    + "join [Order] o on o.OrderID = od.OrderID "
                    + "where YEAR(OrderDate) = ?"
                    + "group by b.Name, c.Name "
                    + "order by sum(od.OrderQty) desc");
            stm.setInt(1, year);
            rs = stm.executeQuery();
            
            while(rs.next()){
                row = new String[3];
                
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);

                listOfBev.add(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return listOfBev;
    }
}
