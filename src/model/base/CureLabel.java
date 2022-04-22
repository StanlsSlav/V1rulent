package model.base;


import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Dimension;

public class CureLabel extends JLabel {
    public CureLabel(Colour colour) {
        setIcon(new ImageIcon("src/assets/img/cures/%s.png", colour.name()));
        setSize(new Dimension(59, 57));
        setLocation(18, 393);
    }
}
