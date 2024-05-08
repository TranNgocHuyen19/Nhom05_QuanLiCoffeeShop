package gui.custom;

import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

// Lớp CustomTabbedPane kế thừa từ BasicTabbedPaneUI để tùy chỉnh giao diện của JTabbedPane
public class CustomTabbedPane extends BasicTabbedPaneUI {

    @Override
    protected Insets getTabInsets(int tabPlacement, int tabIndex) {
        return new Insets(5, 10, 5, 10); // Căn lề cho tab
    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int width, int height, boolean isSelected) {
        if (isSelected) { // Chỉ vẽ đường viền dưới cho tab được chọn
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Đảm bảo vẽ mượt mà
            g2.setColor(new Color(102, 44, 33)); // Màu đường viền
            g2.setStroke(new BasicStroke(3)); // Độ dày của đường viền
            g2.draw(new Line2D.Double(x, y + height - 1, x + width, y + height - 1)); // Đường viền dưới
            g2.dispose();
        }
    }

    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
        // Không cần vẽ dấu hiệu tập trung
    }

    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int width, int height, boolean isSelected) {
        // Không vẽ nền đặc biệt cho tab
    }

    @Override
    protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
        // Không cần vẽ đường viền cho nội dung
    }
}

    
