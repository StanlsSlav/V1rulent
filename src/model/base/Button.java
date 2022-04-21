package model.base;


import model.ButtonState;
import model.interfaces.IPositionable;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;

public class Button extends JButton implements IPositionable {
    private ButtonState state = ButtonState.Idle;
    private String menuName;
    private Window parentWindow;
    private String name;

    public void setMenuName(String menuName) {
        this.menuName = menuName.trim();
    }

    @Override
    public void setName(String name) {
        this.name = name.trim().toLowerCase();
    }

    public void setParentWindow(Window parentWindow) {
        this.parentWindow = parentWindow;
    }

    public Button(String name, String menuName, Window parentWindow) {
        setName(name);
        setMenuName(menuName);
        setParentWindow(parentWindow);
        initialize();
    }

    @Override
    public void updateImage() {
        File imageFile = new File(String.format("./src/assets/img/%s/%d/btn/%s/stages/%s.png",
              menuName, parentWindow.getHeight() == 0 ? 540 : parentWindow.getHeight(), name, state.name().toLowerCase()));

        if (!imageFile.exists()) {
            try {
                throw new FileNotFoundException();
            } catch (FileNotFoundException ig) {
                System.err.printf("Check path '%s'%n", imageFile.getPath());
            }
        }

        setIcon(new ImageIcon(getToolkit().getImage(imageFile.getAbsolutePath())));
    }

    private void initialize() {
        updateImage();

        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(true);

        setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                state = ButtonState.Clicked;
                updateImage();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                state = ButtonState.Idle;
                updateImage();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                state = ButtonState.Hover;
                updateImage();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);

                state = ButtonState.Idle;
                updateImage();
            }
        });
    }
}
