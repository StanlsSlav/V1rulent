package model.base;


import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class CityCard extends JLabel {
    private final String BASE_PATH = "src/assets/img/cards/";
    private Colour colour;

    public CityCard() {
        reset();
        setSize(238, 70);
        setLocation(369, 826);
    }

    public void reset() {
        setIcon(new ImageIcon(BASE_PATH + "Empty.png"));
    }

    public void setColour(Colour colour) {
        this.colour = colour;
        setIcon(new ImageIcon(BASE_PATH + colour.name() + ".png"));
    }

    public Colour getColour() {
        return this.colour;
    }
}
