package entities;

///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package Entities;
//
//import java.sql.*;
//import DB.ConnectDB;
//import DAO.*;
//import Enums.RoleAccount;
//import Enums.SizeBeverage;
////import Enums.StatusOrder;
//import Enums.TypePayment;
//import java.time.LocalDateTime;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author pc
// */
//public class Main {
//
////    static DAOAccount da = new DAOAccount();
//    static DAOBeverage db = new DAOBeverage();
//    static DAOCustomer dc = new DAOCustomer();
//    static DAOCategory dcate = new DAOCategory();
//    static DAOEmployee de = new DAOEmployee();
//    static DAOOrder dod = new DAOOrder();
//    static DAOOrderdetail dodd = new DAOOrderdetail();
//    static DAOPayment dp = new DAOPayment();
////    static DAOTable dt = new DAOTable();
//
//    public static void main(String[] args) {
//        System.out.println("sdadsa");
//        
////        dod.insert(new Order("desdription", new Payment("P0904240001"), new Table(1), new Customer("Cust1"), new Employee("Emp1")));
////        dp.insert(new Payment(LocalDateTime.now(), "Ngân Hàng"));
//            /*
//            Account a = new Account("username", "password", "Quản Lí");
//            DAOAccount dao = new DAOAccount();
//            dao.delete(a.getUsername());
//            dao.findAll().stream().forEach(x -> System.out.println(x));
//            */
//            /*
//            DAOAccount da = new DAOAccount();
//            Account a = da.findById("HoangHuy");
//            Employee e = new Employee("employeeID", "name", 0, 0, "phoneNumber",
//            "address", "position", a);
//            
//            DAOEmployee de = new DAOEmployee();
//            de.insert(e);
//            de.findAll().stream().forEach(x -> System.out.println(x));
//            e.setSalary(99999);
//            de.update(e);
//            de.delete("employeeID");
//            System.out.println(de.findById("employeeID"));
//            */
//            /*
//            Payment p = new Payment("paymentID", LocalDateTime.now(), "Ngân Hàng");
//            
//            DAOPayment dp = new DAOPayment();
//            dp.insert(p);
//            dp.findAll().stream().forEach(x -> System.out.println(x));
//            p.setType(TypePayment.BY_CASH);
//            dp.update(p);
//            dp.delete(p.getPaymentID());
//            System.out.println(p);
//            System.out.println(dp.findById("paymentID"));
//            dp.findAll().stream().forEach(x -> System.out.println(x));
//            */
//            /*
//            Category c = new Category("categoryID", "name", "desc");
//            
//            DAOCategory dc = new DAOCategory();
//            dc.insert(c);
//            c.setName("name2");
//            dc.update(c);
//            dc.delete("categoryID");
//            dc.findAll().stream().forEach(x -> System.out.println(x));
//            
//            */
//            /*
//            DAOCategory dc = new DAOCategory();
//            Beverage b = new Beverage("beverageID", "name",
//            LocalDateTime.now(), false, 0, 0, (SizeBeverage.EXTRA_LARGE).getSize(), dc.findById("1"));
//            
//            DAOBeverage db = new DAOBeverage();
//            db.insert(b);
//            b.setName("name2");
//            db.update(b);
//            db.delete("beverageID");
//            System.out.println(db.findById("beverageID"));*/
//            db.delete("B042");
//            db.findAll().stream().forEach(x -> System.out.println(x));
//            
//            /*
//            DAOCustomer dc = new DAOCustomer();
//            Customer c = new Customer("CustomerID", "name", "phoneNumber");
//            dc.insert(c);
//            c.setName("name2");
//            dc.update(c);
//            System.out.println(dc.findById("CustomerID"));
//            dc.delete("CustomerID");
//            dc.findAll().stream().forEach(x -> System.out.println(x));
//            */
//            /*
//            DAOTable dt = new DAOTable();
//            Table t = new Table(0, 0, true);
//            dt.insert(t);
//            t.setStatus(false);
//            dt.update(t);
//            dt.delete(t.getTableNumber());
//            System.out.println(dt.findById(t.getTableNumber()));
//            dt.findAll().stream().forEach(x -> System.out.println(x));
//            */
//            /*
//            DAOOrder dod = new DAOOrder();
//            
//            DAOTable dt = new DAOTable();
//            DAOEmployee de = new DAOEmployee();
//            DAOPayment dp = new DAOPayment();
//            DAOCustomer dc = new DAOCustomer();
//            
//            Table table = dt.findById(1);
//            Employee emp = de.findById("1");
//            Payment p = dp.findById("P001");
//            Customer c = dc.findById("C001");
//            
//            Order o = new Order("orderID", LocalDateTime.now(), "desdription", "Chờ Thực Hiện", p, null, table, c, emp);
//            dod.insert(o);
//            o.setStatus(StatusOrder.COMPLETED);
//            dod.update(o);
//            dod.delete(o.getOrderID());
//            System.out.println(dod.findById("1"));
//            dod.findAll().stream().forEach(x -> System.out.println(x));
//            */
//            /*
//            DAOOrderdetail dod = new DAOOrderdetail();
//            DAOBeverage db = new DAOBeverage();
//            System.out.println(db.findById("B001"));
//            Orderdetail od = new Orderdetail("1", db.findById("B001"), 1, LocalDateTime.now(), "description");
//            dod.insert(od);
//            od.setOrderQty(9999);
//            dod.update(od);
//            dod.delete(od.getOrderID(), od.getBeverage().getBeverageID());
//            dod.findAll().stream().forEach(x -> System.out.println(x));
//            */
//            /*
//            DAOPayment dp = new DAOPayment();
//            DAOTable dt = new DAOTable();
//            DAOCustomer dc = new DAOCustomer();
//            DAOEmployee de = new DAOEmployee();
//            System.out.println(new Order("desdription", dp.findById("P001"), dt.findById(1), dc.findById("C001"),
//            de.findById("1")));
//            System.out.println(new Order("desdription", dp.findById("P001"), dt.findById(1), dc.findById("C001"),
//            de.findById("1")));
//            System.out.println(new Order("desdription", dp.findById("P001"), dt.findById(1), dc.findById("C001"),
//            de.findById("1")));
//            System.out.println(new Order("desdription", dp.findById("P001"), dt.findById(1), dc.findById("C001"),
//            de.findById("1")));
//            */
//            
////            de.insert(new Employee("name", "phoneNumber", "address", new Account("hoanghuy")));
//            
//    }
//}
