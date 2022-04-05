package view;


import model.base.Button;
import model.base.Panel;
import model.interfaces.IMenu;
import utils.Images;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import static model.base.Button.ButtonType;
import static utils.Utilities.*;


public class MainMenu extends JFrame implements IMenu {
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

        double y = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        double x = Toolkit.getDefaultToolkit().getScreenSize().getWidth();

        // Centra la ventana del juego al centro del monitor
        getWindows()[0].setLocation((int) x / 2 - getWidth() / 2, (int) y / 2 - getHeight() / 2);
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

        pauseContinueBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                switchToCard(rootPanel, "MenusPanel");
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

        playButton = new Button(ButtonType.PRIMARY);
        rulesButton = new Button();
        settingsButton = new Button();
        creditsButton = new Button();
        exitButton = new Button();

        creditsBackButton = new Button();

        gameStartContinueButton = new Button();
        gameStartLoadButton = new Button();
        gameStartNewButton = new Button();
        gameStartBackButton = new Button();

        pauseContinueBtn = new Button("Continue", ButtonType.PRIMARY);
        pauseContinueBtn.setBounds(357, 248, 240, 47);
        pauseSettingsBtn = new Button("Settings");
        pauseSettingsBtn.setBounds(357, 318, 240, 47);
        pauseExitBtn = new Button("Exit");
        pauseExitBtn.setBounds(357, 386, 240, 47);
    }
}
