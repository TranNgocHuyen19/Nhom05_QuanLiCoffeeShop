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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pc
 */
public class DAOEmployee implements BaseDAO<Employee> {

    private List<Employee> employees;
    Connection con = null;
    PreparedStatement stm = null;
    ResultSet rs = null;

    public DAOEmployee() {
        this.employees = new ArrayList<>();
    }

    @Override
    public List<Employee> findAll() {
        employees = new ArrayList<>();
        con = ConnectDB.getConnection();
        try {
            stm = con.prepareStatement("select [EmployeeID], [Name], [Salary], [Shift], [PhoneNumber], [Address], [Username], [Password], [Role] from Employee ");
            rs = stm.executeQuery();

            while (rs.next()) {
                String empID = rs.getString("EmployeeID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");
                int shift = rs.getInt("Shift");
                String phoneNumber = rs.getString("PhoneNumber");
                String address = rs.getString("Address");
                String uname = rs.getString("Username");
                String pwd = rs.getString("Password");
                String role = rs.getString("Role");

                employees.add(new Employee(empID, name, salary, shift, phoneNumber, address, uname, pwd, role));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return employees;
    }

    @Override
    public Employee findById(String id) {
        con = ConnectDB.getConnection();
        Employee emp = null;
        try {
            stm = con.prepareStatement("select [EmployeeID], [Name], [Salary], [Shift], [PhoneNumber], [Address], [Username], [Password], [Role] from Employee where EmployeeID = ?");
            stm.setString(1, id);
            rs = stm.executeQuery();

            if (rs.next()) {
                String empID = rs.getString("EmployeeID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");
                int shift = rs.getInt("Shift");
                String phoneNumber = rs.getString("PhoneNumber");
                String address = rs.getString("Address");
                String uname = rs.getString("Username");
                String pwd = rs.getString("Password");
                String role = rs.getString("Role");

                emp = new Employee(empID, name, salary, shift, phoneNumber, address, uname, pwd, role);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return emp;
    }
    
    public Employee findPasswordByUsername(String username, String password) {
        con = ConnectDB.getConnection();
        Employee emp = null;
        try {
            stm = con.prepareStatement("select [EmployeeID], [Name], [Salary], "
                    + "[Shift], [PhoneNumber], [Address], [Username], [Password], [Role]"
                    + " from Employee"
                    + " where Username = ? and Password = ?");
            stm.setString(1, username);
            stm.setString(2, password);
            rs = stm.executeQuery();
            if (rs.next()) {
                String empID = rs.getString("EmployeeID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");
                int shift = rs.getInt("Shift");
                String phoneNumber = rs.getString("PhoneNumber");
                String address = rs.getString("Address");
                String uname = rs.getString("Username");
                String pwd = rs.getString("Password");
                String role = rs.getString("Role");

                emp = new Employee(empID, name, salary, shift, phoneNumber, address, uname, pwd, role);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return emp;
    }

    @Override
    public boolean insert(Employee t) {
        con = ConnectDB.getConnection();

        try {
            stm = con.prepareStatement("INSERT INTO Employee ([EmployeeID], [Name], [Salary], [Shift], [PhoneNumber], [Address], [Username], [Password], [Role])\n"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
            stm.setString(1, t.getEmployeeID());
            stm.setString(2, t.getName());
            stm.setString(3, t.getSalary() + "");
            stm.setString(4, t.getShift() + "");
            stm.setString(5, t.getPhoneNumber());
            stm.setString(6, t.getAddress());
            stm.setString(7, t.getUsername());
            stm.setString(8, t.getPassword());
            stm.setString(9, t.getRole());

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Employee t) {
        con = ConnectDB.getConnection();
        try {
            stm = con.prepareStatement("update Employee set Name = ?, "
                    + "Salary = ?, "
                    + "Shift = ?, "
                    + "PhoneNumber = ?, "
                    + "Address = ?, "
                    + "Password = ?, "
                    + "Role = ? "
                    + "where EmployeeID = ?;");
            stm.setString(1, t.getName());
            stm.setDouble(2, t.getSalary());
            stm.setString(3, t.getShift());
            stm.setString(4, t.getPhoneNumber());
            stm.setString(5, t.getAddress());
            stm.setString(6, t.getPassword());
            stm.setString(7, t.getRole());
            stm.setString(8, t.getEmployeeID());

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
            stm = con.prepareStatement("delete Employee where EmployeeID = ?");
            stm.setString(1, id);

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public List<Employee> getListTopEmp() {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        List<Employee> listTopEmp = new ArrayList<>();

        String empID;
        Employee emp;
        try {
            stm = con.prepareStatement("SELECT TOP 5 e.EmployeeID "
                    + "FROM Employee e "
                    + "JOIN [Order] o ON e.EmployeeID = o.EmpID "
                    + "WHERE e.EmployeeID != 'Emp0000' "
                    + "GROUP BY e.EmployeeID, e.Name, e.Shift,e.Salary,e.PhoneNumber,e.Address, e.Role "
                    + "ORDER BY SUM(TotalDue)  DESC; ");

            rs = stm.executeQuery();
            while (rs.next()) {
                emp = findById(rs.getString("EmployeeID"));
                listTopEmp.add(emp);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listTopEmp;
    }
}
