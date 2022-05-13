package model.game;


import model.interfaces.ICard;

/**
 * Base entity of an card
 *
 * @param <T> Type of the effect to apply to
 */
public class Card<T> implements ICard<T> {
    public String title;
    public String description;

    @Override
    public boolean applyEffect(T target) {
        return false;
    }
}