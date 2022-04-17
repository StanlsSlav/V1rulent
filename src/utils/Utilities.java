package utils;


import model.base.Panel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Utilities {
    public static MouseAdapter exitOnClick = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);

            if (isLeftButtonPressed(e)) {
                System.exit(0);
            }
        }
    };

    public static void centerScreen(JFrame mainFrame) {
        double y = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        double x = Toolkit.getDefaultToolkit().getScreenSize().getWidth();

        // Centra la ventana del juego al centro del monitor
        Window.getWindows()[0].setLocation((int) x / 2 - mainFrame.getWidth() / 2,
                                           (int) y / 2 - mainFrame.getHeight() / 2);
    }

    public static void switchImage(JPanel panel, Image image) {
        Panel panelToSwitch = (Panel) panel;
        panelToSwitch.setBackgroundImage(image);

        // Quita los artefactos
        panel.updateUI();
    }

    public static void switchToCard(JPanel rootPanel, String cardName) {
        CardLayout cardLayout = (CardLayout) rootPanel.getLayout();
        cardLayout.show(rootPanel, cardName);
    }

    public static boolean isLeftButtonPressed(MouseEvent event) {
        return event.getButton() == MouseEvent.BUTTON1;
    }
}
