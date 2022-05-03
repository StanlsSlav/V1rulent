package model.game;


import controller.GameManager;
import model.Logger;
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

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
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
        if (getTotalViruses() == 3) {
            sendGiftsToNeighbours(this);
            return;
        }

        setTotalViruses(getTotalViruses() + 1);
        GameManager.getInstance().incrementColourVirus(getColour());
    }

    public void decrementVirusesCount() {
        setTotalViruses(getTotalViruses() - 1);
        GameManager.getInstance().decrementColourVirus(getColour());
    }

    private void sendGiftsToNeighbours(City source) {
        Logger.getInstance().log("An epidemic starts from %p", getName());
        GameManager.getInstance().incrementEpidemicsCounter();

        getConnectedCities().forEach(city -> {
            if (city == source) {
                return;
            }

            city.incrementVirusesCount();
        });
    }

    @Override
    public String toString() {
        return String.format("<html>City: %s<br>Viruses: %d</html>",
              getName(), getTotalViruses());
    }
}
