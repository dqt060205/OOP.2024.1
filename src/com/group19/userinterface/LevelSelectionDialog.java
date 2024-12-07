package com.group19.userinterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LevelSelectionDialog extends JDialog {

    private int selectedLevel = -1; // Chứa level được chọn (-1 là chưa chọn)

    public LevelSelectionDialog(JFrame parent, int totalLevels) {
        super(parent, "Select Level", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Choose a Level to Start", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // Tạo layout lưới
        for (int i = 1; i <= totalLevels; i++) {
            JButton levelButton = new JButton("Level " + i);
            int level = i;
            levelButton.addActionListener(e -> {
                selectedLevel = level; // Gán level được chọn
                dispose(); // Đóng dialog
            });
            buttonPanel.add(levelButton);
        }

        add(buttonPanel, BorderLayout.CENTER);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            selectedLevel = -1; // Không chọn level nào
            dispose();
        });
        add(cancelButton, BorderLayout.SOUTH);
    }

    public int getSelectedLevel() {
        return selectedLevel;
    }
}
