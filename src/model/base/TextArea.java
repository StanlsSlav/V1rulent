package model.base;


import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import static javax.swing.SwingUtilities.invokeLater;

/**
 * Custom enhanced {@link JTextArea} to support background images
 */
public class TextArea extends JTextArea {
    public TextArea() {
        super();
        initialize();
    }

    private Image backgroundImage;

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    private void initialize() {
        invokeLater(() -> {
            setOpaque(false);
            setBackgroundImage(getToolkit().getImage("src/assets/img/HistorialBg.png"));
            setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
            setEnabled(false);
            setFont(getFont().deriveFont(Font.BOLD, getFont().getSize() + 4));
            setDisabledTextColor(Color.black);

            addCaretListener(e -> invokeLater(() -> {
                int charactersThreshold = 430;
                int caretLocation = e.getDot();

                if (caretLocation > charactersThreshold) {
                    replaceRange("", 0, caretLocation - 100);
                }
            }));
        });
    }
}