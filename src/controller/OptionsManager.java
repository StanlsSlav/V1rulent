package controller;


import model.exception.NotImplementedException;
import model.game.Difficulty;

/**
 * Controlador para los niveles de dificultad
 */
public class OptionsManager {
    public Difficulty difficulty;

    public void changeDifficulty(Difficulty difficulty)  {
        new NotImplementedException().printStackTrace();
    }
}
