package model.game;


import controller.GameManager;
import controller.OptionsManager;
import model.ActionType;
import model.Logger;

/**
 * Represents the user that's currently playing the game
 */
public class Player {
    public static Player instance;

    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }

        return instance;
    }

    public static void resetInstance() {
        instance = null;
        getInstance();
    }

    public Player() {
        this.name = "Unknown";
        this.totalActionsPerRound = 4;
        this.actions = this.totalActionsPerRound;
    }

    private String name;
    public int totalActionsPerRound;
    private int actions;
    public City currentCity;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name.trim();
        OptionsManager.getInstance().setPlayerName(this.name);
    }

    public int getActions() {
        return actions;
    }

    public void setActions(int actions) {
        this.actions = Math.max(Math.min(actions, totalActionsPerRound), 0);
    }

    /**
     * Try performing the action according to the {@code actionType}
     *
     * @param actionType The type of the action to perform
     * @param target Some action types need a city target to interact with
     */
    public void tryPerformAction(ActionType actionType, City target) {
        switch (actionType) {
            case TRAVEL:
                travelTo(target);
                break;
            case CURE:
                tryRemoveVirus(target);
                break;
            case COMPLETE_CURE:
                tryCureCity(target);
                break;
        }

        GameManager.getInstance().checkEndOfGame();

        if (actions < 1) {
            GameManager.getInstance().startNewRound();
        }
    }

    /**
     * Try to cure the {@code city} by removing all the viruses from {@code city}
     *
     * <p>
     * The cost of the action is the {@code totalActionsPerRound} and the {@code city} must have at least a virus to be
     * able to perform the action
     *
     * @param city The city to perform the action against
     */
    private void tryCureCity(City city) {
        int actionCost = totalActionsPerRound;

        if (city.getTotalViruses() < 1) {
            Logger.getInstance().log(false, "There is no virus in %p", city.getName());
            return;
        }

        if (actions < actionCost) {
            Logger.getInstance().log("You need %p more actions to cure %p",
                  actionCost - actions, city.getName());
            return;
        }

        if (GameManager.getInstance().isCureForColourLocked(city.getColour())) {
            Logger.getInstance().log(false,"The cure for the %p colour is locked",
                  city.getColour());
            return;
        }

        actions -= actionCost;
        while (city.getTotalViruses() > 0) {
            city.decrementVirusesCount();
        }

        Logger.getInstance().log("Cured " + city.getName());
    }

    /**
     * Try to cure the {@code city} by removing all the viruses from {@code city}
     *
     * <p>
     * The cost of the action is 1, the {@code city} must have at least a virus to be and the player must be in the
     * {@code city} able to perform the action
     *
     * @param city The city to perform the action against
     */
    private void tryRemoveVirus(City city) {
        int actionCost = 1;
        int totalViruses = city.getTotalViruses();

        if (actions < actionCost) {
            GameManager.getInstance().startNewRound();
            return;
        }

        if (totalViruses < 1) {
            Logger.getInstance().log("There is no virus in %p", city.getName());
            return;
        }

        actions -= actionCost;
        city.decrementVirusesCount();

        Logger.getInstance().log("Removed a virus from %p", city.getName());
        GameManager.getInstance().updateGameState();
    }

    /**
     * Switch the current player's location to {@code city}
     *
     * @param city The city in which to move
     */
    private void travelTo(City city) {
        int actionCost = 1;

        if (currentCity == city) {
            tryRemoveVirus(city);
            return;
        }

        if (actions < actionCost) {
            GameManager.getInstance().startNewRound();
            return;
        }

        actions -= actionCost;
        currentCity = city;
        Logger.getInstance().log("Traveled to %p", city.getName());
    }

    public void refillActions() {
        actions = totalActionsPerRound;
    }
}