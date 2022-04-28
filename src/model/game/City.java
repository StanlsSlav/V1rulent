package model.game;


import model.base.Colour;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Una ciudad de la mapa
 */
public class City {
    public City() {
    }

    private String name;
    private Colour colour;
    private Point point;
    private ArrayList<City> connectedCities;
    private int totalViruses;

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

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public ArrayList<City> getConnectedCities() {
        return connectedCities;
    }

    public void setConnectedCities(ArrayList<City> connectedCities) {
        this.connectedCities = connectedCities;
    }

    public int getTotalViruses() {
        return totalViruses;
    }

    public void setTotalViruses(int totalViruses) {
        this.totalViruses = totalViruses;
    }

    public void incrementVirusesCount() {
        setTotalViruses(getTotalViruses() + 1);
    }

    public void decrementVirusesCount() {
        setTotalViruses(getTotalViruses() - 1);
    }

    @Override
    public String toString() {
        return String.format("<html>City: %s<br>%s Viruses: %d</html>",
              getName(), getColor().name(), getTotalViruses());
    }
}
