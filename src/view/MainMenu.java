package view;


import model.base.Button;
import model.base.Panel;
import model.interfaces.IMenu;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


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
                buttonsPanel.setBorder(
                      BorderFactory.createEmptyBorder(0, getWidth() / 4, 0, getWidth() / 35));
            }
        });
    }

    @Override
    public void initialize() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(960, 540));

        add(mainPanel);
        pack();
        setVisible(true);
    }

    private void createUIComponents() {
        mainPanel = new Panel(Toolkit.getDefaultToolkit().createImage("./src/assets/MainMenuBg.png"));

        playButton = new Button(Toolkit.getDefaultToolkit().createImage("./src/assets/SecondaryButtonBg.png"));
        rulesButton = new Button();
        settingsButton = new Button();
        creditsButton = new Button();
        exitButton = new Button();
    }
}
