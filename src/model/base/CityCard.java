package model.base;


import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class CityCard extends JLabel {
    private final String BASE_PATH = "src/assets/img/cards/";
    private Colour colour;

    public CityCard() {
        setIcon(new ImageIcon(BASE_PATH + "Empty.png"));
        setSize(238, 70);
        setLocation(369, 826);
    }

    public void setColour(Colour colour) {
        this.colour = colour;

        // TODO: Extract to method
        setIcon(new ImageIcon(BASE_PATH + colour.name() + ".png"));
    }

    public Colour getColour() {
        return this.colour;
    }
}
