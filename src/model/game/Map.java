package model.game;


import java.util.ArrayList;

import static utils.Utilities.loadCities;

/**
 * Representante del mapa del mundo (juego)
 */
public class Map {
    private static Map instance;

    public static Map getInstance() {
        if (null == instance) {
            instance = new Map();
        }

        return instance;
    }

    public Map() {
        if (instance.cities.size() == 0) {
            loadCities();
        }
    }

    public ArrayList<City> cities;

    public static int citiesBetween(City startingPoint, City destination) throws Exception {
        throw new Exception();
    }
}
