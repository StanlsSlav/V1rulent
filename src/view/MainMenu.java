package view;


import model.base.Button;
import model.base.Panel;
import model.interfaces.IMenu;
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

    private JButton pauseContinueBtn;
    private JButton pauseSettingsBtn;
    private JButton pauseBackBtn;
    private JPanel containerPanel;
    private JLabel pauseIcon;

    private JLabel yellowCureLbl;
    private JLabel redCureLbl;
    private JLabel blueCureLbl;
    private JLabel greenCureLbl;

    private ArrayList<JLabel> cardsLbls;

    private ArrayList<JLabel> totalIllnessesLbls;

    private ArrayList<VirusLabel> totalVirusesCounter;

    private JLabel virusIcon;

    private JLabel characterIcon;

    private ScrollPane historialScrollPane;

    private final MouseAdapter switchToGamePanel = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (isLeftButtonPressed(e)) {
                if (Map.getInstance().cities.size() == 0) {
                    Utilities.loadCities();
                }

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
                switchImage(menusPanel, "MainBg");
            }
        }
    };

    private final MouseAdapter switchToPausePanel = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (e.getButton() == MouseEvent.BUTTON2) {
                switchToCard(rootPanel, "PauseMenu");
            }
        }
    };

    private final MouseAdapter switchToCreditsMenu = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (isLeftButtonPressed(e)) {
                switchToCard(switcherPanel, "CreditsMenu");
                switchImage(menusPanel, "CreditsBg");
            }
        }
    };

    private final MouseAdapter switchToGameStartMenu = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (isLeftButtonPressed(e)) {
                switchToCard(switcherPanel, "GameStartMenu");
                switchToCard(rootPanel, "MenusPanel");
            }
        }
    };

    private final MouseAdapter switchToSettingsMenu = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (isLeftButtonPressed(e)) {
                switchToCard(rootPanel, "MenusPanel");
                switchToCard(switcherPanel, "SettingsMenu");
            }
        }
    };

    public MainMenu() {
        super("V1rulent");
        initialize();

        try {
            setIconImage(ImageIO.read(new File("src/assets/img/Logo.png")));
        } catch (IOException ioe) {
            System.err.println("Logo could not be found");
            ioe.printStackTrace();
        }

        centerScreen(instance);
    }

    @Override
    public void initialize() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(960, 540));

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
        pauseSettingsBtn.addMouseListener(switchToSettingsMenu);
        pauseBackBtn.addMouseListener(switchToGameStartMenu);

        gamePanel.addMouseListener(switchToPausePanel);

        creditsBackButton.addMouseListener(switchToMainMenu);

        gameStartBackButton.addMouseListener(switchToMainMenu);
        gameStartContinueButton.addMouseListener(switchToGamePanel);
        gameStartNewButton.addMouseListener(switchToGamePanel);

        playButton.addMouseListener(switchToGameStartMenu);
        creditsButton.addMouseListener(switchToCreditsMenu);
        settingsButton.addMouseListener(switchToSettingsMenu);
        exitButton.addMouseListener(exitOnClick);

        settingsBackButton.addMouseListener(switchToMainMenu);

        add(rootPanel);
        setVisible(true);
    }

    private void createUIComponents() {
        menusPanel = new Panel("MainBg");
        gamePanel = new Panel("GameBg");
        pausePanel = new Panel("PauseBg");

        playButton = new Button(Button.ButtonType.PRIMARY);
        rulesButton = new Button();
        settingsButton = new Button();
        creditsButton = new Button();
        exitButton = new Button();

        creditsBackButton = new Button();

        gameStartContinueButton = new Button(Button.ButtonType.PRIMARY);
        gameStartLoadButton = new Button();
        gameStartNewButton = new Button();
        gameStartBackButton = new Button();

        pauseContinueBtn = new Button(Button.ButtonType.PRIMARY);
        pauseSettingsBtn = new Button();
        pauseBackBtn = new Button();

        settingsBackButton = new Button();
    }
}
