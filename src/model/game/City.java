package model.game;


import model.Position;

import java.util.ArrayList;
import java.util.Dictionary;


public class City {
    public String name;
    public Position position;
    public ArrayList<City> connectedCities;
    public Dictionary<Illness, Integer> presentIllnesses;
    public int totalPopulation;
}
