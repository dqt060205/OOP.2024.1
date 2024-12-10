package com.group19.userinterface;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class inforPage extends JFrame {
    private final JPanel mainPanel; // Panel chính chứa CardLayout
    private final CardLayout cardLayout; // CardLayout để chuyển đổi giữa các màn hình
    private Clip backgroundMusicClip;
    private boolean isMusicPlaying;
    private GamePanel gamePanel;

    public static final int SCREEN_WIDTH = 1400;
    public static final int SCREEN_HEIGHT = 800;
//note
    public inforPage() {
        setTitle("Hust Adventure");
        Toolkit toolkit = this.getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds((dimension.width - SCREEN_WIDTH) / 2, (dimension.height - SCREEN_HEIGHT) / 2 - 25, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
            e.printStackTrace();
        }
        mainPanel.add(gamePanel, "Game");
        this.addKeyListener(gamePanel);
        this.setFocusable(true);
        this.requestFocusInWindow();

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
        returnButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

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
        level1Button.addActionListener(e -> startGameAtLevel(1));
        level2Button.addActionListener(e -> startGameAtLevel(2));
        level3Button.addActionListener(e -> startGameAtLevel(3));
        level4Button.addActionListener(e -> startGameAtLevel(4));
        level5Button.addActionListener(e -> startGameAtLevel(5));
        level6Button.addActionListener(e -> startGameAtLevel(6));

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
        return button;
    }

    private void startGameAtLevel(int level) {
        cardLayout.show(mainPanel, "Game"); // Chuyển sang GamePanel
        gamePanel.startGameAtLevel(level); // Khởi động game ở cấp độ cụ thể
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
            e.printStackTrace();
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

/*package com.group19.userinterface;

//package userinterface;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class inforPage extends JFrame {
    private final JPanel contentPane;
    private Image mainBackground;
    private boolean drawMainBackground;
    private Clip backgroundMusicClip;
    private boolean isMusicPlaying;


    public inforPage() {
        setTitle("Hust Adventure");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 800);
        setLocationRelativeTo(null);

        drawMainBackground = true;
        isMusicPlaying = false;

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
        newGameButton.addActionListener(e -> openLevelSelectionDialog());

        // Hành động cho nút "Continue"
        continueButton.addActionListener(e -> openLevelSelectionDialog());
     
        // Hành động cho nút "Music"
        musicButton.addActionListener(e -> toggleMusic());

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
        this.setVisible(false);
        // Tạo cửa sổ hướng dẫn
        JDialog instructionDialog = new JDialog(this, "Instructions", true);
        instructionDialog.setSize(1400, 800);
        instructionDialog.setLocationRelativeTo(this);

        // Tạo panel chính với layout null
        JPanel instructionPanel = new JPanel(null);

        // Load hình nền
        ImageIcon instructionBackgroundIcon = new ImageIcon("data/InstructionBackground.png");
        JLabel instructionBackgroundLabel = new JLabel(instructionBackgroundIcon);

        // Đặt kích thước cho hình nền
        instructionBackgroundLabel.setBounds(0, 0, 1400, 800);
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
   
        //Image selectLevelBackground;
        this.setVisible(false);
        // tạo hộp thoại
    	JDialog selectLevelDialog = new JDialog(this, "Select Level", true);
    	selectLevelDialog.setSize(1400, 800);
    	selectLevelDialog.setLocationRelativeTo(this);
    	// đẩy background lên
    	ImageIcon selectLevelIcon = new ImageIcon("data/SelectLevelBackground.png");
    	JLabel selectBackground = new JLabel(selectLevelIcon);
    	selectBackground.setBounds(0, 0, 1400, 800);
    	 
    	JPanel selectLevelPanel = new JPanel(null);
    	// tạo các nút
    	JButton returnButton = createButton("data/ReturnButton.png", 12, 12);
    	JButton level1Button = createButton("data/Level1.png", 337, 207);
    	JButton level2Button = createButton("data/Level2.png", 604, 207);
    	JButton level3Button = createButton("data/Level3.png", 863, 207);
    	JButton level4Button = createButton("data/Level4.png", 337, 440);
    	JButton level5Button = createButton("data/Level5.png", 604, 440);
    	JButton level6Button = createButton("data/Level6.png", 863, 440);
    	
    	
    	returnButton.addActionListener(e -> {
    		selectLevelDialog.dispose();
    		this.setVisible(true);
    	});
    	level1Button.addActionListener(e -> {
    		selectLevelDialog.dispose();
    		startGameAtLevel(1);
    	});
    	level2Button.addActionListener(e -> {
    		selectLevelDialog.dispose();
    		startGameAtLevel(2);
    	});
    	level3Button.addActionListener(e -> {
    		selectLevelDialog.dispose();
    		startGameAtLevel(3);
    	});
    	level4Button.addActionListener(e -> {
    		selectLevelDialog.dispose();
    		startGameAtLevel(4);
    	});
    	level5Button.addActionListener(e -> {
    		selectLevelDialog.dispose();
    		startGameAtLevel(5);
    	});
    	level6Button.addActionListener(e -> {
    		selectLevelDialog.dispose();
    		startGameAtLevel(6);
    	});
    	
    	
    	selectLevelPanel.add(level1Button);
    	selectLevelPanel.add(level2Button);
    	selectLevelPanel.add(level3Button);
    	selectLevelPanel.add(level4Button);
    	selectLevelPanel.add(level5Button);
    	selectLevelPanel.add(level6Button);
    	selectLevelPanel.add(returnButton);
    	
    	selectLevelPanel.add(selectBackground);
    	selectLevelDialog.setContentPane(selectLevelPanel);
    	
    	selectLevelDialog.setVisible(true);
    	
       
    	
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
    
    private void toggleMusic() {
        if (isMusicPlaying) {
            stopBackgroundMusic();
        } else {
            playBackgroundMusic();
        }
        isMusicPlaying = !isMusicPlaying; // Thay đổi trạng thái nhạc
    }

    private void playBackgroundMusic() {
        try {
            File musicFile = new File("data/music.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioStream);
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);  // Phát nhạc liên tục
        } catch (Exception e) {
            e.printStackTrace();
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
        stopBackgroundMusic(); // Dừng nhạc khi đóng cửa sổ
    }
   

    public static void main(String[] args) {
        SwingUtilities.invokeLater(inforPage::new);
    }
}*/

