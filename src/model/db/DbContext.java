package model.db;

import model.exception.NotImplementedException;

public class DbContext<T> {
    public String connectionString;

    public T receiveData() throws NotImplementedException {
        throw new NotImplementedException();
    }

    public T sendData() throws NotImplementedException {
        throw new NotImplementedException();
    }

    public void modifyData(T data) throws NotImplementedException {
        throw new NotImplementedException();
    }

    public void deleteData(T data) throws NotImplementedException {
        throw new NotImplementedException();
    }
}
