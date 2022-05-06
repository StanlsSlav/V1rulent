package controller;


import model.game.Player;
import model.game.Round;
import oracle.jdbc.OracleConnection;
import utils.DbUtilities;
import utils.GameUtilities;
import view.MainMenu;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

    private OracleConnection connection;
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
            connection = DriverManager.getConnection(ipConString, user, passwd).unwrap(OracleConnection.class);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not find the class for JDBC. Make sure it is added as dependency!");
        } catch (SQLException e) {
            try {
                connection = DriverManager.getConnection(remoteConString, user, passwd).unwrap(OracleConnection.class);
                System.out.println("Connected to DB");
            } catch (SQLException ex) {
                DbUtilities.printSQLException(ex);
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
            System.out.println("Goodbye DB");
        } catch (SQLException e) {
            DbUtilities.printSQLException(e);
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
            DbUtilities.printSQLException(e);
        }

        return top10Players;
    }

    public void insertNewMatchResult(String result) {
        String playerName = Player.getInstance().getName();
        int survivedRounds = Round.getInstance().number;

        String qry =
              "INSERT INTO match_results (player, survived_rounds, result) VALUES (player(?, NULL), ?, ?)";

        try (PreparedStatement insertNewMatchResult = connection.prepareStatement(qry)) {
            connection.setAutoCommit(false);

            insertNewMatchResult.setString(1, playerName);
            insertNewMatchResult.setInt(2, survivedRounds);
            insertNewMatchResult.setString(3, result);

            insertNewMatchResult.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            DbUtilities.printSQLException(e);

            try {
                connection.rollback();
            } catch (SQLException ex) {
                DbUtilities.printSQLException(ex);
            }
        }
    }

    public void saveGame() {
        String playerName = Player.getInstance().getName();
        int actionsLeft = Player.getInstance().actions;
        String character = MainMenu.getInstance().characterIcon.character;
        String cities = GameUtilities.getInstance().getCities();
        String cards = GameUtilities.getInstance().getCards();
        String cures = GameUtilities.getInstance().getCures();
        int totalOutbreaks = Integer.parseInt(MainMenu.getInstance().epidemicsCounterLbl.getText());

        String qry =
              "INSERT INTO game_saves (player, character, cities, cards, cures, total_outbreaks) " +
                    "VALUES (player(?, ?), ?, city_arr(?), cards_arr(?), cures_arr(?), ?)";

        try (PreparedStatement insertGameSave = connection.prepareStatement(qry)) {
            connection.setAutoCommit(false);

            insertGameSave.setString(1, playerName);
            insertGameSave.setInt(2, actionsLeft);
            insertGameSave.setString(3, character);
            insertGameSave.setString(4, cities);
            insertGameSave.setString(5, cards);
            insertGameSave.setString(6, cures);
            insertGameSave.setInt(7, totalOutbreaks);

            insertGameSave.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            DbUtilities.printSQLException(e);

            try {
                connection.rollback();
            } catch (SQLException ex) {
                DbUtilities.printSQLException(ex);
            }
        }
    }
}
