package model.game;


import model.Location;
import model.base.Colour;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Dictionary;

/**
 * Una ciudad de la mapa
 */
public class City {
    class ToolTip extends JPanel {
        public ToolTip() {
            setPreferredSize(new Dimension(200, 200));
            setToolTipText("");
        }

        @Override
        public void paintComponent(Graphics g) {
            g.setColor(Color.red);
            g.fillRect(0, 0, 100, 200);
            g.setColor(Color.blue);
            g.fillRect(100, 0, 100, 200);
        }

        @Override
        public String getToolTipText(MouseEvent e) {
            // TODO: Continue the tooltip
            return String.format("Name - %s%n", super.getName());
        }

        @Override
        public Point getToolTipLocation(MouseEvent e) {
            Point p = e.getPoint();
            p.y += 15;

            return p;
            // return super.getToolTipLocation(e);
        }
    }

    public City() {
    }

    public City(Location location) {
        setLocation(location);
    }

    private String name;
    private Colour colour;
    private Location location;
    private ArrayList<City> connectedCities;
    private Dictionary<Illness, Integer> presentIllnesses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Colour getColor() {
        return colour;
    }

    public void setColor(Colour colour) {
        this.colour = colour;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ArrayList<City> getConnectedCities() {
        return connectedCities;
    }

    public void setConnectedCities(ArrayList<City> connectedCities) {
        this.connectedCities = connectedCities;
    }

    public Dictionary<Illness, Integer> getPresentIllnesses() {
        return presentIllnesses;
    }

    public void setPresentIllnesses(Dictionary<Illness, Integer> presentIllnesses) {
        this.presentIllnesses = presentIllnesses;
    }

    public void setToolTip() {

    }
}
