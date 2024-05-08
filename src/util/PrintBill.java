/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import dao.*;
import entities.*;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Trần Ngọc Huyền
 */
public class PrintBill {

    private DAOCustomer dc = new DAOCustomer();
    public static int finalPoint;

    public PrintBill() {
    }

    public PrinterJob getPrinterJob(Order o) {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        int startPoint = o.getCust().getRewardPoint();
        int plusPoint = (int) o.getTotalDue() / 10000;

        int totalPoint = startPoint + plusPoint;

                int x = (int) (o.getDiscount() * 100 / (o.getTotalDue() + o.getDiscount()));
                
                int usedPoint;
                
                if(x == 5)
                    usedPoint = 300;
                else if(x == 10)
                    usedPoint = 600;
                else if(x == 15)
                    usedPoint = 900;
                else 
                    usedPoint = 0;
                
        finalPoint = totalPoint - usedPoint;

        String[] custInfor2 = {
            startPoint + "",
            plusPoint + "",
            usedPoint + "",
            finalPoint + ""
        };
        printerJob.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                // Thiết lập font chữ cho các dòng thông tin trên cùng của hóa đơn
                Font bolderFont = new Font("Monospaced", Font.BOLD, 12);
                Font boldFont = new Font("Monospaced", Font.BOLD, 10);
                Font normalFont = new Font("Monospaced", Font.PLAIN, 10);

                Font baseFont10 = new Font("Monospaced", Font.PLAIN, 10);
                Font boldItalicFont10 = baseFont10.deriveFont(Font.BOLD | Font.ITALIC);

                Font baseFont8 = new Font("Monospaced", Font.PLAIN, 8);
                Font boldItalicFont8 = baseFont8.deriveFont(Font.BOLD | Font.ITALIC);

                int contentY = 15;
                // Thông tin quán
                contentY += 20;
                String[] headerLines = {
                    "109 Lê Lợi, Phường 4, Quận Gò Vấp",
                    "0364635032",
                    "HÓA ĐƠN THANH TOÁN",
                    "-----------------------------------"
                };
                String lineBreak = "---------------------------------------------------------------";
                g2d.setFont(bolderFont);
                String s = "";
                for (String line : headerLines) {
                    g2d.drawString(line, (int) (pageFormat.getImageableWidth() / 2 - g2d.getFontMetrics().stringWidth(line) / 2), contentY);
                    contentY += g2d.getFontMetrics().getHeight();
                }

                //Thông tin nhân viên và khách
                String[] detail1 = {
                    "Nhân Viên: ",
                    "Ngày: ",
                    "Giờ Vào: ",
                    "Giờ Ra: "
                };

                String[] detail2 = {
                    o.getEmp().getName(),
                    o.getOrderDate().getDayOfMonth() + "-" + o.getOrderDate().getMonthValue() + "-" + o.getOrderDate().getYear(),
                    o.getOrderDate().getHour() + ":" + o.getOrderDate().getMinute() + ":" + o.getOrderDate().getSecond(),
                    LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ":" + LocalDateTime.now().getSecond()
                };

                contentY += 10;
                int cnt = 0;
                g2d.setFont(normalFont);
                for (String line : detail1) {
                    g2d.setFont(boldFont);
                    g2d.drawString(String.format("%15s%-12s", " ", line), 15, contentY);
                    g2d.setFont(normalFont);
                    g2d.drawString(String.format("%27s%-50s", " ", detail2[cnt]), 15, contentY);
                    contentY += g2d.getFontMetrics().getHeight();
                    cnt++;
                }

                //Danh sách sản phẩm
                contentY += 10;
                s = (String.format("%-4s|%-18s|%-4s|%-15s|%-15s\n", "STT", "Tên", "SL", "Đơn Giá", "Thành Tiền"));
                g2d.drawString(s, (int) (pageFormat.getImageableWidth() / 2 - g2d.getFontMetrics().stringWidth(s) / 2), contentY);
                contentY += g2d.getFontMetrics().getHeight();

                List<Orderdetail> ods = o.getListOfODs();
                for (int i = 0; i < o.getListOfODs().size(); i++) {
                    Orderdetail od = ods.get(i);
                    s = (String.format("%-4s|%-18s|%-4s|%-15s|%-15s\n",
                            i + 1,
                            od.getBeverage().getName(),
                            od.getOrderQty(),
                            od.getBeverage().getSellingPrice(),
                            od.getTotalPrice()
                    ));
                    g2d.drawString(s, (int) (pageFormat.getImageableWidth() / 2 - g2d.getFontMetrics().stringWidth(s) / 2), contentY);
                    contentY += g2d.getFontMetrics().getHeight();
                }

                g2d.drawString(lineBreak, (int) (pageFormat.getImageableWidth() / 2 - g2d.getFontMetrics().stringWidth(lineBreak) / 2), contentY);
                contentY += g2d.getFontMetrics().getHeight();
                s = (String.format("%-51s", "Tổng tiền: ")) + (String.format("%-15s", o.getTotalDue())) + ("\n");
                String d = (String.format("%-51s", "Giảm giá: ")) + (String.format("%-15s", o.getDiscount())) + ("\n");
                g2d.setFont(boldFont);

                if (!(Math.abs(o.getDiscount() - 0) < 0.1)) {
                    g2d.drawString(d, (int) (pageFormat.getImageableWidth() / 2 - g2d.getFontMetrics().stringWidth(d) / 2), contentY);
                    contentY += g2d.getFontMetrics().getHeight();
                }
                g2d.drawString(s, (int) (pageFormat.getImageableWidth() / 2 - g2d.getFontMetrics().stringWidth(s) / 2), contentY);
                contentY += g2d.getFontMetrics().getHeight();
                g2d.setFont(normalFont);
                contentY += 15;

                //Cám ơn khách và hẹn gặp lại
                String thanks = "Cám ơn Quý Khách và hẹn gặp lại";
                String[] pointInfor = {
                    "Chào mừng quý khách đã trở thành hội",
                    "viên và có thể tích điểm trên từng hóa đơn!",
                    "Cứ 10,000 đồng => 1 điểm",
                    "Đủ 300 điểm => chiết khấu 5%",
                    "Đủ 600 điểm => chiết khấu 10%",
                    "Đủ 900 điểm => chiết khấu 15%"
                };

                g2d.setFont(boldItalicFont10);
                g2d.drawString(thanks, (int) (pageFormat.getImageableWidth() / 2 - g2d.getFontMetrics().stringWidth(thanks) / 2), contentY);
                contentY += g2d.getFontMetrics().getHeight();
                g2d.setFont(boldItalicFont8);

                contentY += 10;
                for (String line : pointInfor) {
                    g2d.drawString(line, (int) (pageFormat.getImageableWidth() / 2 - g2d.getFontMetrics().stringWidth(line) / 2), contentY);
                    contentY += g2d.getFontMetrics().getHeight();
                }

                contentY += 20;
                g2d.setFont(boldFont);

                if (!o.getCust().getCustomerID().equals("Cust0000")) {
                    String custName = o.getCust().getName();
                    String custPhone = o.getCust().getPhoneNumber();
                    s = (String.format("%-48s", custName)) + (String.format("%-15s", " ")) + ("\n");
                    g2d.drawString(s, (int) (pageFormat.getImageableWidth() / 2 - g2d.getFontMetrics().stringWidth(s) / 2), contentY);
                    contentY += g2d.getFontMetrics().getHeight();
                    s = (String.format("%-48s", custPhone)) + (String.format("%-15s", " ")) + ("\n");
                    g2d.drawString(s, (int) (pageFormat.getImageableWidth() / 2 - g2d.getFontMetrics().stringWidth(s) / 2), contentY);
                    contentY += g2d.getFontMetrics().getHeight();

                    if (usedPoint == -1) {
                        return Printable.PAGE_EXISTS;
                    }
                    //Thông tin điểm cộng của khách hàng
                    g2d.setFont(normalFont);
                    String[] custInfor1 = {
                        "Điểm đầu",
                        "Điểm cộng",
                        "Điểm trừ",
                        "Điểm cuối"
                    };

                    String[] custInfor2 = {
                        startPoint + "",
                        plusPoint + "",
                        usedPoint + "",
                        finalPoint + ""
                    };

                    contentY += 5;
                    int cnt2 = 0;
                    for (String line : custInfor1) {
                        s = (String.format("%-55s", line)) + (String.format("%8s", custInfor2[cnt2])) + ("\n");
                        g2d.drawString(s, (int) (pageFormat.getImageableWidth() / 2 - g2d.getFontMetrics().stringWidth(s) / 2), contentY);
                        contentY += g2d.getFontMetrics().getHeight();
                        cnt2++;
                    }

                }
                return Printable.PAGE_EXISTS;
            }
        });

        return printerJob;
    }
}
