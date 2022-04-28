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
import view.MainMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class GameManager {
    private static GameManager instance;

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }

        return instance;
    }

    public int roundsCounter;
    public GameState gameState;
    public ArrayList<String> actionsHistory;
    public ArrayList<GameHistory> gameHistories;
    public HashMap<Colour, Integer> totalViruses = new HashMap<>() {{
        put(Colour.Blue, 0);
        put(Colour.Yellow, 0);
        put(Colour.Green, 0);
        put(Colour.Red, 0);
    }};

    public void startNewRound() {
        Round.getInstance().initializeNewRound();
        int virusesCount = 3;

        for (int i = 0; i < virusesCount; i++) {
            Colour randomColour = Utilities.getRandomColour();

            Optional<City> toInfect = Map.getInstance().cities.stream()
                  .filter(city -> city == Utilities.getRandomCityForColour(randomColour))
                  .findFirst();

            toInfect.ifPresent(city -> {
                city.incrementVirusesCount();
                Logger.getInstance().log(false, "%p was infected, now it has %p viruses",
                      city.getName(), city.getTotalViruses());

                totalViruses.put(randomColour, totalViruses.get(randomColour) + 1);
            });
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

    public boolean isPandemyAvoided() throws NotImplementedException {
        throw new NotImplementedException();
    }

    public void saveGame() {
        new NotImplementedException().printStackTrace();
    }
}
