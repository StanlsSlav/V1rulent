package model.base;


import model.ActionType;
import model.game.City;
import model.game.Player;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static javax.swing.SwingUtilities.invokeLater;

public class CityLabel extends JLabel {
    public CityLabel(City city) {
        super();
        setRelatedCity(city);

        invokeLater(() -> {
            setSize(25, 25);
            setLocation(relatedCity.getPoint());

            setIcon(new ImageIcon("src/assets/img/cities/" + relatedCity.getColour().name() + ".png"));
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                String toolTipText = String.format("<html>City: %s<br>Viruses: %d</html>",
                      relatedCity.getName(), relatedCity.getTotalViruses());

                setToolTipText(toolTipText);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.isControlDown()) {
                    Player.getInstance().actions = 5;
                    return;
                }

                if (e.getButton() == MouseEvent.BUTTON1) {
                    Player.getInstance().tryPerformAction(ActionType.TRAVEL, relatedCity);
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    Player.getInstance().tryPerformAction(ActionType.COMPLETE_CURE, relatedCity);
                }
            }
        });
    }

    private City relatedCity;

    public void setRelatedCity(City relatedCity) {
        this.relatedCity = relatedCity;
    }
}
