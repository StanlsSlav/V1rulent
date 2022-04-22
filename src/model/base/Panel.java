package model.base;


import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;

public class Panel extends JPanel {
    public Panel(String backgroundImageName) {
        setBackgroundImage(getToolkit().getImage("src/assets/" + backgroundImageName + ".jpg"));
    }

    public Panel(Image backgroundImage) {
        setBackgroundImage(backgroundImage);
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
