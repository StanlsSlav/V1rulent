package model.game;


import model.interfaces.ICard;

/**
 * Entidad de una carta base
 *
 * @param <T> El tipo de cuál se aplica el efecto de la carta
 */
public class Card<T> implements ICard<T> {
    public String title;
    public String description;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean applyEffect(T target) {
        return false;
    }
}
