package com.group19.userinterface;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class inforPage extends JFrame {
    private final JPanel mainPanel; // Panel chính chứa CardLayout
    private final JPanel blackOverlay;
    private final CardLayout cardLayout; // CardLayout để chuyển đổi giữa các màn hình
    private Clip backgroundMusicClip;
    private boolean isMusicPlaying;
    private GamePanel gamePanel;
    private List<JButton> levelButtons = new ArrayList<>();
    final boolean[] isToggled = {false};


//note
    public inforPage() {
        setTitle("Hust Adventure");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 800);
        setLocationRelativeTo(null);
        isMusicPlaying = true;

        try {
            gamePanel = new GamePanel();
        } catch (IOException e) {
            // TODO Auto-generated catch block

        }
        // Khởi tạo CardLayout và mainPanel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        setContentPane(mainPanel);

        // Thêm các màn hình vào CardLayout
        mainPanel.add(createMenuPanel(), "Menu");
        mainPanel.add(createInstructionPanel(), "Instruction");
        mainPanel.add(createLevelSelectionPanel(), "LevelSelection");
        mainPanel.add(createAboutUsPanel(), "AboutUs");
        
        // Tạo GamePanel và thêm vào CardLayout
        mainPanel.add(gamePanel, "Game");
        this.addKeyListener(gamePanel);
        this.setFocusable(true);
        this.requestFocusInWindow();
        blackOverlay = new JPanel();
        blackOverlay.setBackground(new Color(0, 0, 0, 255)); // Màu đen hoàn toàn
        blackOverlay.setBounds(0, 0, 1400, 800); // Kích thước phủ toàn màn hình
        blackOverlay.setVisible(false); // Ẩn ban đầu
        add(blackOverlay); // Thêm vào JFrame hoặc JPanel chính
        // Hiển thị màn hình đầu tiên (menu chính)
        cardLayout.show(mainPanel, "Menu");

        setVisible(true);
        playBackgroundMusic();
    }

    private JPanel createAboutUsPanel(){
        JPanel AboutUsPanel = new JPanel(null){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                //Vẽ hình nền cho About Us
                ImageIcon AboutUsIcon = new ImageIcon("data/AboutUsBackground.png");
                g.drawImage(AboutUsIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        // Nút "Return" quay lại menu chính
        JButton returnButton = createButton("data/ReturnButton.png", 20, 20);
        returnButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "Menu"); // Quay lại màn hình chính
        });
        // Tạo các Button các ảnh...
        AboutUsPanel.add(returnButton);
        return AboutUsPanel;
    }
    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Vẽ hình nền cho menu
                ImageIcon mainBackgroundIcon = new ImageIcon("data/MenuBackground.png");
                g.drawImage(mainBackgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Tạo các nút trong menu
        JButton settingButton = createButton("data/SettingButton.png", 12, 12);
        JButton musicButton = createButton("data/MusicButton.png", 1120, 12);
        JButton noticeButton = createButton("data/NoticeButton.png", 1270, 12);
        JButton continueButton = createButton("data/ContinueButton.png", 570, 325);
        JButton newGameButton = createButton("data/NewGameButton.png", 570, 445);
        JButton instructionButton = createButton("data/InstructionButton.png", 570, 565);
        JButton aboutUsButton = createButton("data/AboutUsButton.png", 570, 685);
        // Hành động cho các nút
        musicButton.addActionListener(e -> {
            toggleMusic();
            isToggled[0] = !isToggled[0];
            if (isToggled[0]) {
            musicButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                musicButton.setIcon(new ImageIcon("data/MuteButton_hover.png")); // Đổi hình ảnh khi hover
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                musicButton.setIcon(new ImageIcon("data/MuteButton.png")); // Quay lại hình ảnh ban đầu
            }
        }); }
            else {
                musicButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        musicButton.setIcon(new ImageIcon("data/MusicButton_hover.png")); // Đổi hình ảnh khi hover
                    }
                    
                    @Override
                    public void mouseExited(MouseEvent e) {
                        musicButton.setIcon(new ImageIcon("data/MusicButton.png")); // Quay lại hình ảnh ban đầu
                    }
                });
            }
        });
        instructionButton.addActionListener(e -> cardLayout.show(mainPanel, "Instruction"));
        newGameButton.addActionListener(e -> cardLayout.show(mainPanel, "LevelSelection"));
        continueButton.addActionListener(e -> cardLayout.show(mainPanel, "LevelSelection"));
        aboutUsButton.addActionListener(e -> cardLayout.show(mainPanel, "AboutUs")); 


        // Thêm nút vào panel
        menuPanel.add(settingButton);
        menuPanel.add(musicButton);
        menuPanel.add(noticeButton);
        menuPanel.add(continueButton);
        menuPanel.add(newGameButton);
        menuPanel.add(instructionButton);
        menuPanel.add(aboutUsButton);

        return menuPanel;
    }
    /*public void showBlackEffect(Runnable onComplete) {
        Timer fadeInTimer = new Timer(30, null); // Mỗi 30ms thực hiện 1 bước
        Timer fadeOutTimer = new Timer(30, null);
        
        // Hiệu ứng fade-in
        fadeInTimer.addActionListener(new ActionListener() {
            int alpha = 0; // Độ trong suốt ban đầu
            @Override
            public void actionPerformed(ActionEvent e) {
                if (alpha <= 240) {
                    blackOverlay.setBackground(new Color(0, 0, 0, alpha));
                    blackOverlay.setVisible(true);
                    alpha += 20;
                } else {
                    fadeInTimer.stop(); // Dừng fade-in
                    fadeOutTimer.start(); // Bắt đầu fade-out
                }
            }
        });
        
        // Hiệu ứng fade-out
        fadeOutTimer.addActionListener(new ActionListener() {
            int alpha = 240; // Độ trong suốt ban đầu của fade-out
            @Override
            public void actionPerformed(ActionEvent e) {
                if (alpha >= 0) {
                    blackOverlay.setBackground(new Color(0, 0, 0, alpha));
                    alpha -= 20;
                } else {
                    fadeOutTimer.stop(); // Dừng fade-out
                    blackOverlay.setVisible(false);
                    if (onComplete != null) {
                        onComplete.run(); // Thực hiện callback
                    }
                }
            }
        });
        
        fadeInTimer.start(); // Bắt đầu fade-in
    }*/
    
    private JPanel createInstructionPanel() {
        JPanel instructionPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Vẽ hình nền cho instruction
                ImageIcon instructionBackgroundIcon = new ImageIcon("data/InstructionBackground.png");
                g.drawImage(instructionBackgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Nút "Return" quay lại menu chính
        JButton returnButton = createButton("data/ReturnButton.png", 20, 20);
        returnButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "Menu"); // Quay lại màn hình chính
        });

        instructionPanel.add(returnButton);
        return instructionPanel;
    }

    private JPanel createLevelSelectionPanel() {
        JPanel levelSelectionPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Vẽ hình nền cho level selection
                ImageIcon levelSelectionBackgroundIcon = new ImageIcon("data/SelectLevelBackground.png");
                g.drawImage(levelSelectionBackgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);

            }
        };

        // Tạo các nút chọn cấp độ
        JButton returnButton = createButton("data/ReturnButton.png", 12, 12);
        JButton level1Button = createButton("data/Level1.png", true, 1, 337, 207);
        JButton level2Button = createButton("data/Level2.png", false, 2, 604, 207);
        JButton level3Button = createButton("data/Level3.png", false, 3, 863, 207);
        JButton level4Button = createButton("data/Level4.png", false, 4, 337, 440);
        JButton level5Button = createButton("data/Level5.png", false, 5, 604, 440);
        JButton level6Button = createButton("data/Level6.png", false, 6, 863, 440);

        // Hành động cho các nút
        returnButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        levelSelectionPanel.add(returnButton);
        levelSelectionPanel.add(level1Button);
        levelSelectionPanel.add(level2Button);
        levelSelectionPanel.add(level3Button);
        levelSelectionPanel.add(level4Button);
        levelSelectionPanel.add(level5Button);
        levelSelectionPanel.add(level6Button);

        levelButtons.add(level1Button);
        levelButtons.add(level2Button);
        levelButtons.add(level3Button);
        levelButtons.add(level4Button);
        levelButtons.add(level5Button);
        levelButtons.add(level6Button);
        return levelSelectionPanel;
    }

    private JButton createButton(String iconPath, int x, int y) {
        ImageIcon icon = new ImageIcon(iconPath);
        JButton button = new JButton(icon);
        button.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        // Thêm hiệu ứng hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setIcon(new ImageIcon(iconPath.replace(".png", "_hover.png"))); // Đổi hình ảnh khi hover
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon(new ImageIcon(iconPath)); // Quay lại hình ảnh ban đầu
            }
        });

        return button;
    }
    public boolean isLevelUnlocked(int level) {
        return gamePanel.isLevelUnlocked(level);
    }
    public void updateLevelButtons() {
        for (int i = 0; i < levelButtons.size(); i++) {
            int levelIndex = i + 1;
            JButton levelButton = levelButtons.get(i);
            ImageIcon icon = new ImageIcon("data/Level" + levelIndex + ".png");
    
            if (isLevelUnlocked(levelIndex)) {
                levelButton.setEnabled(true);
                levelButton.setIcon(icon); // Ảnh mở khóa
            } else {
                levelButton.setEnabled(false);
                levelButton.setDisabledIcon(new ImageIcon("data/Level" + levelIndex + "_lock.png")); // Ảnh khóa
            }
                levelButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    levelButton.setIcon(new ImageIcon("data/Level" + levelIndex + "_hover.png")); // Đổi hình ảnh khi hover
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    levelButton.setIcon(icon); // Quay lại hình ảnh ban đầu
                }
                });
                levelButton.addActionListener(e -> startGameAtLevel(levelIndex));
            }
        }
    
    public JButton createButton(String iconPath, boolean isUnlocked, int levelIndex, int x, int y) {
        ImageIcon icon = new ImageIcon(iconPath);
        JButton button = new JButton();        
        // Kiểm tra trạng thái mở khóa
        if (isUnlocked) {
            button.setIcon(icon); // Hình level mở khóa
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setIcon(new ImageIcon(iconPath.replace(".png", "_hover.png"))); // Đổi hình ảnh khi hover
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    button.setIcon(icon); // Quay lại hình ảnh ban đầu
                }
            });
            button.addActionListener(e -> //{
                //showBlackEffect(() -> {
                    // Logic chuyển sang game panel
                    startGameAtLevel(levelIndex)
                //});
            /* }*/);
        } else {
            button.setIcon(new ImageIcon(iconPath.replace(".png", "_lock.png"))); // Hình level bị khóa
            //button.setEnabled(false); // Vô hiệu hóa nút khi chưa mở khóa
        }
    
        // Thiết lập các thuộc tính chung
        button.setBounds(x, y, 184, 184); // Vị trí và kích thước
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
    
        return button;
    }
    private void startGameAtLevel(int level) {
        cardLayout.show(mainPanel, "Game");
        gamePanel.startGameAtLevel(level); 
        JButton returnButton = createButton("data/ReturnButton.png", 20, 20);
        returnButton.addActionListener(e -> {
            gamePanel.stopGameLoop();
            cardLayout.show(mainPanel, "LevelSelection");
            gamePanel.remove(returnButton);
            updateLevelButtons();
            gamePanel.revalidate();
            gamePanel.repaint();
        });
    
        gamePanel.setLayout(null);
        gamePanel.add(returnButton);
        gamePanel.revalidate();
        gamePanel.repaint();
    }

    private void toggleMusic() {
        isMusicPlaying = !isMusicPlaying;
        if (isMusicPlaying) {
            playBackgroundMusic();
        } else {
            stopBackgroundMusic();
        }      
    }

    private void playBackgroundMusic() {
        try {
            File musicFile = new File("data/music.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioStream);
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
        }
    }

    private void stopBackgroundMusic() {
        if (backgroundMusicClip != null && backgroundMusicClip.isRunning()) {
            backgroundMusicClip.stop();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        stopBackgroundMusic();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(inforPage::new);
    }
}
