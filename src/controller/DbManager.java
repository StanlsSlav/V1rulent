package controller;


import model.exception.NotImplementedException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Controlador para linkear la BD
 */
public class DbManager {
    private static DbManager instance;

    public static DbManager getInstance() {
        if (instance == null) {
            instance = new DbManager();
        }

        return instance;
    }

    private final Scanner in = new Scanner(System.in);
    private Connection connection;
    private final String user = "PND_V1RULENT";
    private final String passwd = getPasswdFromUser();

    public DbManager() {
    }

    private String getPasswdFromUser() {
        System.out.print("DB passwd: ");
        return in.nextLine().trim();
    }

    public void connect() {
        String ipConString = "jdbc:oracle:thin:@192.168.3.26:1521:XE";
        String remoteConString = "jdbc:oracle:thin:@oracle.ilerna.com:1521:XE";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(ipConString, user, passwd);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not find the class for JDBC");
        } catch (SQLException e) {
            try {
                connection = DriverManager.getConnection(remoteConString, user, passwd);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

        System.out.println("Connected to DB");
    }

    public void disconnect() {
        try {
            if (connection.isClosed()) {
                return;
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet receiveData() throws NotImplementedException {
        throw new NotImplementedException();
    }

    public ResultSet sendData() throws NotImplementedException {
        throw new NotImplementedException();
    }

    public void modifyData(String field, String value) {
        new NotImplementedException().printStackTrace();
    }

    public void deleteData(String identifier) {
        new NotImplementedException().printStackTrace();
    }
}
