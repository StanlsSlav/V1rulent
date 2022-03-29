package utils;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Utilities {
    public static MouseAdapter exitOnClick = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            System.exit(0);
        }
    };
}
