package model.base;


import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    private Image backgroundImage;

    public Panel(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, null);
    }
}
