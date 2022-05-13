package model.entities;


import java.sql.Date;

/**
 * Representative for the sql table with the game saves
 */
public class GameSave {
    public int id;
    public Date saveDate;
    public PlayerEntity player;
    public String character;
    public Object[] cities;
    public Object[] cards;
    public Object[] cures;
    public int round;
    public String historialText;
    public int totalOutbreaks;
}