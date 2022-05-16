package model.interfaces;


public interface ICard<T> {
    /**
     * Apply the proper effect to {@code target}
     *
     * @param target The target who'll receive the effect
     * @return True if the effect could be applied; otherwise False
     */
    boolean applyEffect(T target);
}
