package model.game;


import controller.GameManager;
import view.MainMenu;

/**
 * Represents the round of the current game
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

    /**
     * Start a new round
     */
    public void initializeNewRound() {
        MainMenu.getInstance().historialTxtArea.append("\nRound " + number + "\n");
        GameManager.getInstance().addNewCityCard();
        number++;
    }
}
