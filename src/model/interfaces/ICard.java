package model.interfaces;


public interface ICard<T> {
    /**
     * Aplica el efecto adecuado al {@code target}
     *
     * @param target Quien recibirá la bonificación
     * @return True si se ha podido aplicar el efecto; False en caso contrario
     */
    boolean applyEffect(T target);
}
