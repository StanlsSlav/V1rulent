package utils;


import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Utilities {
    public static MouseAdapter exitOnClick = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);

            if (e.getButton() == MouseEvent.BUTTON1) {
                System.exit(0);
            }
        }
    };

    public static void switchToCard(JPanel rootPanel, String cardName) {
        CardLayout cardLayout = (CardLayout) rootPanel.getLayout();
        cardLayout.show(rootPanel, cardName);
    }
}
