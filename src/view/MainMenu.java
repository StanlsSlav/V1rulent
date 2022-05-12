package view;


import controller.DbManager;
import controller.GameManager;
import controller.OptionsManager;
import model.base.*;
import model.entities.GameSave;
import model.game.Difficulty;
import model.game.Map;
import model.game.Player;
import model.interfaces.IMenu;
import utils.GeneralUtilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static javax.swing.SwingUtilities.invokeLater;
import static utils.GeneralUtilities.*;

public class MainMenu extends JFrame implements IMenu {
    public static MainMenu instance;

    public static MainMenu getInstance() {
        return instance;
    }

    private JPanel rootPanel;

    private JPanel menusPanel;
    private JPanel switcherPanel;

    private JPanel mainMenu;
    private JPanel creditsMenu;
    private JPanel gameStartMenu;
    private JPanel rankingMenu;
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

    private JButton rankingBtn;
    private JTextArea rankingTxtArea;
    private JButton rankingBackBtn;
    private JPanel gameSelectionMenu;
    private JButton gameSelectionBackBtn;
    public JTable gameSelectionSavesTable;

    public JLabel yellowCureIcon;
    public JLabel redCureIcon;
    public JLabel blueCureIcon;
    public JLabel greenCureIcon;

    public ArrayList<JLabel> cardsLbls;

    public VirusLabel greenTotalVirusesLbl;
    public VirusLabel blueTotalVirusesLbl;
    public VirusLabel yellowTotalVirusesLbl;
    public VirusLabel redTotalVirusesLbl;

    private JLabel virusIcon;

    private JLabel epidemicsIcon;
    public JLabel epidemicsCounterLbl;

    public CharacterIcon characterIcon;

    public JTextArea historialTxtArea;

    private boolean wasGameLoaded = false;

    private final MouseAdapter switchToGamePanel = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (!isLeftButtonPressed(e)) {
                return;
            }

