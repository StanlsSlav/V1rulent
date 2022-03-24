package view;


import com.sun.deploy.panel.ITreeNode;
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
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu extends JFrame implements IMenu {
    private JLabel menuTitle;

    private JPanel mainPanel;
    private JPanel buttonsPanel;

    private JButton playButton;
    private JButton rulesButton;
    private JButton settingsButton;
    private JButton creditsButton;
    private JButton exitButton;

    public MainMenu() {
        super("V1rulent");
        initialize();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);

                int width = getWidth();
                int height = getHeight();

                // Asegúrate de que los botones no pasan el border inclinado negro
                int percent = 9;

                if (width > 1550) {
                    percent += 10;
                } else if (width > 1295) {
                    percent += 7;
                } else if (width > 1100) {
                    percent += 5;
                }

                buttonsPanel.setBorder(
                      BorderFactory.createEmptyBorder(0, Math.round(width / 100f * percent), 0, 0));

                double y = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
                double x = Toolkit.getDefaultToolkit().getScreenSize().getWidth();

                // TODO: activar despues para fijar pantalla
//                if (x >= 1920 && y >= 1080) {
//                    setSize(new Dimension(1920, 1080));
//                } else if (x >= 1366 && y >= 768) {
//                    setSize(new Dimension(1366, 768));
//                } else if (x >= 1280 && y >= 720) {
//                    setSize(new Dimension(1280, 720));
//                } else {
//                    setMinimumSize(new Dimension(960, 540));
//                }

                // Añade 5% de border entre todos los elementos del panel principal
                int fivePercentOfWidth = width / 100 * 5;
                int fivePercentOfHeight = height / 100 * 5;

                mainPanel.setBorder(
                      BorderFactory.createEmptyBorder(fivePercentOfHeight, fivePercentOfWidth, fivePercentOfHeight, fivePercentOfWidth));

                System.out.printf("Width: %d - Height: %d%n", getWidth(), getHeight());
            }
        });

        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }
        });
    }

    @Override
    public void initialize() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(960, 540));

        double y = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        double x = Toolkit.getDefaultToolkit().getScreenSize().getWidth();

        // Center the game window center to monitor's center
        getWindows()[0].setLocation((int) x / 2 - getWidth() / 2, (int) y / 2 - getHeight() / 2);

        add(mainPanel);

        // TODO: Uncomenta después de la creación del menu de settings
        //setResizable(false);
        setVisible(true);
    }

    private void createUIComponents() {
        mainPanel = new Panel(Toolkit.getDefaultToolkit().createImage("./src/assets/MainMenuBg.png"));

        playButton = new Button();
        rulesButton = new Button();
        settingsButton = new Button();
        creditsButton = new Button();
        exitButton = new Button();
    }
}
