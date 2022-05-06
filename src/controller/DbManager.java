package controller;


import model.game.Player;
import model.game.Round;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Controlador para la BD
 */
public class DbManager {
    private static DbManager instance;

    public static DbManager getInstance() {
        if (instance == null) {
            instance = new DbManager();
            instance.connect();
        }

        return instance;
    }

    private Connection connection;
    private final String user = "PND_V1RULENT";
    private final String passwd = "PASSWD";

    public DbManager() {
    }

    public void connect() {
        String ipConString = "jdbc:oracle:thin:@192.168.3.26:1521:XE";
        String remoteConString = "jdbc:oracle:thin:@oracle.ilerna.com:1521:XE";

        DriverManager.setLoginTimeout(1);

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(ipConString, user, passwd);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not find the class for JDBC");
        } catch (SQLException e) {
            try {
                connection = DriverManager.getConnection(remoteConString, user, passwd);
                System.out.println("Connected to DB");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void disconnect() {
        try {
            if (connection.isClosed()) {
                return;
            }

            connection.close();
            System.out.println("Goodbye DB");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getTop10Players() {
        ArrayList<String> top10Players = new ArrayList<>();
        Statement statement;

        String qry = "SELECT player_name, points FROM leaderboard WHERE rownum <= 10";
        ResultSet result;

        try {
            statement = connection.createStatement();
            result = statement.executeQuery(qry);

            while (result.next()) {
                String formattedResult = String.format("%.10s\t\t%d",
                      result.getString("player_name"), result.getInt("points"));

                top10Players.add(formattedResult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return top10Players;
    }

    public void insertNewMatchResult(String result) {
        String playerName = Player.getInstance().getName();
        int survivedRounds = Round.getInstance().number;

        Statement statement;
        String qry = String.format(
              "INSERT INTO match_results (player, survived_rounds, result) VALUES (player('%s', NULL), %d, '%s')",
              playerName, survivedRounds, result);

        try {
            statement = connection.createStatement();
            statement.executeQuery(qry);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
