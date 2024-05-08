package entities;

import db.ConnectDB;
import enums.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Employee {

    private String employeeID;
    private String name;
    private double salary;
    private ShiftEmployee shift;
    private String phoneNumber;
    private String address;
    private String username;
    private String password;
    private RoleAccount role;

    private static int sequence = 1;

    public Employee(String employeeID) {
        this.employeeID = employeeID;
    }

    public Employee(String name, String phoneNumber, String address, double salary, int shift) {
        this.employeeID = generatID();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.salary = salary;
        setShift(shift);
        this.username = generateUsername();
        this.password = "1";
    }

    public Employee(String employeeID, String name, double salary, int shift, String phoneNumber, String address, String username, String password, String role) {
        this.employeeID = employeeID;
        this.name = name;
        this.salary = salary;
        setShift(shift);
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.username = username;
        this.password = password;

        if (role.equalsIgnoreCase("Quản Lí")) {
            this.role = RoleAccount.MANAGER;
        } else {
            this.role = RoleAccount.EMPLOYEE;
        }
    }

    public Employee(String name, double salary, int shift, String phoneNumber, String address, String role) {
        this.employeeID = generatID();
        this.name = name;
        this.salary = salary;
        setShift(shift);
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.username = generateUsername();
        this.password = "1";

        setRole(role);
    }

    public static String generatID() {
        String newEmpID = null;
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String formattedSequence = null;
        String id = null;

        try {
            stm = con.prepareStatement("select top 1 EmployeeID from Employee order by EmployeeID desc");
            rs = stm.executeQuery();
            if (rs.next()) {
                id = rs.getString("EmployeeID");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (id != null) {
            sequence = Integer.parseInt(id.substring(4)) + 1;
        }
        formattedSequence = String.format("%04d", sequence++);
        newEmpID = "Emp" + formattedSequence;
        return newEmpID;
    }

    public String generateUsername() {
        String count = this.employeeID.substring(employeeID.length() - 2, employeeID.length());
        return "nhanvien" + count;
        //Emp0001
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role.getRole();
    }

    public void setRole(String role) {
        if(role == null) 
            this.role = RoleAccount.EMPLOYEE;

        if(role.equalsIgnoreCase("Quản Lí"))
            this.role = RoleAccount.MANAGER;
        else
            this.role = RoleAccount.EMPLOYEE;
    }
    
    public String getEmployeeID() {
        return employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getShift() {
        return shift.getShift();
    }

    public void setShift(int shift) {
        if(shift == 1)
            this.shift = ShiftEmployee.MORNING;
        else if(shift == 2)
            this.shift = ShiftEmployee.AFTERNOON;
        else
            this.shift = ShiftEmployee.EVENING;
    }

    

    @Override
    public String toString() {
        return "Employee{" + "employeeID=" + employeeID + ", name=" + name + ", salary=" + salary + ", shift=" + shift + ", phoneNumber=" + phoneNumber + ", address=" + address + ", username=" + username + ", password=" + password + ", role=" + role.getRole() + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.employeeID);
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
        final Employee other = (Employee) obj;
        return Objects.equals(this.employeeID, other.employeeID);
    }

}
