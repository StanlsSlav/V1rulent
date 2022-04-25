package view;


import model.base.Button;
import model.base.CharacterIcon;
import model.base.CityCard;
import model.base.Colour;
import model.base.CureIcon;
import model.base.Panel;
import model.base.TextArea;
import model.base.VirusLabel;
import model.interfaces.IMenu;
import utils.Utilities;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

    private JLabel yellowCureIcon;
    private JLabel redCureIcon;
    private JLabel blueCureIcon;
    private JLabel greenCureIcon;

    private ArrayList<JLabel> cardsLbls;

    private VirusLabel greenTotalVirusesLbl;
    private VirusLabel blueTotalVirusesLbl;
    private VirusLabel yellowTotalVirusesLbl;
    private VirusLabel redTotalVirusesLbl;

    private JLabel virusIcon;

    private JLabel epidemiesIcon;
    private JLabel epidemiesCounterLbl;

    private CharacterIcon characterIcon;

    private JTextArea historialTxtArea;

    private final MouseAdapter switchToGamePanel = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (isLeftButtonPressed(e)) {
                switchToCard(rootPanel, "GamePanel");
            }
        }
    };

    private final MouseAdapter switchToNewGame = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (isLeftButtonPressed(e)) {
                initializeGame();
                setSize(new Dimension(1920, 1080));
                centerScreen(instance);

                switchToGamePanel.mouseClicked(e);
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
                switchToCard(rootPanel, "PausePanel");
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

                switcherPanel.setBorder(BorderFactory.createEmptyBorder(0, Math.round(width / 100f * percent), 0, 0));

                // Añade 5% de border entre todos los elementos del panel principal
                int fivePercentOfWidth = width / 100 * 5;
                int fivePercentOfHeight = height / 100 * 5;

                menusPanel.setBorder(BorderFactory.createEmptyBorder(fivePercentOfHeight, fivePercentOfWidth, fivePercentOfHeight, fivePercentOfWidth));

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
        gameStartNewButton.addMouseListener(switchToNewGame);

        playButton.addMouseListener(switchToGameStartMenu);
        creditsButton.addMouseListener(switchToCreditsMenu);
        settingsButton.addMouseListener(switchToSettingsMenu);
        exitButton.addMouseListener(exitOnClick);

        settingsBackButton.addMouseListener(switchToMainMenu);

        gamePanel.setLayout(null);

        add(rootPanel);
        setUndecorated(true);
        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
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

    private void initializeGame() {
        Utilities.loadCities();

        // La inicialización debe ser nueva
        gamePanel.removeAll();

        yellowCureIcon = new CureIcon(Colour.Yellow);
        redCureIcon = new CureIcon(Colour.Red);
        blueCureIcon = new CureIcon(Colour.Blue);
        greenCureIcon = new CureIcon(Colour.Green);

        redCureIcon.setLocation(177, 786);
        blueCureIcon.setLocation(42, 910);
        greenCureIcon.setLocation(177, 910);

        gamePanel.add(yellowCureIcon);
        gamePanel.add(redCureIcon);
        gamePanel.add(blueCureIcon);
        gamePanel.add(greenCureIcon);

        cardsLbls = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            cardsLbls.add(new CityCard());
        }

        cardsLbls.get(1).setLocation(626, 827);
        cardsLbls.get(2).setLocation(884, 827);
        cardsLbls.get(3).setLocation(368, 917);
        cardsLbls.get(4).setLocation(626, 917);
        cardsLbls.get(5).setLocation(884, 917);

        for (JLabel card : cardsLbls) {
            gamePanel.add(card);
        }

        greenTotalVirusesLbl = new VirusLabel(Colour.Green);
        blueTotalVirusesLbl = new VirusLabel(Colour.Blue);
        yellowTotalVirusesLbl = new VirusLabel(Colour.Yellow);
        redTotalVirusesLbl = new VirusLabel(Colour.Red);

        blueTotalVirusesLbl.setLocation(1344, 793);
        yellowTotalVirusesLbl.setLocation(1184, 935);
        redTotalVirusesLbl.setLocation(1344, 935);

        gamePanel.add(greenTotalVirusesLbl);
        gamePanel.add(blueTotalVirusesLbl);
        gamePanel.add(yellowTotalVirusesLbl);
        gamePanel.add(redTotalVirusesLbl);

        virusIcon = new JLabel(new ImageIcon("src/assets/img/VirusIcon.png"));
        virusIcon.setSize(new Dimension(150, 150));
        virusIcon.setLocation(1210, 830);
        gamePanel.add(virusIcon);

        epidemiesIcon = new JLabel(new ImageIcon("src/assets/img/Lifes.png"));
        epidemiesIcon.setSize(new Dimension(118, 118));
        epidemiesIcon.setLocation(42, 659);
        gamePanel.add(epidemiesIcon);

        epidemiesCounterLbl = new JLabel("0");
        epidemiesCounterLbl.setSize(new Dimension(118, 118));
        epidemiesCounterLbl.setFont(epidemiesCounterLbl.getFont().deriveFont(Font.PLAIN, 64));
        epidemiesCounterLbl.setLocation(epidemiesIcon.getLocation().x + 150, epidemiesIcon.getLocation().y);
        gamePanel.add(epidemiesCounterLbl);

        characterIcon = new CharacterIcon();
        gamePanel.add(characterIcon);


        historialTxtArea = new TextArea("Round 1", "HistorialBg");
        historialTxtArea.setSize(443, 708);
        historialTxtArea.setLocation(1465, 21);
        historialTxtArea.setLineWrap(true);
        historialTxtArea.setWrapStyleWord(true);
        historialTxtArea.setOpaque(false);
        historialTxtArea.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        gamePanel.add(historialTxtArea);
    }
}
