package view;


import model.interfaces.IMenu;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame implements IMenu {
    private JPanel panelMain;

    public MainMenu() {
        super("Pandemic - V1rulent");
        initialize();
    }

    @Override
    public void initialize() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));

        add(panelMain);
        pack();
        setVisible(true);
    }
}
