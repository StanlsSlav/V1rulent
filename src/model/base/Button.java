package model.base;


import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import static java.awt.Font.PLAIN;

public class Button extends JButton {
    public Button() {
        initialize();
    }

    public Button(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    private void initialize() {
        setFont(new Font("Roboto Light", PLAIN, 36));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
