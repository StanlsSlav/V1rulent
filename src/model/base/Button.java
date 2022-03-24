package model.base;


import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;

import static java.awt.Font.PLAIN;


public class Button extends JButton {
    public Button() {
        initialize();
    }

    private void initialize() {
        setFont(new Font("Roboto Light", PLAIN, 40));
        setForeground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
