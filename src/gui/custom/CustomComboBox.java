/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.custom;

/**
 *
 * @author Trần Ngọc Huyền
 */
import javax.swing.*;
import java.awt.*;

public class CustomComboBox<T> extends JComboBox<T> {
    private boolean isFirstSelection = true;

    public CustomComboBox(T[] options) {
        super(options);
        initComponents();
    }

    private void initComponents() {
        // Thay đổi màu nền mặc định của JComboBox thành màu trắng
        this.setBackground(Color.WHITE);
        UIManager.put("ComboBox.border", BorderFactory.createEmptyBorder());
        // Tạo một CustomComboBoxRenderer để tạo hình chữ nhật cho mỗi mục trong JComboBox
        setRenderer(new CustomComboBoxRenderer());
    }

    @Override
    public void setModel(ComboBoxModel<T> model) {
        super.setModel(model);
        // Sau khi thiết lập mô hình dữ liệu mới, cần đảm bảo rằng renderer được cài đặt lại để phù hợp với mô hình mới
        setRenderer(new CustomComboBoxRenderer());
    }

    // Lớp này sẽ tạo một Renderer để vẽ mục ComboBox dưới dạng hình chữ nhật
    private class CustomComboBoxRenderer extends JLabel implements ListCellRenderer<Object> {
        public CustomComboBoxRenderer() {
            setOpaque(true); // Đảm bảo JLabel có thể vẽ nền
            setHorizontalAlignment(CENTER); // Canh chỉnh nội dung vào giữa
            setVerticalAlignment(CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            // Đặt giá trị của JLabel thành giá trị của mục trong JComboBox
            setText(value.toString());
            // Nếu mục đang được chọn, đặt màu nền và màu chữ
            if (isSelected) {
                setBackground(new Color(0, 80, 129));
                setForeground(Color.white);
            } else {
                // Nếu mục không được chọn, đặt màu nền và màu chữ mặc định
                setBackground(Color.white);
                setForeground(list.getForeground());
            }
            // Trả về JLabel để hiển thị trong JComboBox
            return this;
        }
    }
}


