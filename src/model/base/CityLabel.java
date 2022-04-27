package model.base;


import model.game.City;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CityLabel extends JLabel {
    public CityLabel(City city) {
        super();
        setRelatedCity(city);

        setSize(25, 25);
        setLocation(relatedCity.getPoint());

        setIcon(new ImageIcon("src/assets/img/cities/" + relatedCity.getColor().name() + ".png"));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setToolTipText(relatedCity.toString());
            }
        });
    }

    private City relatedCity;

    public void setRelatedCity(City relatedCity) {
        this.relatedCity = relatedCity;
    }
}
