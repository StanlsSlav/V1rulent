package model.base;


import controller.GameManager;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Font;

/**
 * Represents visually the counter for a certain virus colour
 */
public class VirusLabel extends JLabel {
    public VirusLabel(Colour colour) {
        super();

        SwingUtilities.invokeLater(() -> {
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
            setSize(61, 80);
        });

        setLocation(1184, 793);

        addPropertyChangeListener("text", e -> GameManager.getInstance().checkEndOfGame());
    }
}