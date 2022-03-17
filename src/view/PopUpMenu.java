package view;


import model.base.Menu;

import java.util.ArrayList;
import java.util.EventObject;

/**
 * Un menu que sale encina de otro/s
 */
public class PopUpMenu extends Menu {
    public PopUpMenu(String title) {
        super(title);
    }

    public PopUpMenu(String title, String description) {
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
