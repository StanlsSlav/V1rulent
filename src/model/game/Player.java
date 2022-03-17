package model.game;


import model.exception.NotImplementedException;

/**
 * El jugador del juego
 */
public class Player {
    public int totalActionsPerRound;
    public int actions;
    public City currentCity;

    public void moveTo(City destination) throws NotImplementedException {
        throw new NotImplementedException();
    }
}
