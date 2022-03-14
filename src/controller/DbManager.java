package controller;


import model.db.DbContext;
import model.exception.NotImplementedException;


public class DbManager<T> {
    public void connect(DbContext<T> dbContext) throws NotImplementedException {
        throw new NotImplementedException();
    }

    public void disconnect(DbContext<T> dbContext) throws NotImplementedException {
        throw new NotImplementedException();
    }
}
