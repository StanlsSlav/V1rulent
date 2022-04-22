package model.base;


import javax.swing.JScrollPane;
import java.awt.Graphics;
import java.awt.Image;

public class ScrollPane extends JScrollPane {
    public ScrollPane(String backgroundImageName) {
        setBackgroundImage(getToolkit().getImage("src/assets/" + backgroundImageName + ".png"));
    }

    public ScrollPane(Image backgroundImage) {
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
