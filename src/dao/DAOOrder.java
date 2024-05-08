/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.ConnectDB;
import entities.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pc
 */
public class DAOOrder implements BaseDAO<Order> {

    private DAOOrderdetail dod = new DAOOrderdetail();
    private DAOCustomer dc = new DAOCustomer();
    private DAOEmployee de = new DAOEmployee();

    private List<Order> listOrders;
    Connection con = null;
    PreparedStatement stm = null;
    ResultSet rs = null;

    public DAOOrder() {
        this.listOrders = new ArrayList<>();
    }

    @Override
    public List<Order> findAll() {
        listOrders = new ArrayList<>();
        List<Order> listOrders = new ArrayList<>();

        try {
            Connection con = ConnectDB.getConnection();
            PreparedStatement stm = con.prepareStatement("SELECT [OrderID], [OrderDate], [Status],[EmpID],[PaymentID],[CustomerID], [Discount] FROM [Order]");
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String id = rs.getString("OrderID");
                LocalDateTime date = rs.getTimestamp("OrderDate").toLocalDateTime();

                DAOEmployee de = new DAOEmployee();
                DAOPayment dp = new DAOPayment();
                DAOCustomer dc = new DAOCustomer();

                Employee emp = de.findById(rs.getString("EmpID"));
                Payment p = dp.findById(rs.getString("PaymentID"));
                Customer c = dc.findById(rs.getString("CustomerID"));
                double discount = rs.getDouble("Discount");

                List<Orderdetail> listOfOD = new ArrayList<>();
                try {
                    PreparedStatement stm2 = con.prepareStatement("SELECT * FROM OrderDetail WHERE OrderID = ?");
                    stm2.setString(1, id);
                    ResultSet rs2 = stm2.executeQuery();
                    while (rs2.next()) {
                        String beverageID = rs2.getString("BeverageID");
                        int orderQty = rs2.getInt("OrderQty");
                        String description = rs2.getString("Description");

                        DAOBeverage db = new DAOBeverage();
                        Orderdetail orderDetail = new Orderdetail(db.findById(beverageID),
                                orderQty, description);
                        listOfOD.add(orderDetail);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DAOOrder.class.getName()).log(Level.SEVERE, null, ex);
                }

                listOrders.add(new Order(id, date, p, listOfOD, c, emp, discount));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listOrders;
    }

    @Override
    public Order findById(String id) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        Order o = null;
        try {
            stm = con.prepareStatement("select [OrderID], [OrderDate],[EmpID],[PaymentID],[CustomerID], [Discount] from [Order] WHERE OrderID = ?");
            stm.setString(1, id);
            rs = stm.executeQuery();
            if (rs.next()) {
                String orderID = rs.getString("OrderID");
                LocalDateTime date = rs.getTimestamp("OrderDate").toLocalDateTime();
                DAOEmployee de = new DAOEmployee();
                DAOPayment dp = new DAOPayment();
                DAOCustomer dc = new DAOCustomer();

                Employee emp = de.findById(rs.getString("EmpID"));
                Payment p = dp.findById(rs.getString("PaymentID"));
                Customer c = dc.findById(rs.getString("CustomerID"));
                double discount = rs.getDouble("Discount");

                List<Orderdetail> listOfOD = new ArrayList<Orderdetail>();
                try {
                    PreparedStatement stm2 = con.prepareStatement("select * from OrderDetail "
                            + "where OrderID = ?");
                    stm2.setString(1, orderID);
                    ResultSet rs2 = stm2.executeQuery();
                    while (rs2.next()) {
                        String beverageID = rs2.getString("BeverageID");
                        int orderQty = rs2.getInt("OrderQty");
                        String description = rs2.getString("Description");

                        DAOBeverage db = new DAOBeverage();
                        Orderdetail orderDetail = new Orderdetail(db.findById(beverageID),
                                orderQty, description);
                        listOfOD.add(orderDetail);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DAOOrder.class.getName()).log(Level.SEVERE, null, ex);
                }

                o = new Order(id, date, p, listOfOD, c, emp, discount);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return o;
    }

    @Override
    public boolean insert(Order t) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = ConnectDB.getConnection();

            try {
                stm = con.prepareStatement("insert into [Order]([OrderID], [OrderDate], [EmpID],[PaymentID],[CustomerID],[TotalDue], [Discount]) values (?, ?, ?, ?, ?, ?, ?)");
                stm.setString(1, t.getOrderID());
                stm.setTimestamp(2, Timestamp.valueOf(t.getOrderDate()));
                stm.setString(4, t.getPayment().getPaymentID());

                stm.setString(3, t.getEmp().getEmployeeID());
                stm.setString(5, t.getCust().getCustomerID());
                double totalPrice = t.getTotalDue();
                System.out.println(totalPrice);
                stm.setDouble(6, totalPrice);
                stm.setDouble(7, t.getDiscount());

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            stm.executeUpdate();

            List<Orderdetail> ods = t.getListOfODs();
            for (Orderdetail od : ods) {
                dod.insert(od, t.getOrderID());
            }

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DAOOrder.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean insertWithoutPayment(Order t) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = con.prepareStatement("insert into [Order] ([OrderID], [OrderDate], [EmpID], [CustomerID], [TotalDue], [Discount]) values (?, ?, ?, ?, ?, ?)");
            stm.setString(1, t.getOrderID());
            stm.setTimestamp(2, Timestamp.valueOf(t.getOrderDate()));
            stm.setString(3, t.getEmp().getEmployeeID());
            stm.setString(4, t.getCust().getCustomerID());
            stm.setDouble(5, t.getTotalDue() - t.getDiscount());
            stm.setDouble(6, t.getDiscount());
            stm.executeUpdate();

            List<Orderdetail> ods = t.getListOfODs();
            for (Orderdetail od : ods) {
                dod.insert(od, t.getOrderID());
            }
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Order t) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement("update [Order] "
                    + "set OrderDate = ?,  EmpID = ?, "
                    + "PaymentID = ?, CustomerID = ?, TotalDue = ?, Discount = ?"
                    + "where OrderID = ?");
            stm.setString(7, t.getOrderID());
            stm.setTimestamp(1, Timestamp.valueOf(t.getOrderDate()));
            stm.setString(2, t.getEmp().getEmployeeID());
            stm.setString(3, t.getPayment().getPaymentID());
            stm.setString(4, t.getCust().getCustomerID());
            stm.setDouble(5, t.getTotalDue() - t.getDiscount());
            stm.setDouble(6, t.getDiscount());

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
            stm = con.prepareStatement("delete [Order] where OrderID = ?");
            stm.setString(1, id);

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Map<Integer, Double> getYearlyOrderSums() {

        Map<Integer, Double> yearSumMap = new HashMap<>();

        try {
            Connection con = ConnectDB.getConnection(); // Thiết lập kết nối cơ sở dữ liệu
            PreparedStatement stm = con.prepareStatement("SELECT YEAR(OrderDate) AS Year, SUM(TotalDue) AS SumOfTotalDue "
                    + "FROM [Order] "
                    + "GROUP BY YEAR(OrderDate) "
                    + "ORDER BY YEAR(OrderDate) DESC"); // Truy vấn để lấy năm và tổng số tiền của mỗi năm

            ResultSet rs = stm.executeQuery(); // Thực hiện truy vấn

            // Duyệt qua kết quả và thêm vào bản đồ
            while (rs.next()) {
                Integer year = rs.getInt("Year");
                Double sumOfTotalDue = rs.getDouble("SumOfTotalDue");
                yearSumMap.put(year, sumOfTotalDue);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return yearSumMap; // Trả về bản đồ chứa năm và tổng số tiền
    }

    public Map<Integer, Double> getMonthlyOrderSums(int year) {
        Map<Integer, Double> monthSumMap = new HashMap<>();

        try {
            Connection con = ConnectDB.getConnection(); // Thiết lập kết nối cơ sở dữ liệu
            PreparedStatement stm = con.prepareStatement("select month(OrderDate) as Month, sum(TotalDue) as SumOfTotalDue "
                    + "from [Order] "
                    + "where year(OrderDate) = ? "
                    + "group by month(OrderDate)"); // Truy vấn để lấy năm và tổng số tiền của mỗi năm

            stm.setInt(1, year);
            ResultSet rs = stm.executeQuery(); // Thực hiện truy vấn

            // Duyệt qua kết quả và thêm vào bản đồ
            while (rs.next()) {
                Integer month = rs.getInt("Month");
                Double sumOfTotalDue = rs.getDouble("SumOfTotalDue");
                monthSumMap.put(month, sumOfTotalDue);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return monthSumMap; // Trả về bản đồ chứa năm và tổng số tiền
    }

    public String getYearPeriod() {
        String yearPeriod = "";
        try (Connection con = ConnectDB.getConnection()) { // Use try-with-resources for auto-closing
            PreparedStatement stm = con.prepareStatement("SELECT DISTINCT YEAR(OrderDate) FROM [Order] ORDER BY YEAR(OrderDate)");

            ResultSet rs = stm.executeQuery();

            if (rs.next()) { // Ensure there's at least one result
                int firstYear = rs.getInt(1); // Get the first year
                int lastYear = firstYear; // Initialize last year with the first year

                while (rs.next()) { // Loop through the results to get the last year
                    lastYear = rs.getInt(1);
                }

                yearPeriod = firstYear + " - " + lastYear;
            } else {
                yearPeriod = "No orders found"; // If there's no data
            }

        } catch (SQLException ex) {
            ex.printStackTrace(); // Logging the exception
            yearPeriod = "Error fetching year period"; // Fallback value on error
        }

        return yearPeriod;
    }

    public List<String> getAllYear() {
        List<String> years = new ArrayList<>();

        try {
            Connection con = ConnectDB.getConnection(); // Thiết lập kết nối cơ sở dữ liệu
            PreparedStatement stm = con.prepareStatement("SELECT YEAR(OrderDate) AS Year "
                    + "FROM [Order] "
                    + "GROUP BY YEAR(OrderDate) "); // Truy vấn để lấy năm và tổng số tiền của mỗi năm

            ResultSet rs = stm.executeQuery(); // Thực hiện truy vấn

            // Duyệt qua kết quả và thêm vào bản đồ
            while (rs.next()) {
                String year = rs.getString("Year");
                years.add(year);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return years;
    }

    public double getYearRevenue(int year) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        double revenue = 0;
        try {
            stm = con.prepareStatement("select sum(TotalDue) as SumOfTotalDue from [Order] where year(OrderDate) = ?");
            stm.setInt(1, year);
            rs = stm.executeQuery();

            if (rs.next()) {
                revenue = rs.getDouble("SumOfTotalDue");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return revenue;
    }

    public double getMonthRevenue(int year, int month) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        double revenue = 0;
        try {
            stm = con.prepareStatement("select sum(TotalDue) as SumOfTotalDue from [Order] where year(OrderDate) = ? and month(OrderDate) = ?");
            stm.setInt(1, year);
            stm.setInt(2, month);

            rs = stm.executeQuery();
            if (rs.next()) {
                revenue = rs.getDouble("SumOfTotalDue");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return revenue;
    }

    public double getDayRevenue(int year, int month, int day) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        double revenue = 0;
        try {
            stm = con.prepareStatement("select sum(TotalDue) as SumOfTotalDue from [Order] where year(OrderDate) = ? and month(OrderDate) = ? and day(OrderDate) = ?");
            stm.setInt(1, year);
            stm.setInt(2, month);
            stm.setInt(3, day);
            rs = stm.executeQuery();
            if (rs.next()) {
                revenue = rs.getDouble("SumOfTotalDue");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return revenue;
    }

    public int getYearSoLuong(int year) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        int soLuong = 0;
        try {
            stm = con.prepareStatement("select sum(od.OrderQty) as SoLuong "
                    + "from [Order] o "
                    + "join OrderDetail od on od.OrderID = o.OrderID "
                    + "where year(OrderDate) = ?");

            stm.setInt(1, year);
            rs = stm.executeQuery();

            if (rs.next()) {
                soLuong = rs.getInt("SoLuong");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return soLuong;
    }

    public int getMonthSoLuong(int year, int month) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        int soLuong = 0;
        try {
            stm = con.prepareStatement("select sum(od.OrderQty) as SoLuong "
                    + "from [Order] o "
                    + "join OrderDetail od on od.OrderID = o.OrderID "
                    + "where year(OrderDate) = ? and month(OrderDate) = ?");

            stm.setInt(1, year);
            stm.setInt(2, month);
            rs = stm.executeQuery();

            if (rs.next()) {
                soLuong = rs.getInt("SoLuong");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return soLuong;
    }

    public int getDaySoLuong(int year, int month, int day) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        int soLuong = 0;
        try {
            stm = con.prepareStatement("select sum(od.OrderQty) as SoLuong "
                    + "from [Order] o "
                    + "join OrderDetail od on od.OrderID = o.OrderID "
                    + "where year(OrderDate) = ? and month(OrderDate) = ? and day(OrderDate) = ?");

            stm.setInt(1, year);
            stm.setInt(2, month);
            stm.setInt(3, day);
            rs = stm.executeQuery();

            if (rs.next()) {
                soLuong = rs.getInt("SoLuong");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return soLuong;
    }

    public List<String[]> getListInforOfOrder() {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        List<String[]> listInforOfOrder = new ArrayList<>();
        String[] row;

        try {
            String sql = "SELECT e.Name, o.TotalDue, DATEDIFF(MINUTE, o.OrderDate, GETDATE()) AS MinutesSinceOrder, o.OrderID "
                    + "FROM [Order] o"
                    + "	JOIN Employee e ON o.EmpID = e.EmployeeID ";

            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                row = new String[4];
                
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);

                listInforOfOrder.add(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listInforOfOrder;
    }

    public List<Order> getListOrderInTimePeriod(Date startDate, Date endDate) {
        List<Order> listOrder = new ArrayList<>();
        try {
            Connection con = ConnectDB.getConnection();
            PreparedStatement stm1 = con.prepareStatement("select OrderID, OrderDate, EmpID, CustomerID, Discount "
                    + "from [Order] "
                    + "where OrderDate between ? and ? "
                    + "order by OrderDate desc");

            Calendar startCal = Calendar.getInstance();

            startCal.setTime(startDate);
            startCal.add(Calendar.DATE, -1);
            
            Timestamp st1 = new Timestamp(startCal.getTime().getTime());
            
            st1.setHours(23);
            st1.setMinutes(59);
            st1.setSeconds(59);

            Timestamp st2 = new Timestamp(endDate.getTime());
            
            st2.setHours(23);
            st2.setMinutes(59);
            st2.setSeconds(59);

            stm1.setTimestamp(1, st1);
            stm1.setTimestamp(2, st2);
            
//            System.out.println(st1);
//            System.out.println(st2);
            ResultSet rs = stm1.executeQuery();

            while (rs.next()) {
                String orderID = rs.getString(1);
                LocalDateTime orderDate = rs.getTimestamp(2).toLocalDateTime();
                String empID = rs.getString(3);
                String cusID = rs.getString(4);
                double discount = rs.getDouble(5);
                Order o = new Order(orderID, orderDate, null, findById(orderID).getListOfODs(), dc.findById(cusID), de.findById(empID), discount);
//                System.out.println(o);
                listOrder.add(o);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listOrder;
    }

}
