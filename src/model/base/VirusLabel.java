package model.base;


import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;


public class VirusLabel extends JLabel {
    public VirusLabel(Colour colour) {
        super();

        switch (colour) {
            case Blue -> setForeground(Color.getColor(HexColour.blue));
            case Red -> setForeground(Color.getColor(HexColour.red));
            case Green -> setForeground(Color.getColor("39EC6B"));
            case Yellow -> setForeground(Color.getColor(HexColour.yellow));
        }

        setFont(getFont().deriveFont(Font.PLAIN, 64));
        setSize(new Dimension(61, 79));
        setLocation(1184, 793);
        setText("0");
    }
}
