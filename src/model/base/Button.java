package model.base;


import model.game.ButtonState;
import model.interfaces.IPositionable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Paths;

import static java.awt.Font.PLAIN;

public class Button extends JButton implements IPositionable {
    public enum ButtonType {
        PRIMARY,
        SECONDARY
    }

    private String basePath = "assets/img";
    private final ButtonType type;
    private ButtonState state;
    private String menuName;
    private Integer windowWidth;
    private String name;
    private Image backgroundImage;

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public void setWindowWidth(Integer windowWidth) {
        this.windowWidth = windowWidth;
    }

    public Button(String menuName, Integer windowWidth) {
        type = ButtonType.SECONDARY;
        setMenuName(menuName);
        setWindowWidth(windowWidth);
        initialize();
    }

    public Button(String text, String menuName, Integer windowWidth) {
        type = ButtonType.SECONDARY;
        setText(text);
        setMenuName(menuName);
        setWindowWidth(windowWidth);
        initialize();
    }

    public Button(ButtonType type, String menuName, Integer windowWidth) {
        this.type = type;
        setMenuName(menuName);
        setWindowWidth(windowWidth);
        initialize();
    }

    public Button(String text, ButtonType type, String menuName, Integer windowWidth) {
        this.type = type;
        setText(text);
        setMenuName(menuName);
        setWindowWidth(windowWidth);
        initialize();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void updateImage() {
        // TODO: Format
        backgroundImage = getToolkit().getImage(
              basePath + menuName + windowWidth + "btn" + name + "stages" + state + ".png");
    }

    private void initialize() {
        backgroundImage = getToolkit().getImage(basePath + "/");

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
