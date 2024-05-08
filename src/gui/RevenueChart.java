/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gui;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class RevenueChart extends JPanel {

    private DecimalFormat df = new DecimalFormat("###,##0");
    private Double[] values;
    private Integer[] xParameters;
    private int animationIndex;
    private Timer timer;

    private String chartTitle = "BIỂU ĐỒ DOANH THU";
    private String xName = "Năm";

    public RevenueChart(Integer[] xParameters, Double[] values) {
        setBackground(new Color(210, 180, 160));
        this.xParameters = xParameters;
        this.values = values;
        animationIndex = 0;
        timer = new Timer(50, e -> {
            if (animationIndex < 20) {
                animationIndex++;
                repaint();
            } else {
                timer.stop();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight() - 40;

        // Vẽ trục x và trục y
        g2d.drawLine(130, height - 50, width - 50, height - 50);  // trục x
        g2d.drawLine(130, height - 50, 130, 50);  // trục y

        // Vẽ mũi tên ở cuối trục x
        int arrowSize = 5;  // kích thước mũi tên nhỏ hơn
        int[] xArrowX = {width - 40, width - 50, width - 50};  // tọa độ x của mũi tên trục x
        int[] xArrowY = {height - 50, height - 55, height - 45};  // tọa độ y của mũi tên trục x
        g2d.fillPolygon(xArrowX, xArrowY, 3);  // vẽ mũi tên tam giác

        // Vẽ mũi tên ở cuối trục y
        int[] yArrowX = {130, 125, 135};  // tọa độ x của mũi tên trục y
        int[] yArrowY = {50, 60, 60};  // tọa độ y của mũi tên trục y
        g2d.fillPolygon(yArrowX, yArrowY, 3);  // vẽ mũi tên tam giác

        // Tính chiều rộng của mỗi cột
        int barWidth = (xParameters.length > 0) ? (width - 100) / 16 : 0;

        // Tìm giá trị cao nhất để điều chỉnh tỷ lệ
        double maxRevenue = 0;
        for (Double revenue : values) {
            if (revenue > maxRevenue) {
                maxRevenue = revenue;
            }
        }

        maxRevenue *= 1.5;  // thêm khoảng cách trên cùng

        // Số lượng vạch chia trên trục y
        int numLines = 10;  
        double increment = Math.ceil((maxRevenue * 0.9) / numLines / 10000) * 10000;  

        // Thiết lập kiểu vạch đứt khúc
        float[] dashPattern = {10, 10};  
        Stroke dashedStroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dashPattern, 0);

        g2d.setStroke(dashedStroke);
        for (int i = 0; i <= numLines; i++) {
            int y = height - 50 - (int) ((increment * i * (height - 100)) / maxRevenue);
            if (i != 0) {
                g2d.setColor(Color.black);
                g2d.drawLine(130, y, width - 50, y);  
            }
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString(df.format(increment * i), 10, y + 5);  
        }

        g2d.setStroke(new BasicStroke());  // đặt lại stroke mặc định

        // Khoảng cách giữa trục y và cột đầu tiên
        int xOffset = 130;  
        int barSpacing = 20;  // khoảng cách giữa các cột

        // Vẽ các cột cho mỗi năm
        for (int i = 0; i < animationIndex; i++) {
            if (i >= values.length) {
                break;
            }

            double barHeight = (maxRevenue != 0) ? (values[i] * (height - 100)) / maxRevenue : 0;

            g2d.setColor(new Color(111, 69, 24)); 
            int xPosition = xOffset + 60 + i * (barWidth + barSpacing);  // vị trí của cột
            g2d.fillRect(xPosition, height - 50 - (int) barHeight, barWidth - 10, (int) barHeight);  // vẽ cột
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 18));

            g2d.drawString(df.format(values[i]), xPosition + 15, height - 55 - (int) barHeight);  
        }

        // Căn chỉnh nhãn trục x vào giữa các cột
        for (int i = 0; i < animationIndex; i++) {
            if (i >= xParameters.length) {
                break;
            }

            g2d.setFont(new Font("Arial", Font.BOLD, 18));  // đặt phông chữ
            int xPosition = xOffset + 60 + i * (barWidth + barSpacing);  // vị trí của cột
            int xCenter = xPosition + (barWidth / 2);  // xác định trung tâm của cột
            g2d.drawString(xParameters[i].toString(), xCenter - (g2d.getFontMetrics().stringWidth(xParameters[i].toString()) / 2), height - 30);  // vẽ nhãn trục x
        }

        // Đặt tên cho biểu đồ
        g2d.setFont(new Font("Arial", Font.BOLD, 26));  
        g2d.setColor(new Color(111, 69, 24));  
        g2d.drawString(chartTitle, (width / 2) - (g2d.getFontMetrics().stringWidth(chartTitle) / 2), height + 10);  // tên biểu đồ ở giữa phía dưới

        // Vẽ nhãn cho trục x và trục y
        g2d.drawString(xName, width - 80, height - 20);  
        g2d.drawString("Doanh thu", 65, 40);  
    }

    public void setChartTitle(String title) {
        this.chartTitle = title;
    }
    
    
//
//    public static void main(String[] args) {
//        Integer[] xParameters = {2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026};
//        Double[] values = {10000.0, 12000.0, 9000.0, 15000.0, 18000.0, 21000.0, 25000.0, 30000.0, 32000.0, 28000.0, 26000.0, 34000.0};
//
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Revenue Chart");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(800, 600);
//            frame.getContentPane().add(new RevenueChart(xParameters, values));
//            frame.setVisible(true);
//        });
//    }

    public void setxName(String xName) {
        this.xName = xName;
    }
}
