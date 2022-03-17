package model.game;

import java.util.Dictionary;
import java.util.List;

/**
 * Cada vez que el jugador se queda sin acciones, una nueva ronda empezara
 */
public class Round {
    public int number;
    public Dictionary<Integer, List<String>> history;
}
