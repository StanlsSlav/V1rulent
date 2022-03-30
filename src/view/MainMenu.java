package view;


import model.base.Button;
import model.base.Panel;
import model.interfaces.IMenu;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static utils.Utilities.*;


public class MainMenu extends JFrame implements IMenu {
    private JLabel menuTitle;

    private JPanel mainPanel;
    private JPanel mainMenu;
    private JPanel switcherPanel;

    private JButton playButton;
    private JButton rulesButton;
    private JButton settingsButton;
    private JButton creditsButton;
    private JButton exitButton;
    private JPanel creditsMenu;
    private JButton creditsBackButton;
    private JPanel gameStartMenu;
    private JButton gameStartContinueButton;
    private JButton gameStartBackButton;
    private JButton gameStartLoadButton;
    private JButton gameStartNewButton;
    private JLabel versionLabel;
    private JLabel authorsLabel;
    private JLabel bryanLabel;
    private JLabel iagoLabel;
    private JLabel ericLabel;
    private JLabel sebLabel;

    public MainMenu() {
        super("V1rulent");
        initialize();

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
                int percent = 10;

                if (width > 1550) {
                    percent += 10;
                } else if (width > 1295) {
                    percent += 5;
                } else if (width > 1100) {
                    percent += 3;
                }

                switcherPanel.setBorder(
                      BorderFactory.createEmptyBorder(0, Math.round(width / 100f * percent), 0, 0));

                // Añade 5% de border entre todos los elementos del panel principal
                int fivePercentOfWidth = width / 100 * 5;
                int fivePercentOfHeight = height / 100 * 5;

                mainPanel.setBorder(
                      BorderFactory.createEmptyBorder(fivePercentOfHeight, fivePercentOfWidth, fivePercentOfHeight,
                                                      fivePercentOfWidth));

                System.out.printf("Width: %d - Height: %d%n", getWidth(), getHeight());
            }
        });

        creditsBackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                if (isLeftButtonPressed(e)) {
                    switchToCard(switcherPanel, "MainMenu");
                }
            }
        });

        creditsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                if (isLeftButtonPressed(e)) {
                    switchToCard(switcherPanel, "CreditsMenu");
                }
            }
        });

        gameStartBackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                if (isLeftButtonPressed(e)) {
                    switchToCard(switcherPanel, "MainMenu");
                }
            }
        });

        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                if (isLeftButtonPressed(e)) {
                    switchToCard(switcherPanel, "GameStartMenu");
                }
            }
        });

        exitButton.addMouseListener(exitOnClick);

        try {
            menuTitle.setFont(
                  Font.createFont(Font.TRUETYPE_FONT, new File("./src/assets/fonts/Orbitron-SemiBold.ttf")));

            versionLabel.setFont(menuTitle.getFont());
            authorsLabel.setFont(menuTitle.getFont());
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        menuTitle.setFont(menuTitle.getFont().deriveFont(Font.PLAIN, 86));
        versionLabel.setFont(versionLabel.getFont().deriveFont(Font.PLAIN, 32));
        authorsLabel.setFont(authorsLabel.getFont().deriveFont(Font.PLAIN, 32));

        add(mainPanel);
        setVisible(true);
    }

    private void createUIComponents() {
        mainPanel = new Panel(Toolkit.getDefaultToolkit().createImage("./src/assets/MainMenuBg.jpg"));

        playButton = new Button();
        rulesButton = new Button();
        settingsButton = new Button();
        creditsButton = new Button();
        exitButton = new Button();

        creditsBackButton = new Button();

        gameStartContinueButton = new Button();
        gameStartLoadButton = new Button();
        gameStartNewButton = new Button();
        gameStartBackButton = new Button();
    }
}
