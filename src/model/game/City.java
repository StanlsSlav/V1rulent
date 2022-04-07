package model.game;


import model.Position;

import java.util.ArrayList;
import java.util.Dictionary;


/**
 * Una ciudad de la mapa
 */
public class City {
    public String name;
    public Color color;
    public Position position;
    public ArrayList<City> connectedCities;
    public Dictionary<Illness, Integer> presentIllnesses;
    public int totalPopulation;

    public enum Color {
        Blue,
        Red,
        Green,
        Yellow
    }
}
