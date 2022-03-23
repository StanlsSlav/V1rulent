package model.base;


import javax.swing.JButton;
import java.awt.Font;

import static java.awt.Font.PLAIN;

public class Button extends JButton {
    public Button() {
        initialize();
    }

    private void initialize() {
        setFont(new Font("Roboto Light", PLAIN, 36));
    }
}
