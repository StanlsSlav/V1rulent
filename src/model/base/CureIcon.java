package model.base;


import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class CureIcon extends JLabel {
    private final String cureImagesBasePath = "src/assets/img/cures/";
    private boolean isUnlocked = true;
    private Colour colour;

    public CureIcon(Colour colour) {
        setSize(118, 114);
        lock();
        setLocation(42, 786);
        setColor(colour);
    }

    public void lock() {
        this.isUnlocked = false;
        setIcon(new ImageIcon(cureImagesBasePath + "Empty.png"));
    }

    public void unlock() {
        this.isUnlocked = true;
        setIcon(new ImageIcon(cureImagesBasePath + colour.name() + ".png"));
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    private void setColor(Colour colour) {
        this.colour = colour;
    }
}
