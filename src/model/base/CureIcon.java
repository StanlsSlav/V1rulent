package model.base;


import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class CureIcon extends JLabel {
    private final String cureImagesBasePath = "src/assets/img/cures/";
    private boolean isEmpty = true;
    private Colour colour;

    public CureIcon(Colour colour) {
        setSize(118, 114);
        lock();
        setLocation(42, 786);
        setColor(colour);
    }

    public void lock() {
        this.isEmpty = true;
        setIcon(new ImageIcon(cureImagesBasePath + "Empty.png"));
    }

    public void unlock() {
        this.isEmpty = false;
        setColor(colour);
        setIcon(new ImageIcon(cureImagesBasePath + colour.name() + ".png"));
    }

    public boolean isLocked() {
        return isEmpty;
    }

    private void setColor(Colour colour) {
        this.colour = colour;
    }
}
