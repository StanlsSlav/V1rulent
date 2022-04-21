package model.game;


import model.Location;

import java.util.ArrayList;
import java.util.Dictionary;


/**
 * Una ciudad de la mapa
 */
public class City {
    public String name;
    public Color color;
    public Location location;
    public ArrayList<City> connectedCities;
    public Dictionary<Illness, Integer> presentIllnesses;

    public enum Color {
        Blue,
        Red,
        Green,
        Yellow
    }
}
