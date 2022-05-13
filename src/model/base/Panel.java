package model.base;


import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;

/**
 * Enhanced {@link JPanel} to support background images
 */
public class Panel extends JPanel {
    public Panel(String backgroundImageName) {
        setBackgroundImage(getToolkit().getImage("src/assets/img/" + backgroundImageName + ".png"));
    }

    private Image backgroundImage;

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}