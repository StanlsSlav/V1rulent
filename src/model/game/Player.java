package model.game;


import controller.GameManager;
import controller.OptionsManager;
import model.ActionType;
import model.Logger;
import view.MainMenu;

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

    public static void resetInstance() {
        instance = null;
        getInstance();
    }

    public String name;
    public int totalActionsPerRound;
    public int actions;
    public City currentCity;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name.trim();

        OptionsManager.getInstance().playerName = getName();
    }

    public void tryPerformAction(ActionType actionType, City target) {
        switch (actionType) {
            case TRAVEL: getInstance().travelTo(target); break;
            case CURE: getInstance().tryRemoveVirus(target); break;
            case COMPLETE_CURE: getInstance().tryCureCity(target); break;
        }

        if (actions < 1) {
            GameManager.getInstance().startNewRound();
        }
    }

    private void tryCureCity(City city) {
        int actionCost = totalActionsPerRound;

        if (city.getTotalViruses() < 1) {
            Logger.getInstance().log("There is no virus in %p", city.getName());
            return;
        }

        if (actions < actionCost) {
            Logger.getInstance().log("You need %p more actions to cure %p",
                  actionCost - actions, city.getName());
            return;
        }

        actions -= actionCost;
        while (city.getTotalViruses() > 0) {
            city.decrementVirusesCount();
        }

        Logger.getInstance().log("Cured " + city.getName());
    }

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
