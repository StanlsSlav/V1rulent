package view;


import model.interfaces.IMenu;

import javax.swing.*;

/**
 * Un menu que sale encina de otro/s
 */
public class PopUpMenu extends JFrame implements IMenu {
    public PopUpMenu() {
        super("V1rulent");
        initialize();
    }

    public PopUpMenu(String title) {
        super(title);
        initialize();
    }

    /**
     * {@inheritDoc}
     */
    public void initialize() {
        setVisible(true);
    }
}
