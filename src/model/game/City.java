package model.game;


import model.Location;

import java.util.ArrayList;
import java.util.Dictionary;

/**
 * Una ciudad de la mapa
 */
public class City {
    public enum Color {
        Blue,
        Red,
        Green,
        Yellow
    }

    public City() {
    }

    public City(Location location) {
        setLocation(location);
    }

    private String name;
    private Color color;
    private Location location;
    private ArrayList<City> connectedCities;
    private Dictionary<Illness, Integer> presentIllnesses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
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
}
