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
    public Dictionary<Color, Integer> totalIllnesses;

    public void updateGameState() throws NotImplementedException {
        throw new NotImplementedException();
    }

    public boolean isPandemyAvoided() throws NotImplementedException {
        throw new NotImplementedException();
    }

    public void saveGame() throws NotImplementedException {
        throw new NotImplementedException();
    }
}
