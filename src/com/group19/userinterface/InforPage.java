package com.group19.userinterface;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class InforPage extends JFrame implements MouseListener {
    private final JPanel contentPane;
    private GamePanel gamePanel;
    private InputManager inputManager;

    public InforPage(){
        setTitle("Hust Adventure");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 800);
        setLocationRelativeTo(null);

        // Tạo contentPane với hình nền
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon mainBackgroundIcon = new ImageIcon("data/MenuBackground.png"); // Đường dẫn tới ảnh nền
                Image mainBackground = mainBackgroundIcon.getImage();
                g.drawImage(mainBackground, 0, 0, getWidth(), getHeight(), this);
            }
        };

        contentPane.setLayout(null);
        setContentPane(contentPane);

        try{
            inputManager = new InputManager(this);
        } catch (Exception e) {}

        ImageIcon instructionBackgroundIcon = new ImageIcon("data/InstructionBackground.png");
        Image instructionBackground = instructionBackgroundIcon.getImage();

        ImageIcon selectLevelBackgroundIcon = new ImageIcon("data/SelectLevelBackground.png");
        Image selectLevelBackground = selectLevelBackgroundIcon.getImage();

        // Thêm văn bản giới thiệu
        // JLabel label = new JLabel("Welcome to Hust Adventure!");
        // label.setFont(new Font("ComicGeckoPro", Font.BOLD, 40));
        // label.setForeground(Color.BLACK);
        // label.setBounds(450, 60, 600, 70);
        // contentPane.add(label);

        //Tạo nút "Setting"
        ImageIcon settingIcon = new ImageIcon("data/SettingButton.png");
        JButton settingButton = new JButton(settingIcon);
        settingButton.setBounds(12, 12, settingIcon.getIconWidth(), settingIcon.getIconHeight());
        settingButton.setBorderPainted(false);
        settingButton.setContentAreaFilled(false);
        settingButton.setFocusPainted(false);
        settingButton.setOpaque(false);

        //Tạo nút "Music"
        ImageIcon musicIcon = new ImageIcon("data/MusicButton.png");
        JButton musicButton = new JButton(musicIcon);
        musicButton.setBounds(1120, 12, musicIcon.getIconWidth(), musicIcon.getIconHeight());
        musicButton.setBorderPainted(false);
        musicButton.setContentAreaFilled(false);
        musicButton.setFocusPainted(false);
        musicButton.setOpaque(false);

        //Tạo nút "Notice"
        ImageIcon noticeIcon = new ImageIcon("data/NoticeButton.png");
        JButton noticeButton = new JButton(noticeIcon);
        noticeButton.setBounds(1270, 12, noticeIcon.getIconWidth(), noticeIcon.getIconHeight());
        noticeButton.setBorderPainted(false);
        noticeButton.setContentAreaFilled(false);
        noticeButton.setFocusPainted(false);
        noticeButton.setOpaque(false);
        

        // Tạo nút "Continue"
        ImageIcon continueIcon = new ImageIcon("data/ContinueButton.png");
        JButton continueButton = new JButton(continueIcon);
        continueButton.setBounds(570, 325, continueIcon.getIconWidth(), continueIcon.getIconHeight());
        continueButton.setBorderPainted(false);
        continueButton.setContentAreaFilled(false);
        continueButton.setFocusPainted(false);
        continueButton.setOpaque(false);

        // Tạo nút "New Game"
        ImageIcon newGameIcon = new ImageIcon("data/NewGameButton.png");
        JButton newGameButton = new JButton(newGameIcon);
        newGameButton.setBounds(570, 445, newGameIcon.getIconWidth(), newGameIcon.getIconHeight());
        newGameButton.setBorderPainted(false);
        newGameButton.setContentAreaFilled(false);
        newGameButton.setFocusPainted(false);
        newGameButton.setOpaque(false);

        // Hành động khi nhấn nút "Information"
        newGameButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Thông tin về trò chơi!"));

        // Tạo nút "Instruction"
        ImageIcon instructionIcon = new ImageIcon("data/InstructionButton.png");
        JButton instructionButton = new JButton(instructionIcon);
        instructionButton.setBounds(570, 565, instructionIcon.getIconWidth(), instructionIcon.getIconHeight());
        instructionButton.setBorderPainted(false);
        instructionButton.setContentAreaFilled(false);
        instructionButton.setFocusPainted(false);
        instructionButton.setOpaque(false);

        // Hành động khi nhấn nút "Start"
        instructionButton.addActionListener(e -> startGame());

        // Tạo nút "About Us"
        ImageIcon aboutUsIcon = new ImageIcon("data/AboutUsButton.png");
        JButton aboutUsButton = new JButton(aboutUsIcon);
        aboutUsButton.setBounds(570, 685, aboutUsIcon.getIconWidth(), aboutUsIcon.getIconHeight());
        aboutUsButton.setBorderPainted(false);
        aboutUsButton.setContentAreaFilled(false);
        aboutUsButton.setFocusPainted(false);
        aboutUsButton.setOpaque(false);

        // Tạo nút "Return"
        ImageIcon returnIcon = new ImageIcon("data/ReturnButton.png");
        JButton returnButton = new JButton(returnIcon);
        returnButton.setBounds(12, 12, returnIcon.getIconWidth(), returnIcon.getIconHeight());
        returnButton.setBorderPainted(false);
        returnButton.setContentAreaFilled(false);
        returnButton.setFocusPainted(false);
        returnButton.setOpaque(false);
        returnButton.setVisible(false);

        // Thêm nút vào contentPane
        contentPane.add(settingButton);
        contentPane.add(musicButton);
        contentPane.add(noticeButton);
        contentPane.add(continueButton);
        contentPane.add(newGameButton);
        contentPane.add(instructionButton);
        contentPane.add(aboutUsButton);
        contentPane.add(returnButton);

        // Tạo GamePanel nhưng ẩn ban đầu
        try {
            gamePanel = new GamePanel();
        } catch(Exception e) {}
        gamePanel.setVisible(false);
        add(gamePanel);

        inputManager.addMouseListeners(musicButton, continueButton, newGameButton, instructionButton, aboutUsButton, returnButton);

        setVisible(true);
    }

    // Hàm bắt đầu game
    private void startGame() {
        contentPane.setVisible(false); // Ẩn giao diện chính
        gamePanel.setVisible(true);   // Hiển thị GamePanel
        gamePanel.startGame();        // Bắt đầu game
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InforPage());
    }

}
