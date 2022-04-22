package model.base;


import javax.swing.Icon;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class VirusLabel extends JLabel {
    public VirusLabel(Icon image, Colour colour) {
        super(image);
        setColour(colour);

        initialize();
    }

    private Colour colour;

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    private void initialize() {
        switch (colour) {
            case Blue -> setForeground(Color.getColor(HexColour.blue));
            case Red -> setForeground(Color.getColor(HexColour.red));
            case Green -> setForeground(Color.getColor(HexColour.green));
            case Yellow -> setForeground(Color.getColor(HexColour.yellow));
        }

        setFont(getFont().deriveFont(Font.PLAIN, 32));
        setSize(new Dimension(30, 40));
        setLocation(592, 396);
        setText("0");
    }
}
