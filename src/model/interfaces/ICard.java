package model.interfaces;


import model.exception.NotImplementedException;


public interface ICard<T> {
    boolean applyEffect(T target) throws NotImplementedException;
}
