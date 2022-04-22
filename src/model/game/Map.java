package model.game;


import java.util.ArrayList;


/**
 * Representante del mapa del mundo (juego)
 */
public class Map {
    private static Map instance;

    public static Map getInstance() {
        if (instance == null) {
            instance = new Map();
        }

        return instance;
    }

    private Map() {
    }

    public ArrayList<City> cities = new ArrayList<>();

    public static int citiesBetween(City startingPoint, City destination) throws Exception {
        throw new Exception();
    }
}
