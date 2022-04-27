package model.game;


import model.ActionType;
import model.exception.NotImplementedException;

/**
 * El jugador del juego
 */
public class Player {
    public static Player instance;

    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }

        return instance;
    }

    public int totalActionsPerRound = 2;
    public int actions = totalActionsPerRound;
    public City currentCity;

    public static boolean tryPerformAction(ActionType actionType) {
        int actionCost = 1;

        if (actionType == ActionType.COMPLETE_CURE) {
            actionCost = instance.totalActionsPerRound;
        }

        if (instance.actions >= actionCost) {
            instance.actions -= actionCost;
            return true;
        }

        new Exception("Action could not be performed").printStackTrace();
        return false;
    }

    public void moveTo(City destination) throws NotImplementedException {
        throw new NotImplementedException();
    }
}
