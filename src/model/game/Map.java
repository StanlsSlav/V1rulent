package model.game;


import java.util.ArrayList;

/**
 * Representante del mapa del mundo (juego)
 */
public class Map {
    public static Map instance;

    public static Map getInstance() {
        if (null == instance) {
            instance = new Map();
        }

        return instance;
    }

    public ArrayList<City> cities;

    public static int citiesBetween(City startingPoint, City destination) throws Exception {
        throw new Exception();
    }
}
