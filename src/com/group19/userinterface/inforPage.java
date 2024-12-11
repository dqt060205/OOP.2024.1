package com.group19.userinterface;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
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

//note
    public inforPage() {
        setTitle("Hust Adventure");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 800);
        setLocationRelativeTo(null);
        isMusicPlaying = false;


        // Khởi tạo CardLayout và mainPanel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        setContentPane(mainPanel);

        // Thêm các màn hình vào CardLayout
        mainPanel.add(createMenuPanel(), "Menu");
        mainPanel.add(createInstructionPanel(), "Instruction");
        mainPanel.add(createLevelSelectionPanel(), "LevelSelection");
        
        // Tạo GamePanel và thêm vào CardLayout
        try {
            gamePanel = new GamePanel();
        } catch (IOException e) {
            // TODO Auto-generated catch block

        }
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
        musicButton.addActionListener(e -> toggleMusic());
        instructionButton.addActionListener(e -> cardLayout.show(mainPanel, "Instruction"));
        newGameButton.addActionListener(e -> cardLayout.show(mainPanel, "LevelSelection"));
        continueButton.addActionListener(e -> cardLayout.show(mainPanel, "LevelSelection"));

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
    public void showBlackEffect(Runnable onComplete) {
        new Thread(() -> {
            try {
                // Hiệu ứng fade-in (tăng độ trong suốt)
                for (int i = 0; i <= 240; i += 20) {
                    blackOverlay.setBackground(new Color(0, 0, 0, i)); // Thay đổi alpha
                    blackOverlay.setVisible(true);
                    Thread.sleep(30); // Điều chỉnh tốc độ hiệu ứng
                }
    
                //onComplete.run(); // Gọi callback khi fade-in hoàn tất
    
                // Hiệu ứng fade-out (giảm độ trong suốt)
                for (int i = 240; i >= 0; i -= 20) {
                    blackOverlay.setBackground(new Color(0, 0, 0, i));
                    Thread.sleep(30);
                }
                onComplete.run(); // Gọi callback khi fade-in hoàn tất

                blackOverlay.setVisible(false); // Ẩn lớp đen khi hiệu ứng kết thúc
            } catch (InterruptedException e) {
            }
        }).start();
    }
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
            gamePanel.stopGameLoop(); // Dừng game loop hiện tại
            cardLayout.show(mainPanel, "MainMenu"); // Quay lại màn hình chính
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
        JButton level1Button = createButton("data/Level1.png", 337, 207);
        JButton level2Button = createButton("data/Level2.png", 604, 207);
        JButton level3Button = createButton("data/Level3.png", 863, 207);
        JButton level4Button = createButton("data/Level4.png", 337, 440);
        JButton level5Button = createButton("data/Level5.png", 604, 440);
        JButton level6Button = createButton("data/Level6.png", 863, 440);

        // Hành động cho các nút
        returnButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
        level1Button.addActionListener(e -> {
            showBlackEffect(() -> {
                // Logic chuyển sang game panel
                startGameAtLevel(1);
            });
        });
        level2Button.addActionListener(e -> {
            showBlackEffect(() -> {
                // Logic chuyển sang game panel
                startGameAtLevel(2);
            });
        });
        level3Button.addActionListener(e -> {
            showBlackEffect(() -> {
                // Logic chuyển sang game panel
                startGameAtLevel(3);
            });
        });
        level4Button.addActionListener(e -> {
            showBlackEffect(() -> {
                // Logic chuyển sang game panel
                startGameAtLevel(4);
            });
        });
        level5Button.addActionListener(e -> {
            showBlackEffect(() -> {
                // Logic chuyển sang game panel
                startGameAtLevel(5);
            });
        });
        level6Button.addActionListener(e -> {
            showBlackEffect(() -> {
                // Logic chuyển sang game panel
                startGameAtLevel(6);
            });
        });

        levelSelectionPanel.add(returnButton);
        levelSelectionPanel.add(level1Button);
        levelSelectionPanel.add(level2Button);
        levelSelectionPanel.add(level3Button);
        levelSelectionPanel.add(level4Button);
        levelSelectionPanel.add(level5Button);
        levelSelectionPanel.add(level6Button);

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

    private void startGameAtLevel(int level) {
        cardLayout.show(mainPanel, "Game");
        gamePanel.startGameAtLevel(level); 
        JButton returnButton = createButton("data/ReturnButton.png", 20, 20);
        returnButton.addActionListener(e -> {
            gamePanel.stopGameLoop();
            cardLayout.show(mainPanel, "LevelSelection");
            gamePanel.remove(returnButton);
            gamePanel.revalidate();
            gamePanel.repaint();
        });
    
        gamePanel.setLayout(null);
        gamePanel.add(returnButton);
        gamePanel.revalidate();
        gamePanel.repaint();
    }

    private void toggleMusic() {
        if (isMusicPlaying) {
            stopBackgroundMusic();
        } else {
            playBackgroundMusic();
        }
        isMusicPlaying = !isMusicPlaying;
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
