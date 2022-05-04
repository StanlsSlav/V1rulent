package model.base;


import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class CureIcon extends JLabel {
    private final String cureImagesBasePath = "src/assets/img/cures/";
    private Colour colour;

    public CureIcon(Colour colour) {
        setSize(118, 114);
        setIcon(new ImageIcon(cureImagesBasePath + "Empty.png"));
        setLocation(42, 786);
        setColor(colour);
    }

    public void unlock() {
        setColor(colour);
        setIcon(new ImageIcon(cureImagesBasePath + colour.name() + ".png"));
    }

    private void setColor(Colour colour) {
        this.colour = colour;
    }
}
