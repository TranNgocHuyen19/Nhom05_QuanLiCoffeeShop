package entities;

import db.ConnectDB;
import enums.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Payment {

    private String paymentID;
    private LocalDateTime paymentDate;
    private TypePayment type;

    private static LocalDate currentDate = LocalDate.now();
    private static int sequence = 1;

    public Payment(LocalDateTime paymentDate, String type) {
        this.paymentID = generatID();
        this.paymentDate = paymentDate;
        if (type.equalsIgnoreCase("Ngân Hàng")) {
            this.type = TypePayment.CREDIT_CARD;
        } else if (type.equalsIgnoreCase("Tiền Mặt")) {
            this.type = TypePayment.CASH;
        } else {
            this.type = TypePayment.E_WALLET;
        }
    }

    public Payment(String paymentID) {
        this.paymentID = paymentID;
    }

    public Payment(String paymentID, LocalDateTime paymentDate, String type) {
        this.paymentID = paymentID;
        this.paymentDate = paymentDate;
        if (type.equalsIgnoreCase("Ngân Hàng")) {
            this.type = TypePayment.CREDIT_CARD;
        } else if (type.equalsIgnoreCase("Tiền Mặt")) {
            this.type = TypePayment.CASH;
        } else {
            this.type = TypePayment.E_WALLET;
        }
    }

    public static String generatID() {
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("ddMMyy"));
        String newPaymentID = null;
        String formattedSequence = null;
        Connection con = ConnectDB.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String id = null;
        try {
            stm = con.prepareStatement("select top 1 PaymentID \n"
                    + "from [Payment] \n"
                    + "where year(PaymentDate) = year(getdate()) and month(PaymentDate) = month(getdate()) and day(PaymentDate) = day(getdate())  \n"
                    + "order by PaymentID desc");
            rs = stm.executeQuery();
            if (rs.next()) {
                id = rs.getString("PaymentID");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (id != null) {
            String paymentDate = id.substring(1, 7);
            if (!paymentDate.equals(formattedDate)) {
                formattedSequence = String.format("%04d", sequence++);
            } else {
                sequence = Integer.parseInt(id.substring(7));
                formattedSequence = String.format("%04d", ++sequence);
            }
        } else {
            formattedSequence = String.format("%04d", sequence++);
        }
        newPaymentID = "P" + formattedDate + formattedSequence;
        return newPaymentID;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getType() {
        return type.getType();
    }

    public void setType(TypePayment type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Payment{" + "paymentID=" + paymentID + ", paymentDate=" + paymentDate + ", type=" + type + '}';
    }
}
