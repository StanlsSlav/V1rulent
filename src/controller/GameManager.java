package controller;


import model.Logger;
import model.base.Colour;
import model.exception.NotImplementedException;
import model.game.City;
import model.game.GameHistory;
import model.game.GameState;
import model.game.Map;
import model.game.Player;
import model.game.Round;
import utils.Utilities;
import view.EogPopUp;
import view.MainMenu;

import javax.swing.JLabel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class GameManager {
    private static GameManager instance;

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }

        return instance;
    }

    public int roundsCounter;
    public ArrayList<String> actionsHistory;
    public ArrayList<GameHistory> gameHistories;
    public HashMap<Colour, Integer> totalViruses = new HashMap<Colour, Integer>() {{
        put(Colour.Blue, 0);
        put(Colour.Yellow, 0);
        put(Colour.Green, 0);
        put(Colour.Red, 0);
    }};

    public void startNewRound() {
        Round.getInstance().initializeNewRound();
        final int virusesCount = 3;

        ArrayList<City> previouslyPickedCities = new ArrayList<>();

        while (previouslyPickedCities.size() < virusesCount) {
            Colour randomColour = Utilities.getRandomColour();

            Optional<City> toInfect = Map.getInstance().cities.stream()
                  .filter(city -> city == Utilities.getRandomCityForColour(randomColour))
                  .findFirst();

            if (toInfect.isPresent() && !previouslyPickedCities.contains(toInfect.get())) {
                toInfect.get().incrementVirusesCount();
                Logger.getInstance().log(false, "%p was infected, now it has %p viruses",
                      toInfect.get().getName(), toInfect.get().getTotalViruses());

                totalViruses.put(randomColour, totalViruses.get(randomColour) + 1);
                previouslyPickedCities.add(toInfect.get());
            }
        }

        // Deja un espacio entre las ciudades contagiadas y las acciones del usuario
        MainMenu.getInstance().historialTxtArea.append("\n");

        updateGameState();
        Player.getInstance().refillActions();
    }

    public void updateGameState() {
        MainMenu.getInstance().yellowTotalVirusesLbl.setText(String.valueOf(totalViruses.get(Colour.Yellow)));
        MainMenu.getInstance().blueTotalVirusesLbl.setText(String.valueOf(totalViruses.get(Colour.Blue)));
        MainMenu.getInstance().greenTotalVirusesLbl.setText(String.valueOf(totalViruses.get(Colour.Green)));
        MainMenu.getInstance().redTotalVirusesLbl.setText(String.valueOf(totalViruses.get(Colour.Red)));
    }

    public void resetGame() {
        Player.resetInstance();
        Round.resetInstance();

        totalViruses = new HashMap<Colour, Integer>() {{
            put(Colour.Blue, 0);
            put(Colour.Yellow, 0);
            put(Colour.Green, 0);
            put(Colour.Red, 0);
        }};

        updateGameState();

        MainMenu.getInstance().historialTxtArea.setText("");
        startNewRound();
    }

    public void checkEndOfGame() {
        if (hasEpidemiesPassedThreshold()) {
            new EogPopUp("The world has entered in panic. You have lost!");
            return;
        }

        if (anyMoreVirusesLeft()) {
            new EogPopUp("The virus has engulfed the world. You have lost!");
            return;
        }

        if (isNoMoreVirusesLeft()) {
            new EogPopUp("Awesome! The world has been saved!");
        }
    }

    private boolean hasEpidemiesPassedThreshold() {
        int currentEpidemiesCount = Integer.parseInt(MainMenu.getInstance().epidemiesCounterLbl.getText());
        return currentEpidemiesCount >= OptionsManager.getInstance().epidemiesThreshold;
    }

    private boolean anyMoreVirusesLeft() {
        AtomicInteger currentTotalViruses = new AtomicInteger();
        Map.getInstance().cities.forEach(city -> currentTotalViruses.addAndGet(city.getTotalViruses()));

        return currentTotalViruses.get() < OptionsManager.getInstance().virusesThreshold;
    }

    private boolean isNoMoreVirusesLeft() {
        AtomicInteger currentTotalViruses = new AtomicInteger();
        Map.getInstance().cities.forEach(city -> currentTotalViruses.addAndGet(city.getTotalViruses()));

        return currentTotalViruses.get() == 0;
    }

    public void incrementEpidemiesCounter() {
        JLabel epidemiesCounterLbl = MainMenu.getInstance().epidemiesCounterLbl;
        int currentEpidemiesCount = Integer.parseInt(epidemiesCounterLbl.getText());

        epidemiesCounterLbl.setText(currentEpidemiesCount + 1 + "");
    }

    public void saveGame() {
        new NotImplementedException().printStackTrace();
    }
}
