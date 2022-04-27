package model.base;


import model.ActionType;
import model.Logger;
import model.game.City;
import model.game.Player;

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

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.isControlDown()) {
                    Player.getInstance().actions = 5;
                }

                if (e.getClickCount() == 2) {
                    if (Player.tryPerformAction(ActionType.COMPLETE_CURE)){
                        relatedCity.setTotalViruses(0);
                        Logger.getInstance().log("Cured " + relatedCity.getName());
                    } else {
                        Logger.getInstance().log("Could not cure " + relatedCity.getName());
                    }
                    return;
                }

                if (relatedCity != Player.getInstance().currentCity) {
                    if (Player.tryPerformAction(ActionType.TRAVEL)) {
                        Player.getInstance().currentCity = relatedCity;
                        Logger.getInstance().log("Traveled to " + relatedCity.getName());
                    } else {
                        Logger.getInstance().log("Traveled to " + relatedCity.getName());
                    }
                    return;
                }

                if (Player.tryPerformAction(ActionType.CURE)) {
                    relatedCity.setTotalViruses(relatedCity.getTotalViruses() - 1);
                    Logger.getInstance().log("Removed a virus from %p, %p left", relatedCity.getName(), relatedCity.getTotalViruses());
                }
            }
        });
    }

    private City relatedCity;

    public void setRelatedCity(City relatedCity) {
        this.relatedCity = relatedCity;
    }
}
