/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.custom;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 *
 * @author Trần Ngọc Huyền
 */
public class CustomRectangularButton extends JButton {

    private Color[] buttonColors; // Mảng màu: [text color, normal background, hover background]

    public CustomRectangularButton(Color[] buttonColors) {
        this.buttonColors = buttonColors;

        setContentAreaFilled(false); // Loại bỏ màu nền mặc định
        setFocusPainted(false);      // Loại bỏ viền khi nút được chọn
        setBorderPainted(false);     // Loại bỏ đường viền của nút

        // Sử dụng sự kiện chuột để thay đổi giao diện khi di chuyển qua hoặc nhấn
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(buttonColors[1]);
                setForeground(Color.black);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(buttonColors[1]);
                setForeground(Color.black);;
            }

            @Override
            public void mousePressed(MouseEvent e) {
             setBackground(buttonColors[1]);
                setForeground(Color.black);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
               setBackground(buttonColors[1]);
                setForeground(Color.black);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // Chọn màu nền dựa trên trạng thái của nút
        if (getModel().isPressed()) { // Nếu nút được nhấn
            g2.setColor(buttonColors[1]); // Màu nền bình thường
        } else if (getModel().isRollover()) { // Nếu con trỏ chuột di chuyển qua nút
            g2.setColor(buttonColors[2]); // Màu nền nhạt hơn
        } else {
            g2.setColor(buttonColors[1]); // Màu nền bình thường
        }

        g2.fillRect(0, 0, getWidth(), getHeight()); // Vẽ nền của nút

        // Đặt màu văn bản
        setForeground(buttonColors[0]); // Màu văn bản từ mảng màu

        super.paintComponent(g); // Gọi phương thức cha để vẽ các thành phần khác của nút
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Không vẽ đường viền
    }
}
