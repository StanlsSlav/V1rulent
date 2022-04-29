package controller;


import model.exception.NotImplementedException;
import model.game.Difficulty;
import model.game.Map;

/**
 * Controlador para los niveles de dificultad
 */
public class OptionsManager {
    private static OptionsManager instance;

    public static OptionsManager getInstance() {
        if (instance == null) {
            instance = new OptionsManager();
        }

        return instance;
    }

    public Difficulty difficulty;
    public int epidemicsThreshold = 8;
    public int virusesThreshold = Map.getInstance().cities.size() * 3;

    public void changeDifficulty(Difficulty difficulty) {
        new NotImplementedException().printStackTrace();
    }
}
