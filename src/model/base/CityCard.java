package model.base;


import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Dimension;


public class CityCard extends JLabel {
    private final String BASE_PATH = "src/assets/img/cards/";

    public CityCard() {
        setIcon(new ImageIcon(BASE_PATH + "Empty.png"));
        setSize(new Dimension(238, 70));
        setLocation(369, 826);
    }

    public void setColour(Colour colour) {
        setIcon(new ImageIcon(BASE_PATH + colour.name() + ".png"));
    }
}
