package model.db;


import model.exception.NotImplementedException;

import java.sql.ResultSet;

/**
 * Contenedor de una conexión a una BD
 */
public class DbContext {
    public String connectionString;

    public ResultSet receiveData() throws NotImplementedException {
        throw new NotImplementedException();
    }

    public ResultSet sendData() throws NotImplementedException {
        throw new NotImplementedException();
    }

    public void modifyData(String field, String value)  {
        new NotImplementedException().printStackTrace();
    }

    public void deleteData(String identifier)  {
        new NotImplementedException().printStackTrace();
    }
}
