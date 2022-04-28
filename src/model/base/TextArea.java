package model.base;


import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TextArea extends JTextArea {
    public TextArea(String backgroundImageName) {
        super("Round 1");
        setBackgroundImage(getToolkit().getImage("src/assets/img/" + backgroundImageName + ".png"));
        initialize();
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

    private void initialize() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(false);
        setBackgroundImage(getToolkit().getImage("src/assets/img/HistorialBg.png"));
        setEnabled(false);
        setFont(getFont().deriveFont(Font.BOLD, getFont().getSize() + 4));
        setDisabledTextColor(Color.black);
    }
}