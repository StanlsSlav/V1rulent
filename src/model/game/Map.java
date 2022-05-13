package model.game;


import utils.GeneralUtilities;

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

    public ArrayList<City> cities;

    public ArrayList<City> getCities() {
        if (cities == null) {
            GeneralUtilities.loadCities();
        }

        return cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }
}