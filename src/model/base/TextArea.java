package model.base;


import javax.swing.JTextArea;
import java.awt.Graphics;
import java.awt.Image;


public class TextArea extends JTextArea {
    public TextArea(String text, String backgroundImageName) {
        super(text);
        setBackgroundImage(getToolkit().getImage("src/assets/img/" + backgroundImageName + ".png"));
        initialize();
    }

    public TextArea(String backgroundImageName) {
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
    }
}
