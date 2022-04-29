package controller;


import model.game.Difficulty;
import model.game.Map;
import model.game.Player;

import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

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

    public String playerName = Player.getInstance().name;
    public Difficulty difficulty;
    public int epidemicsThreshold = 8;
    public int virusesThreshold = Map.getInstance().cities.size() * 3;

    public SpinnerModel epidemicsLimits = new SpinnerNumberModel(1, 1, 15, 1);

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
