package model.game;


import model.exception.NotImplementedException;
import model.interfaces.ICard;


public class Card<T> implements ICard<T> {
    public String title;
    public String description;

    @Override
    public boolean applyEffect(T target) throws NotImplementedException {
        throw new NotImplementedException();
    }
}
