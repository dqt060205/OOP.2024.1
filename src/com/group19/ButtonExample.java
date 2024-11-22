package com.group19;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ButtonExample extends JFrame implements ActionListener {

    JButton button; // Tạo nút

    public ButtonExample() {
        // Thiết lập cho JFrame
        setTitle("Button Example");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Sử dụng layout null để dễ dàng đặt vị trí nút

        // Tạo nút và thiết lập vị trí, kích thước
        button = new JButton("Nhấn tôi!");
        button.setBounds(100, 80, 100, 40); // X, Y, Width, Height
        button.addActionListener(this); // Đăng ký sự kiện cho nút

        // Thêm nút vào JFrame
        add(button);

        // Hiển thị JFrame
        setVisible(true);
    }

    // Xử lý sự kiện khi người dùng nhấn vào nút
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            JOptionPane.showMessageDialog(this, "Bạn đã nhấn vào nút!");
        }
    }

    public static void main(String[] args) {
        new ButtonExample();
    }
}
