package model.game;


import controller.GameManager;
import model.Logger;
import model.base.Colour;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Represents the data of an city instance
 *
 * <p>
 * {@link model.base.CityLabel} is its visual representative
 */
public class City {
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

    /**
     * Increment the instance' virus count
     *
     * <p>
     * If it already got 3 viruses then propagate to {@link City#connectedCities} avoiding the source city, this
     */
    public void incrementVirusesCount() {
        if (getTotalViruses() == 3) {
            sendGiftsToNeighbours(this);
            return;
        }

        setTotalViruses(getTotalViruses() + 1);
        GameManager.getInstance().incrementColourVirus(getColour());
    }

    /**
     * Similar to {@link City#incrementVirusesCount()}
     *
     * <p>
     * Decrement the instance' virus count
     */
    public void decrementVirusesCount() {
        setTotalViruses(getTotalViruses() - 1);
        GameManager.getInstance().decrementColourVirus(getColour());
    }

    /**
     * Propagate the virus to the {@link City#connectedCities}
     *
     * @param source The source from where the breakout comes from and ignore it
     */
    private void sendGiftsToNeighbours(City source) {
        Logger.getInstance().log(false, "An epidemic starts from %p", getName());
        GameManager.getInstance().incrementEpidemicsCounter();

        getConnectedCities().forEach(city -> {
            if (city == source) {
                return;
            }

            Logger.getInstance().log(false, "%p got infected", city.name);
            city.incrementVirusesCount();
        });
    }
}