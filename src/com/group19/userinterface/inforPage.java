package com.group19.userinterface;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class inforPage extends JFrame {
    private final JPanel contentPane;
    private Image mainBackground;
    private boolean drawMainBackground;

    public inforPage() {
        setTitle("Hust Adventure");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 800);
        setLocationRelativeTo(null);

        drawMainBackground = true;

        // Load hình nền chính
        ImageIcon mainBackgroundIcon = new ImageIcon("data/MenuBackground.png");
        mainBackground = mainBackgroundIcon.getImage();

        // Tạo contentPane
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (drawMainBackground) {
                    g.drawImage(mainBackground, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        contentPane.setLayout(null);
        setContentPane(contentPane);

        setupButtons();

        setVisible(true);
    }

    private void setupButtons() {
        // Tạo các nút với các vị trí và kích thước
        JButton settingButton = createButton("data/SettingButton.png", 12, 12);
        JButton musicButton = createButton("data/MusicButton.png", 1120, 12);
        JButton noticeButton = createButton("data/NoticeButton.png", 1270, 12);
        JButton continueButton = createButton("data/ContinueButton.png", 570, 325);
        JButton newGameButton = createButton("data/NewGameButton.png", 570, 445);
        JButton instructionButton = createButton("data/InstructionButton.png", 570, 565);
        JButton aboutUsButton = createButton("data/AboutUsButton.png", 570, 685);
        JButton returnButton = createButton("data/ReturnButton.png", 12, 12);

        // Đặt nút "Return" ẩn ban đầu
        returnButton.setVisible(false);

        // Hành động cho nút "Instruction"
        instructionButton.addActionListener(e -> openInstructionDialog());

        // Hành động cho nút "New Game"
        newGameButton.addActionListener(e -> startNewGame());

        // Thêm nút vào contentPane
        contentPane.add(settingButton);
        contentPane.add(musicButton);
        contentPane.add(noticeButton);
        contentPane.add(continueButton);
        contentPane.add(newGameButton);
        contentPane.add(instructionButton);
        contentPane.add(aboutUsButton);
        contentPane.add(returnButton);
    }

    private JButton createButton(String iconPath, int x, int y) {
        ImageIcon icon = new ImageIcon(iconPath);
        JButton button = new JButton(icon);
        button.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        return button;
    }

    private void openInstructionDialog() {
        // Tạo cửa sổ hướng dẫn
        JDialog instructionDialog = new JDialog(this, "Instructions", true);
        instructionDialog.setSize(1200, 800);
        instructionDialog.setLocationRelativeTo(this);

        // Load hình nền
        ImageIcon instructionBackgroundIcon = new ImageIcon("C:\\Users\\LAPTOP PC\\Desktop\\BTL OOP\\OOP.2024.1\\data\\InstructionBackground.png");
        JLabel instructionBackgroundLabel = new JLabel(instructionBackgroundIcon);

        // Đặt kích thước cho hình nền
        instructionBackgroundLabel.setBounds(0, 0, 1200, 800);

        // Tạo panel và thêm hình nền vào
        JPanel instructionPanel = new JPanel(null);
        instructionPanel.add(instructionBackgroundLabel);

        instructionDialog.setContentPane(instructionPanel);
        instructionDialog.setVisible(true);
    }

    private void startNewGame() {
        // Ẩn cửa sổ hiện tại (InforPage)
        this.setVisible(false);

        // Mở GameFrame
        try {
            GameFrame gameFrame = new GameFrame();
            gameFrame.startGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(inforPage::new);
    }
}