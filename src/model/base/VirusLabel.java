package model.base;


import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;


public class VirusLabel extends JLabel {
    public VirusLabel(Colour colour) {
        super();

        switch (colour) {
            case Blue:
                setForeground(Color.decode(HexColour.blue));
                break;
            case Red:
                setForeground(Color.decode(HexColour.red));
                break;
            case Green:
                setForeground(Color.decode(HexColour.green));
                break;
            case Yellow:
                setForeground(Color.decode(HexColour.yellow));
                break;
        }

        setFont(getFont().deriveFont(Font.PLAIN, 64));
        setSize(new Dimension(61, 79));
        setLocation(1184, 793);
        setText("0");
    }
}
