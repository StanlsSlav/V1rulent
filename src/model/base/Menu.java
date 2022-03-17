package model.base;


import javax.swing.*;
import java.util.EventObject;

public abstract class Menu extends JFrame {
    public Menu() {
        super("V1rulent - Pandemic");
        initialize();
    }

    public Menu(String title) {
        super(title);
        initialize();
    }

    public Menu(String title, String description) {
        super(title);
        this.description = description;
        initialize();
    }

    public String description;

    /**
     * Maneja un evento del menu
     *
     * @param eventObject El evento a manejar
     */
    public abstract void handleEvent(EventObject eventObject);

    /**
     * Acciones a ejecutar cuando se crea el menu
     */
    public abstract void initialize();
}
