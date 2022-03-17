package view;


import model.interfaces.IMenu;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame implements IMenu {
    private JPanel panelMain;
    private JButton button1;
    private JButton button2;
    private JButton button3;

    public MainMenu() {
        super("Pandemic - V1rulent");
        initialize();
    }

    public MainMenu(String title) {
        super(title);
        initialize();
    }

    @Override
    public void initialize() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 250));

        add(panelMain);
        pack();
        setVisible(true);
    }
}
