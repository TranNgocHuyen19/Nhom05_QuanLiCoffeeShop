/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import gui.custom.CustomRectangularButton;
import dao.*;
import entities.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author pc
 */
public class FrameInvoice extends javax.swing.JDialog implements ActionListener {

    private JButton btnPrint;
    private JButton btnCancel;
//    private Container app;
    private PrinterJob printerJob;
    private DAOOrder dod = new DAOOrder();
    private Order o;
    private boolean flag;

    public FrameInvoice(Order o, PrinterJob printerJob, boolean flag) {
//        this.app = app;
        this.printerJob = printerJob;
        this.o = o;
        this.flag = flag;

        setTitle("Hóa Đơn");

        if (o.getCust().getCustomerID().equals("Cust0000")) {
            setSize(650, 600);
        } else {
            setSize(650, 700);
        }
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PageFormat pageFormat = new PageFormat(); // Đặt pageFormat phù hợp

        InvoicePrinter customPanel = new InvoicePrinter(pageFormat, o, flag);
//        customPanel.setBackground(Color.BLUE);
        add(customPanel, BorderLayout.CENTER);

        JPanel p = new JPanel();
        btnPrint = new CustomRectangularButton(new Color[]{Color.WHITE, new Color(177, 126, 97), new Color(177, 126, 97)});
        btnPrint.setText("In Hóa Đơn");
        btnCancel = new CustomRectangularButton(new Color[]{Color.WHITE, new Color(111, 69, 24), new Color(111, 69, 24)});
        btnCancel.setText("Hủy Bỏ");

        p.add(btnPrint);
        p.add(btnCancel);
        p.setBackground(Color.WHITE);
        add(p, BorderLayout.SOUTH);
        setVisible(true);

        btnPrint.addActionListener(this);
        btnCancel.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object ob = e.getSource();

        dod.update(o);

        if (ob == btnPrint) {
            try {
                this.printerJob.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        } else if (ob == btnCancel) {

        }
        this.setVisible(false);
    }
}
