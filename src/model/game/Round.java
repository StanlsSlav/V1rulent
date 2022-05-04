package model.game;

import controller.GameManager;
import view.MainMenu;

import java.util.HashMap;
import java.util.List;

/**
 * Cada vez que el jugador se queda sin acciones, una nueva ronda empezara
 */
public class Round {
    public static Round instance;

    public static Round getInstance() {
        if (instance == null) {
            instance = new Round();
        }

        return instance;
    }

    public static void resetInstance() {
        instance = null;
        getInstance();
    }

    public int number = 1;
    public HashMap<Integer, List<String>> history;

    public void initializeNewRound() {
        MainMenu.getInstance().historialTxtArea.append("\nRound " + number + "\n");
        GameManager.getInstance().addNewCityCard();
        number++;
    }
}
