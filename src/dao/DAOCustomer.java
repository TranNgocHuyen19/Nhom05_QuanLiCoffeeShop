
package dao;

import db.ConnectDB;
import entities.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level ;
import java.util.logging.Logger ;


/**
 *
 * @author pc
 */
public class DAOCustomer implements BaseDAO<Customer>{

    private List<Customer> listCustomers;
    Connection con = null;
    PreparedStatement stm = null;
    ResultSet rs = null;

    public DAOCustomer() {
        this.listCustomers = new ArrayList<Customer>();
    }

    @Override
    public List<Customer> findAll() {
        listCustomers = new ArrayList<Customer>();
        con = ConnectDB.getConnection();
        try {
            stm = con.prepareStatement("select * from Customer");
            rs = stm.executeQuery();

            while (rs.next()) {
                String id = rs.getString("CustomerID");
                String name = rs.getString("Name");
                String phoneNumber = rs.getString("PhoneNumber");
                int rewardPoint = rs.getInt("RewardPoint");
                listCustomers.add(new Customer(id, name, phoneNumber, rewardPoint));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listCustomers;
    }

    @Override
    public Customer findById(String id) {
        con = ConnectDB.getConnection();
        Customer c = null;
        try {
            stm = con.prepareStatement("select * from Customer where CustomerID = ?");
            stm.setString(1, id);
            rs = stm.executeQuery();

            if (rs.next()) {
                String Custid = rs.getString("CustomerID");
                String name = rs.getString("Name");
                String phoneNumber = rs.getString("PhoneNumber");
                int rewardPoint = rs.getInt("RewardPoint");
                c = new Customer(Custid, name, phoneNumber, rewardPoint);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return c;
    }

    public Customer findByPhone(String sdt) {
        con = ConnectDB.getConnection();
        Customer c = null;
        try {
            stm = con.prepareStatement("select * from Customer where PhoneNumber = ?");
            stm.setString(1, sdt);
            rs = stm.executeQuery();

            if (rs.next()) {
                String Custid = rs.getString("CustomerID");
                String name = rs.getString("Name");
                String phoneNumber = rs.getString("PhoneNumber");
                int rewardPoint = rs.getInt("RewardPoint");
                c = new Customer(Custid, name, phoneNumber, rewardPoint);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return c;
    }
    @Override
    public boolean insert(Customer t) {
        con = ConnectDB.getConnection();

        try {
            stm = con.prepareStatement("insert into Customer ([CustomerID], [Name], [PhoneNumber]) values (?, ?, ?)");
            stm.setString(1, t.getCustomerID());
            stm.setString(2, t.getName());
            stm.setString(3, t.getPhoneNumber());

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Customer t) {
        con = ConnectDB.getConnection();
        try {
            stm = con.prepareStatement("update Customer "
                    + "set Name = ?, PhoneNumber = ?, RewardPoint = ? "
                    + "where CustomerID = ?");
            stm.setString(1, t.getName());
            stm.setString(2, t.getPhoneNumber());
            stm.setString(4, t.getCustomerID());
            stm.setInt(3, t.getRewardPoint());

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
            stm = con.prepareStatement("delete Customer where CustomerID = ?");
            stm.setString(1, id);

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public int getStartPoint(String id){
        int point = 0;
        double sum = 0;
        con = ConnectDB.getConnection();
        
        try {
            stm = con.prepareStatement("select sum(TotalDue) " +
                    "from [Order] " +
                    "where CustomerID = ? " +
                    "group by CustomerID");
            stm.setString(1, id);
            
            rs = stm.executeQuery();
            
            if(rs.next()){
                sum = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        point = (int) Math.floor(sum / 10000);
        
        return point;
    }
     public List<Customer> getListTopCus() {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String CusID = "";
        String name = "";
        String sdt = "";
        int point;
        List<Customer> listTopCus = new ArrayList<>();
        try {
            stm = con.prepareStatement("select top 5 c.CustomerID, c.Name, c.PhoneNumber, c.RewardPoint, SUM(o.TotalDue) as tongTien \n" +
"                                    from [dbo].[Customer] c \n" +
"                                    	join [dbo].[Order] o on o.CustomerID = c.CustomerID \n "
                    + "where c.CustomerID != 'Cust0000' " +
"                                    group by c.CustomerID, c.Name, c.PhoneNumber, c.RewardPoint\n" +
"                                    order by tongTien desc");   
        rs = stm.executeQuery();
        while(rs.next()) {
            CusID = rs.getString("CustomerID");
            name = rs.getString("Name");
            sdt = rs.getString("PhoneNumber");
            point = rs.getInt("RewardPoint");
            listTopCus.add(new Customer(CusID, name, sdt, point));
            
        }
        } catch (SQLException ex) {
            ex.printStackTrace();
          }
        return listTopCus;
        
        
    }
    
    
}