            switchToCard(rootPanel, "GamePanel");
        }
    };

    private final MouseAdapter switchToNewGame = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (!isLeftButtonPressed(e)) {
                return;
            }

            initializeNewGame();
            switchToGamePanel.mouseClicked(e);
        }
    };

    private final MouseAdapter switchToLastGame = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (!isLeftButtonPressed(e)) {
                return;
            }

            initializeLastGame();
            switchToGamePanel.mouseClicked(e);
        }
    };

    private final MouseAdapter switchToGameSaveMenu = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (!isLeftButtonPressed(e)) {
                return;
            }

            GameManager.getInstance().updateSaveTable(DbManager.getInstance().getGameSaves());
            switchToCard(switcherPanel, "GameSelectionMenu");
        }
    };

    public final MouseAdapter switchToMainMenu = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (!isLeftButtonPressed(e)) {
                return;
            }

            switchToCard(switcherPanel, "MainMenu");
            switchToCard(rootPanel, "MenusPanel");
            switchImage(menusPanel, "MainBg");
        }
    };

    private final MouseAdapter switchToPausePanel = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (e.getButton() != MouseEvent.BUTTON2) {
                return;
            }

            switchToCard(rootPanel, "PausePanel");
        }
    };

    private final MouseAdapter switchToCreditsMenu = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (!isLeftButtonPressed(e)) {
                return;
            }

            switchToCard(switcherPanel, "CreditsMenu");
            switchImage(menusPanel, "CreditsBg");
        }
    };

    public final MouseAdapter switchToGameStartMenu = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (!isLeftButtonPressed(e)) {
                return;
            }

            switchToCard(switcherPanel, "GameStartMenu");
            switchToCard(rootPanel, "MenusPanel");
        }
    };

    private final MouseAdapter switchToSettingsMenu = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (!isLeftButtonPressed(e)) {
                return;
            }

            invokeLater(() -> {
                nameTxtField.setText(OptionsManager.getInstance().playerName);
                difficultyComboBox.setSelectedIndex(OptionsManager.getInstance().difficulty.ordinal());
                settingsTotalEpidemicsSpinner.setValue(OptionsManager.getInstance().epidemicsThreshold);
            });

            switchToCard(rootPanel, "MenusPanel");
            switchToCard(switcherPanel, "SettingsMenu");
        }
    };

    private final MouseAdapter switchToRanking = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (!isLeftButtonPressed(e)) {
                return;
            }

            invokeLater(() -> {
                ArrayList<String> top10Players = DbManager.getInstance().getTop10Players();

                rankingTxtArea.setText("");
                for (int i = 0; i < top10Players.size(); i++) {
                    rankingTxtArea.append(String.format("%d. %s%n", i + 1, top10Players.get(i)));
                }
            });

            switchToCard(switcherPanel, "RankingMenu");
        }
    };

    private final MouseAdapter saveGameAndSwitchToMainMenu = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (!isLeftButtonPressed(e)) {
                return;
            }

            switchToMainMenu.mouseClicked(e);
            DbManager.getInstance().saveGame();
        }
    };

    private final MouseAdapter exitGame = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (!isLeftButtonPressed(e)) {
                return;
            }

            exit(0, false);
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
                try {
                    Player.getInstance().setName(e.getDocument().getText(0, e.getLength()));
                } catch (BadLocationException ignored) {
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    Player.getInstance().setName(e.getDocument().getText(0, e.getLength()));
                } catch (BadLocationException ignored) {
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    Player.getInstance().setName(e.getDocument().getText(0, e.getLength()));
                } catch (BadLocationException ignored) {
                }
            }
        });

        settingsTotalEpidemicsSpinner.addChangeListener(e ->
              OptionsManager.getInstance().setEpidemicsThreshold((int) settingsTotalEpidemicsSpinner.getValue()));

        gameSelectionSavesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int id = Integer.parseInt(String.valueOf(gameSelectionSavesTable.getValueAt(gameSelectionSavesTable.getSelectedRow(), 0)));
                initializeSavedGame(id);
                switchToGamePanel.mouseClicked(e);
            }
        });

        pauseContinueBtn.addMouseListener(switchToGamePanel);
        pauseSettingsBtn.addMouseListener(switchToSettingsMenu);

        gamePanel.addMouseListener(switchToPausePanel);

        creditsBackButton.addMouseListener(switchToMainMenu);

        gameStartBackButton.addMouseListener(switchToMainMenu);
        gameStartLoadButton.addMouseListener(switchToGameSaveMenu);
        gameStartContinueButton.addMouseListener(switchToLastGame);
        gameStartNewButton.addMouseListener(switchToNewGame);

        gameSelectionBackBtn.addMouseListener(switchToGameStartMenu);

        playButton.addMouseListener(switchToGameStartMenu);
        creditsButton.addMouseListener(switchToCreditsMenu);
        rankingBtn.addMouseListener(switchToRanking);
        settingsButton.addMouseListener(switchToSettingsMenu);
        exitButton.addMouseListener(exitGame);

        settingsBackButton.addMouseListener(switchToMainMenu);
        rankingBackBtn.addMouseListener(switchToMainMenu);
        pauseBackBtn.addMouseListener(saveGameAndSwitchToMainMenu);

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
        rankingBtn = new Button();
        creditsButton = new Button();
        exitButton = new Button();

        creditsBackButton = new Button();

        gameStartContinueButton = new Button(Button.ButtonType.PRIMARY);
        gameStartLoadButton = new Button();
        gameStartNewButton = new Button();
        gameStartBackButton = new Button();

        gameSelectionBackBtn = new Button();

        pauseContinueBtn = new Button(Button.ButtonType.PRIMARY);
        pauseSettingsBtn = new Button();
        pauseBackBtn = new Button();

        settingsBackButton = new Button();
        settingsTotalEpidemicsSpinner = new JSpinner(OptionsManager.getInstance().epidemicsLimits);

        rankingBackBtn = new Button();

        // La cabecera no se mostrara porque falta el JScrollPane, que también alarga los elementos del menu
        // Pero sin una cabecera entonces el null exception saltara
        gameSelectionSavesTable = new JTable(new Object[10][2], new Object[] {"Id", "Save Date"});
    }

    private void initializeGameView() {
        wasGameLoaded = true;

        GeneralUtilities.loadCities();

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
        gamePanel.add(epidemicsCounterLbl);

        characterIcon = new CharacterIcon();
        gamePanel.add(characterIcon);

        historialTxtArea = new TextArea();
        historialTxtArea.setSize(443, 708);
        historialTxtArea.setLocation(1465, 21);

        gamePanel.add(historialTxtArea);
    }

    private void initializeNewGame() {
        initializeBaseGame();
        GameManager.getInstance().resetGame();
    }

    private void initializeLastGame() {
        initializeBaseGame();

        if (DbManager.getInstance().tryLoadingLastGame()) {
            return;
        }

        initializeNewGame();
    }

    private void initializeSavedGame(int id) {
        initializeBaseGame();

        if (id < 1) {
            initializeLastGame();
            return;
        }

        ArrayList<GameSave> gameSaves = DbManager.getInstance().getGameSaves();
        Optional<GameSave> selectedGameSave = gameSaves.stream().filter(x -> x.id == id).findFirst();

        if (selectedGameSave.isPresent()) {
            GameManager.getInstance().loadSave(selectedGameSave.get());
            return;
        }

        initializeNewGame();
    }

    private void initializeBaseGame() {
        OptionsManager.getInstance().loadSettingsFromXml();
        invokeLater(() -> Map.getInstance().getCities().forEach(city -> gamePanel.add(new CityLabel(city))));

        if (!wasGameLoaded) {
            initializeGameView();
        }
    }
}