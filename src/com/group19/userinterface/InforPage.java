import java.awt.*;
import javax.swing.*;

public class InforPage extends JFrame {
    private JPanel contentPane;

    public InforPage() {
        setTitle("Like A Huster!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 800);
        setLocationRelativeTo(null);

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
        JLabel label = new JLabel("Welcom to Like A Dino!");
        label.setFont(new Font("Times New Roman", Font.BOLD, 40));
        label.setForeground(Color.BLACK);
        label.setBounds(450, 60, 600, 70);
        contentPane.add(label);

        // Tạo nút information
        ImageIcon icon = new ImageIcon("C:\\Users\\LAPTOP PC\\Desktop\\Test\\image\\infor.png");
        JButton button = new JButton(icon);
        button.setBounds(100, 200, icon.getIconWidth(), icon.getIconHeight());
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        // Tạo nút start
        ImageIcon icon2 = new ImageIcon("C:\\Users\\LAPTOP PC\\Desktop\\Test\\image\\start.png");
        JButton button2 = new JButton(icon2);
        button2.setBounds(600, 200, icon2.getIconWidth(), icon2.getIconHeight());
        button2.setBorderPainted(false);
        button2.setContentAreaFilled(false);
        button2.setFocusPainted(false);
        button2.setOpaque(false);

        // Thêm hành động cho nút information
        button.addActionListener(e -> JOptionPane.showMessageDialog(this, "Nút Information đã được nhấn!"));

        // Thêm hành động cho nút Start
        button2.addActionListener(e -> JOptionPane.showMessageDialog(this, "Nút Start được nhấn!"));

        // Thêm hai nút vào contentPane
        contentPane.add(button);
        contentPane.add(button2);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InforPage frame = new InforPage();
            frame.setVisible(true);
        });
    }
}
