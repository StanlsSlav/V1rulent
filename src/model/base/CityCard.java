package model.base;


import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Represent a city card which the player receives randomly on each new round
 */
public class CityCard extends JLabel {
    private final String BASE_PATH = "src/assets/img/cards/";
    private Colour colour;

    public CityCard() {
        reset();
        setSize(238, 70);
        setLocation(369, 826);
    }

    /**
     * Reset the current instance of the city card as if it does not exist anymore in the player's "hand"
     */
    public void reset() {
        this.colour = null;
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
