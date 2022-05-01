package view;


import controller.GameManager;
import controller.OptionsManager;
import model.base.*;
import model.game.Difficulty;
import model.game.Map;
import model.game.Player;
import model.interfaces.IMenu;
import utils.Utilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.Option;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static javax.swing.SwingUtilities.invokeLater;
import static utils.Utilities.*;

public class MainMenu extends JFrame implements IMenu {
    private static MainMenu instance;

    public static MainMenu getInstance() {
        return instance;
    }

    private JPanel rootPanel;

    private JPanel menusPanel;
    private JPanel switcherPanel;

    private JPanel mainMenu;
    private JPanel creditsMenu;
    private JPanel gameStartMenu;
    private JPanel gamePanel;
    private JPanel pausePanel;

    public JButton playButton;
    private JButton rulesButton;
    private JButton settingsButton;
    private JButton creditsButton;
    private JButton exitButton;
    public JButton creditsBackButton;
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
    private JLabel nameLbl;
    public JTextField nameTxtField;
    private JLabel difficultyLbl;
    public JComboBox<Difficulty> difficultyComboBox;
    private JLabel epidemicsLbl;
    public JSpinner settingsTotalEpidemicsSpinner;

    private JLabel yellowCureIcon;
    private JLabel redCureIcon;
    private JLabel blueCureIcon;
    private JLabel greenCureIcon;

    private ArrayList<JLabel> cardsLbls;

    public VirusLabel greenTotalVirusesLbl;
    public VirusLabel blueTotalVirusesLbl;
    public VirusLabel yellowTotalVirusesLbl;
    public VirusLabel redTotalVirusesLbl;

    private JLabel virusIcon;

    private JLabel epidemicsIcon;
    public JLabel epidemicsCounterLbl;

    private CharacterIcon characterIcon;

    public JTextArea historialTxtArea;

    private boolean wasGameLoaded = false;

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
                if (!wasGameLoaded) {
                    initializeGameView();
                }

                invokeLater(() -> {
                    initializeGame();
                    setSize(1920, 1080);
                });

                centerScreen(instance);

                switchToGamePanel.mouseClicked(e);
            }
        }
    };

    public final MouseAdapter switchToMainMenu = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (isLeftButtonPressed(e)) {
                OptionsManager.getInstance().saveSettings();

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

    public final MouseAdapter switchToGameStartMenu = new MouseAdapter() {
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
                invokeLater(() -> {
                    nameTxtField.setText(OptionsManager.getInstance().playerName);
                    // FIXME: Null
                    difficultyComboBox.setSelectedIndex(OptionsManager.getInstance().difficulty.ordinal());
                    settingsTotalEpidemicsSpinner.setValue(OptionsManager.getInstance().epidemicsThreshold);
                });

                switchToCard(rootPanel, "MenusPanel");
                switchToCard(switcherPanel, "SettingsMenu");
            }
        }
    };

    public MainMenu() {
        super("V1rulent");
        instance = this;

        invokeLater(() -> {
            initialize();

            try {
                setIconImage(ImageIO.read(new File("src/assets/img/Logo.png")));
            } catch (IOException ioe) {
                System.err.println("Logo could not be found");
                ioe.printStackTrace();
            }

            centerScreen(instance);
        });
    }

    @Override
    public void initialize() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);

                final int width = getWidth();
                final int height = getHeight();

                // Los botones no deben pasan el border inclinado
                switcherPanel.setBorder(BorderFactory.createEmptyBorder(
                      0, Math.round(width / 100f * 15), 0, 0));

                // Añade 5% de border entre todos los elementos del panel principal
                final int fivePercentOfWidth = width / 100 * 5;
                final int fivePercentOfHeight = height / 100 * 5;

                menusPanel.setBorder(BorderFactory.createEmptyBorder(
                      fivePercentOfHeight, fivePercentOfWidth, fivePercentOfHeight, fivePercentOfWidth));
            }
        });

        difficultyComboBox.addItemListener(e -> {
            Difficulty newDifficulty = Difficulty.Easy;

            try {
                newDifficulty = Enum.valueOf(Difficulty.class, e.getItem().toString());
            } catch (Exception ignored) {
            }

            OptionsManager.getInstance().setDifficulty(newDifficulty);
        });

        nameTxtField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changePlayerName(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changePlayerName(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changePlayerName(e);
            }

            private void changePlayerName(DocumentEvent e) {
                try {
                    Player.getInstance().name = e.getDocument().getText(0, e.getLength());
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
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
        settingsTotalEpidemicsSpinner = new JSpinner(OptionsManager.getInstance().epidemicsLimits);
    }

    private void initializeGameView() {
        wasGameLoaded = true;

        Utilities.loadCities();
        Utilities.loadSettings();

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
        virusIcon.setSize(150, 150);
        virusIcon.setLocation(1210, 830);
        gamePanel.add(virusIcon);

        epidemicsIcon = new JLabel(new ImageIcon("src/assets/img/Lifes.png"));
        epidemicsIcon.setSize(118, 118);
        epidemicsIcon.setLocation(42, 659);
        gamePanel.add(epidemicsIcon);

        epidemicsCounterLbl = new JLabel("0");
        epidemicsCounterLbl.setSize(118, 118);
        epidemicsCounterLbl.setFont(epidemicsCounterLbl.getFont().deriveFont(Font.PLAIN, 64));
        epidemicsCounterLbl.setLocation(epidemicsIcon.getLocation().x + 150, epidemicsIcon.getLocation().y);
        epidemicsCounterLbl.addPropertyChangeListener("text",
              e -> GameManager.getInstance().checkEndOfGame());
        gamePanel.add(epidemicsCounterLbl);

        characterIcon = new CharacterIcon();
        gamePanel.add(characterIcon);

        historialTxtArea = new TextArea();
        historialTxtArea.setSize(443, 708);
        historialTxtArea.setLocation(1465, 21);

        gamePanel.add(historialTxtArea);
    }

    private void initializeGame() {
        OptionsManager.getInstance().loadSettingsFromXml();
        Map.getInstance().cities.forEach(city -> gamePanel.add(new CityLabel(city)));
        GameManager.getInstance().resetGame();
    }
}
