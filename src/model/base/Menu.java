package model.base;


import javax.swing.JMenu;
import java.util.ArrayList;
import java.util.EventObject;


public abstract class Menu extends JMenu {
    public String title;
    public String description;
    public ArrayList<String> options;

    public abstract void handleEvent(EventObject eventObject);
}
