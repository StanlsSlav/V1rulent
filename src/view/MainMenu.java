package view;


import model.base.Button;
import model.base.Panel;
import model.interfaces.IMenu;
import utils.Images;
import utils.Utilities;

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

    private final MouseAdapter switchToGamePanel = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (isLeftButtonPressed(e)) {
                Utilities.loadCities();
                switchToCard(rootPanel, "GamePanel");
            }
        }
    };

    private final MouseAdapter switchToMainMenu = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (isLeftButtonPressed(e)) {
                switchToCard(switcherPanel, "MainMenu");
                switchImage(menusPanel, getToolkit().createImage("src/assets/MainMenuBg.jpg"));
            }
        }
    };

    private final MouseAdapter switchToPausePanel = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (e.getButton() == MouseEvent.BUTTON2) {
                switchToCard(rootPanel, "PausePanel");
            }
        }
    };

    private final MouseAdapter switchToCreditsMenu = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (isLeftButtonPressed(e)) {
                switchToCard(switcherPanel, "CreditsPanel");
                switchImage(menusPanel, Images.CreditsMenuBg.get());
            }
        }
    };

    private final MouseAdapter switchToGameStartMenu = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (isLeftButtonPressed(e)) {
                switchToCard(switcherPanel, "GameStartMenu");
            }
        }
    };

    private final MouseAdapter switchToSettingsMenu = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (isLeftButtonPressed(e)) {
                switchToCard(switcherPanel, "SettingsMenu");
            }
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

        pauseContinueBtn.addMouseListener(switchToGamePanel);

        pausePanel.setLayout(null);
        pausePanel.add(pauseContinueBtn);
        pausePanel.add(pauseSettingsBtn);
        pausePanel.add(pauseExitBtn);

        gamePanel.addMouseListener(switchToPausePanel);

        creditsBackButton.addMouseListener(switchToMainMenu);
        creditsButton.addMouseListener(switchToCreditsMenu);

        gameStartBackButton.addMouseListener(switchToMainMenu);

        playButton.addMouseListener(switchToGameStartMenu);

        settingsButton.addMouseListener(switchToSettingsMenu);
        settingsBackButton.addMouseListener(switchToMainMenu);

        pauseSettingsBtn.addMouseListener(switchToMainMenu);

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

        gameStartContinueButton.addMouseListener(switchToGamePanel);
        gameStartNewButton.addMouseListener(switchToGamePanel);

        exitButton.addMouseListener(exitOnClick);
        pauseExitBtn.addMouseListener(exitOnClick);

        add(rootPanel);
        setVisible(true);
    }

    private void createUIComponents() {
        menusPanel = new Panel("mainMenu", getWindows()[0]);
        gamePanel = new Panel("game", getWindows()[0]);
        pausePanel = new Panel("pauseMenu", getWindows()[0]);

        playButton = new Button("play", "mainMenu", getWindows()[0]);
        rulesButton = new Button("rules", "mainMenu", getWindows()[0]);
        settingsButton = new Button("settings", "mainMenu", getWindows()[0]);
        creditsButton = new Button("credits", "mainMenu", getWindows()[0]);
        exitButton = new Button("exit", "mainMenu", getWindows()[0]);

        creditsBackButton = new Button("back", "creditsMenu", getWindows()[0]);

        gameStartContinueButton = new Button("continue", "gameStartMenu", getWindows()[0]);
        gameStartLoadButton = new Button("load", "gameStartMenu", getWindows()[0]);
        gameStartNewButton = new Button("new", "gameStartMenu", getWindows()[0]);
        gameStartBackButton = new Button("back", "gameStartMenu", getWindows()[0]);

        pauseContinueBtn = new Button("continue", "pauseMenu", getWindows()[0]);
        pauseContinueBtn.setBounds(357, 248, 240, 47);
        pauseSettingsBtn = new Button("settings", "pauseMenu", getWindows()[0]);
        pauseSettingsBtn.setBounds(357, 318, 240, 47);
        pauseExitBtn = new Button("exit", "pauseMenu", getWindows()[0]);
        pauseExitBtn.setBounds(357, 386, 240, 47);

        settingsBackButton = new Button("back", "settingsMenu", getWindows()[0]);
    }
}
