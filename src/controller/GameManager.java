package controller;


import model.exception.NotImplementedException;
import model.game.GameHistory;
import model.game.GameState;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Dictionary;

public class GameManager {
    public int roundsCounter;
    public GameState gameState;
    public ArrayList<String> actionsHistory;
    public ArrayList<GameHistory> gameHistories;
    public Dictionary<Color, Integer> totalViruses;

    public void updateGameState() {
        new NotImplementedException().printStackTrace();
    }

    public boolean isPandemyAvoided() throws NotImplementedException {
        throw new NotImplementedException();
    }

    public void saveGame() {
        new NotImplementedException().printStackTrace();
    }
}
