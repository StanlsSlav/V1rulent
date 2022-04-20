package view;


import model.base.Button;
import model.base.Panel;
import model.interfaces.IMenu;
import utils.Images;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import static model.base.Button.ButtonType;
import static utils.Utilities.*;

public class MainMenu extends JFrame implements IMenu {
    private final JFrame instance = this;

    private JPanel rootPanel;

    private JPanel menusPanel;
    private JPanel switcherPanel;

    private JPanel mainMenu;
    private JPanel creditsMenu;
    private JPanel gameStartMenu;
    private JPanel gamePanel;
    private JPanel pausePanel;

    private JButton playButton;
    private JButton rulesButton;
    private JButton settingsButton;
    private JButton creditsButton;
    private JButton exitButton;
    private JButton creditsBackButton;
    private JButton gameStartContinueButton;
    private JButton gameStartBackButton;
    private JButton gameStartLoadButton;
    private JButton gameStartNewButton;
    private JPanel settingsMenu;
    private JButton settingsBackButton;
    private JComboBox<Integer> resolutionsComboBox;

    private JButton pauseContinueBtn;
    private JButton pauseSettingsBtn;
    private JButton pauseExitBtn;

    private final MouseAdapter switchToGame = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            switchToCard(rootPanel, "GamePanel");
        }
    };

    public MainMenu() {
        super("V1rulent");
        initialize();

        try {
            setIconImage(ImageIO.read(new File("src/assets/Logo.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        centerScreen(instance);
    }

    @Override
    public void initialize() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(960, 540));
        setResizable(false);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);

                int width = getWidth();
                int height = getHeight();

                // Los botones no deben pasan el border inclinado
                int percent = 40;

                if (width > 1550) {
                    percent -= 25;
                } else if (width > 1295) {
                    percent -= 20;
                } else if (width > 1100) {
                    percent -= 10;
                }

                switcherPanel.setBorder(
                      BorderFactory.createEmptyBorder(0, Math.round(width / 100f * percent), 0, 0));

                // Añade 5% de border entre todos los elementos del panel principal
                int fivePercentOfWidth = width / 100 * 5;
                int fivePercentOfHeight = height / 100 * 5;

                menusPanel.setBorder(BorderFactory.createEmptyBorder(
                      fivePercentOfHeight, fivePercentOfWidth, fivePercentOfHeight, fivePercentOfWidth));

                System.out.printf("Width: %d - Height: %d%n", getWidth(), getHeight());
            }
        });

        pauseContinueBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                switchToCard(rootPanel, "GamePanel");
            }
        });

        pausePanel.setLayout(null);
        pausePanel.add(pauseContinueBtn);
        pausePanel.add(pauseSettingsBtn);
        pausePanel.add(pauseExitBtn);

        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (e.getButton() == MouseEvent.BUTTON2) {
                    switchToCard(rootPanel, "PausePanel");
                }
            }
        });

        creditsBackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (isLeftButtonPressed(e)) {
                    switchToCard(switcherPanel, "MainMenu");
                    switchImage(menusPanel, Images.MainMenuBg.get());
                }
            }
        });

        creditsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (isLeftButtonPressed(e)) {
                    switchToCard(switcherPanel, "CreditsMenu");
                    switchImage(menusPanel, Images.CreditsMenuBg.get());
                }
            }
        });

        gameStartBackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (isLeftButtonPressed(e)) {
                    switchToCard(switcherPanel, "MainMenu");
                }
            }
        });

        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (isLeftButtonPressed(e)) {
                    switchToCard(switcherPanel, "GameStartMenu");
                }
            }
        });

        settingsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (isLeftButtonPressed(e)) {
                    switchToCard(switcherPanel, "SettingsMenu");
                }
            }
        });

        settingsBackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (isLeftButtonPressed(e)) {
                    switchToCard(switcherPanel, "MainMenu");
                }
            }
        });

        pauseSettingsBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (isLeftButtonPressed(e)) {
                    switchToCard(switcherPanel, "SettingsMenu");
                    switchToCard(rootPanel, "MenusPanel");
                }
            }
        });

        resolutionsComboBox.addItem(540);
        resolutionsComboBox.addItem(720);
        resolutionsComboBox.addItem(1080);
        resolutionsComboBox.addItemListener(e -> {
            Dictionary<Integer, Integer> resolutionsAvailable = new Hashtable<>();

            resolutionsAvailable.put(540, 960);
            resolutionsAvailable.put(720, 1280);
            resolutionsAvailable.put(1080, 1920);

            int height = (int) e.getItem();
            int width = resolutionsAvailable.get(height);

            setSize(new Dimension(width, height));
            centerScreen(instance);
        });

        gameStartContinueButton.addMouseListener(switchToGame);
        gameStartNewButton.addMouseListener(switchToGame);

        exitButton.addMouseListener(exitOnClick);
        pauseExitBtn.addMouseListener(exitOnClick);

        add(rootPanel);
        setVisible(true);
    }

    private void createUIComponents() {
        menusPanel = new Panel(Images.MainMenuBg.get());
        gamePanel = new Panel(Images.GameBg.get());
        pausePanel = new Panel(Images.PauseMenuBg.get());

        playButton = new Button(ButtonType.PRIMARY, "mainMenu", getWidth());
        rulesButton = new Button("mainMenu", getWidth());
        settingsButton = new Button("mainMenu", getWidth());
        creditsButton = new Button("mainMenu", getWidth());
        exitButton = new Button("mainMenu", getWidth());

        creditsBackButton = new Button("creditsMenu", getWidth());

        gameStartContinueButton = new Button("gameStartMenu", getWidth());
        gameStartLoadButton = new Button("gameStartMenu", getWidth());
        gameStartNewButton = new Button("gameStartMenu", getWidth());
        gameStartBackButton = new Button("gameStartMenu", getWidth());

        pauseContinueBtn = new Button("Continue", ButtonType.PRIMARY, "pauseMenu", getWidth());
        pauseContinueBtn.setBounds(357, 248, 240, 47);
        pauseSettingsBtn = new Button("Settings", "pauseMenu", getWidth());
        pauseSettingsBtn.setBounds(357, 318, 240, 47);
        pauseExitBtn = new Button("Exit", "pauseMenu", getWidth());
        pauseExitBtn.setBounds(357, 386, 240, 47);

        settingsBackButton = new Button("settingsMenu", getWidth());
    }
}
