package controller;


import model.game.City;
import model.game.Map;
import model.game.Player;
import model.game.Round;
import oracle.sql.ARRAY;
import utils.DbUtilities;
import view.MainMenu;

import java.sql.*;
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
    private final String passwd = "IBAE123";

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
            throw new RuntimeException("Could not find the class for JDBC. Make sure it is added as dependency!");
        } catch (SQLException e) {
            try {
                connection = DriverManager.getConnection(remoteConString, user, passwd);
                System.out.println("Connected to DB");
            } catch (SQLException ex) {
                DbUtilities.printSQLException(ex);
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

            insertNewMatchResult.setString(0, playerName);
            insertNewMatchResult.setInt(1, survivedRounds);
            insertNewMatchResult.setString(2, result);

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
        Array cities;
        Array cards;
        Array cures;
        int totalOutbreaks = Integer.parseInt(MainMenu.getInstance().epidemicsCounterLbl.getText());

        String qry =
              "INSERT INTO game_saves (player, character, cities, cards, cures, total_outbreaks) " +
                    "VALUES (player(?, ?), ?, ?, ?, ?, ?)";

        try (PreparedStatement insertGameSave = connection.prepareStatement(qry)) {
            connection.setAutoCommit(false);

            // TODO: Complete GameUtilities and make sure the arrays get created accordingly
            cities = connection.createArrayOf("city", Map.getInstance().cities.toArray());
            cards = connection.createArrayOf("varchar2(6)", GameUtilities.getInstance().getCards());
            cures = connection.createArrayOf("number", GameUtilities.getInstance().getCures());

            insertGameSave.setString(0, playerName);
            insertGameSave.setInt(1, actionsLeft);
            insertGameSave.setString(2, character);
            insertGameSave.setArray(3, cities);
            insertGameSave.setArray(4, cards);
            insertGameSave.setArray(5, cures);
            insertGameSave.setInt(6, totalOutbreaks);

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
