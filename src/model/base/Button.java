package model.base;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.Font.PLAIN;


public class Button extends JButton {
    public enum ButtonType {
        PRIMARY,
        SECONDARY
    }

    private final ButtonType type;

    public Button() {
        type = ButtonType.SECONDARY;
        initialize();
    }

    public Button(String text) {
        type = ButtonType.SECONDARY;
        setText(text);
        initialize();
    }

    public Button(ButtonType type) {
        this.type = type;
        initialize();
    }

    public Button(String text, ButtonType type) {
        this.type = type;
        setText(text);
        initialize();
    }

    private void initialize() {
        setFont(new Font("Roboto Light", PLAIN, 40));
        setForeground(Color.BLACK);

        setHorizontalAlignment(LEFT);
        setHorizontalTextPosition(CENTER);

        setFocusPainted(false);
        setBorderPainted(false);

        if (type == ButtonType.PRIMARY) {
            setBackground(Color.decode("#F5F5F5"));
        } else {
            setBackground(Color.decode("#CD3F3F"));
        }

        setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (type == ButtonType.PRIMARY) {
                    setBackground(Color.decode("#777777"));
                    return;
                }

                setBackground(Color.decode("#480808"));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                if (type == ButtonType.PRIMARY) {
                    setBackground(Color.decode("#C0C0C0"));
                    return;
                }

                setBackground(Color.decode("#8E1E1E"));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                if (type == ButtonType.PRIMARY) {
                    setBackground(Color.decode("#C0C0C0"));
                    return;
                }

                setBackground(Color.decode("#8E1E1E"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);

                if (type == ButtonType.PRIMARY) {
                    setBackground(Color.decode("#F5F5F5"));
                    return;
                }

                setBackground(Color.decode("#CD3F3F"));
            }
        });
    }
}
