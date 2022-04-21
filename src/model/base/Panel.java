package model.base;


import model.interfaces.IPositionable;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Window;
import java.io.File;
import java.io.FileNotFoundException;

public class Panel extends JPanel implements IPositionable {
    public Panel(String name, Window parentWindow) {
        setName(name);
        setParentWindow(parentWindow);
        updateImage();
    }

    private String name;

    private Window parentWindow;

    private Image backgroundImage;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setParentWindow(Window parentWindow) {
        this.parentWindow = parentWindow;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void updateImage() {
        File imageFile = new File(String.format("./src/assets/img/%s/%d/bg.png",
              name, parentWindow.getHeight() == 0 ? 540 : parentWindow.getHeight()));

        if (!imageFile.exists()) {
            try {
                throw new FileNotFoundException();
            } catch (FileNotFoundException ig) {
                System.err.printf("Check path '%s'%n", imageFile.getPath());
            }
        }

        setBackgroundImage(getToolkit().getImage(imageFile.getAbsolutePath()));
    }
}
