/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;

/**
 *
 * @author pc
 */
public class PanelChart extends javax.swing.JPanel {
    
    private RevenueChart revenueChart;

    public PanelChart() {
        initComponents();
        Integer[] xParameters = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        Double[] values = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        revenueChart = new RevenueChart(xParameters, values);
        
        // Set layout manager to BorderLayout
        setLayout(new BorderLayout());
        setBackground(new Color(210, 180, 160));
        // Add RevenueChart to the center of the panel
        add(revenueChart, BorderLayout.CENTER);
    }
    
    public PanelChart(Integer[] xParameters, Double[] values) {
        initComponents();
        revenueChart = new RevenueChart(xParameters, values);
        setBackground(new Color(210, 180, 160));
        
        // Set layout manager to BorderLayout
        setLayout(new BorderLayout());
        
        // Add RevenueChart to the center of the panel
        add(revenueChart, BorderLayout.CENTER);
    }

    public RevenueChart getRevenueChart() {
        return revenueChart;
    }

    public void setRevenueChart(RevenueChart revenueChart) {
        this.revenueChart = revenueChart;
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
