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

        // Hành động cho nút "Continue"
        continueButton.addActionListener(e -> openLevelSelectionDialog());

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

        // Tạo panel chính với layout null
        JPanel instructionPanel = new JPanel(null);

        // Load hình nền
        ImageIcon instructionBackgroundIcon = new ImageIcon("data/InstructionBackground.png");
        JLabel instructionBackgroundLabel = new JLabel(instructionBackgroundIcon);

        // Đặt kích thước cho hình nền
        instructionBackgroundLabel.setBounds(0, 0, 1200, 800);
        instructionPanel.add(instructionBackgroundLabel);

        // Tạo nút "Return"
        JButton returnButton = createButton("data/ReturnButton.png", 20, 20);
        returnButton.addActionListener(e -> {
            instructionDialog.dispose(); // Đóng cửa sổ hướng dẫn
            this.setVisible(true); // Hiển thị lại cửa sổ chính
        });

        instructionPanel.add(returnButton);
        instructionPanel.setComponentZOrder(returnButton, 0);

        // Đặt panel làm nội dung cho dialog
        instructionDialog.setContentPane(instructionPanel);
        instructionDialog.setVisible(true);
    }

    private void openLevelSelectionDialog() {
        int totalLevels = 4;  // Số lượng màn chơi (hoặc lấy từ LevelManager nếu có)

        // Tạo dialog chọn màn chơi
        LevelSelectionDialog dialog = new LevelSelectionDialog(this, totalLevels);
        dialog.setVisible(true);

        // Lấy màn chơi người dùng chọn
        int selectedLevel = dialog.getSelectedLevel();
        if (selectedLevel != -1) {
            System.out.println("Selected Level: " + selectedLevel);
            startGameAtLevel(selectedLevel); // Bắt đầu game ở màn chơi đã chọn
        } else {
            System.out.println("No level selected.");
        }
    }

    private void startGameAtLevel(int selectedLevel) {
        this.setVisible(false); // Ẩn cửa sổ chính

        try {
            GameFrame gameFrame = new GameFrame();
            gameFrame.startGameAtLevel(selectedLevel); // Bắt đầu game tại màn chơi đã chọn
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startNewGame() {
        this.setVisible(false); // Ẩn cửa sổ chính

        try {
            GameFrame gameFrame = new GameFrame();
            gameFrame.startGameAtLevel(1); // Bắt đầu từ màn 1
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(inforPage::new);
    }
}
