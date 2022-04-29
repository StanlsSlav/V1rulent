package model.game;


import controller.GameManager;

/**
 * Enfermedad del juego
 */
public class Virus {
    public City currentCity;

    public void propagate()  {
        GameManager.getInstance().incrementEpidemicsCounter();
        GameManager.getInstance().checkEndOfGame();
    }
}
