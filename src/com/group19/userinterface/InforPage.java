package com.group19.userinterface;

import java.awt.*;
import javax.swing.*;

public class InforPage extends JFrame {
    private final JPanel contentPane;
    private GamePanel gamePanel;

    public InforPage() {
        setTitle("Hust Adventure");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 800);
        setLocationRelativeTo(null);

        // Tạo contentPane với hình nền
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("C:\\Users\\LAPTOP PC\\Desktop\\Test\\image\\bgr.jpg"); // Đường dẫn tới ảnh nền
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Thêm văn bản giới thiệu
        JLabel label = new JLabel("Welcome to Hust Adventure!");
        label.setFont(new Font("ComicGeckoPro", Font.BOLD, 40));
        label.setForeground(Color.BLACK);
        label.setBounds(450, 60, 600, 70);
        contentPane.add(label);

        // Tạo nút "Information"
        ImageIcon infoIcon = new ImageIcon("C:\\Users\\LAPTOP PC\\Desktop\\Test\\image\\infor.png");
        JButton infoButton = new JButton(infoIcon);
        infoButton.setBounds(100, 200, infoIcon.getIconWidth(), infoIcon.getIconHeight());
        infoButton.setBorderPainted(false);
        infoButton.setContentAreaFilled(false);
        infoButton.setFocusPainted(false);
        infoButton.setOpaque(false);

        // Hành động khi nhấn nút "Information"
        infoButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Thông tin về trò chơi!"));

        // Tạo nút "Start"
        ImageIcon startIcon = new ImageIcon("C:\\Users\\LAPTOP PC\\Desktop\\Test\\image\\start.png");
        JButton startButton = new JButton(startIcon);
        startButton.setBounds(600, 200, startIcon.getIconWidth(), startIcon.getIconHeight());
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);
        startButton.setOpaque(false);

        // Hành động khi nhấn nút "Start"
        startButton.addActionListener(e -> startGame());

        // Thêm nút vào contentPane
        contentPane.add(infoButton);
        contentPane.add(startButton);

        // Tạo GamePanel nhưng ẩn ban đầu
        try {
            gamePanel = new GamePanel();
        } catch(Exception e) {}
        gamePanel.setVisible(false);
        add(gamePanel);

        setVisible(true);
    }

    // Hàm bắt đầu game
    private void startGame() {
        contentPane.setVisible(false); // Ẩn giao diện chính
        gamePanel.setVisible(true);   // Hiển thị GamePanel
        gamePanel.startGame();        // Bắt đầu game
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InforPage());
    }
}
