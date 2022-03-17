package view;


import model.base.Menu;

import java.util.EventObject;

/**
 * El menu principal que se muestra al inicio del juego
 */
public class MainMenu extends Menu {
    public MainMenu() {
    }

    public MainMenu(String title) {
        super(title);
    }

    public MainMenu(String title, String description) {
        super(title, description);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleEvent(EventObject eventObject) {

    }
}
