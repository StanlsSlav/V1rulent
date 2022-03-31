package model.base;


import javax.swing.*;
import java.awt.*;


public class Panel extends JPanel {
    public Panel(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
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
