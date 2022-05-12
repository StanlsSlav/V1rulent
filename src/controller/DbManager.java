package controller;


import model.entities.GameSave;
import model.entities.PlayerEntity;
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
import java.util.Map;

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
    Map<String, Class<?>> map;

    private final String user = "PND_V1RULENT";
    private final String passwd = "PASSWD";

    public void connect() {
        String ipConString = "jdbc:oracle:thin:@192.168.3.26:1521:XE";
        String remoteConString = "jdbc:oracle:thin:@oracle.ilerna.com:1521:XE";

        DriverManager.setLoginTimeout(1);

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(ipConString, user, passwd).unwrap(OracleConnection.class);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            try {
                connection = DriverManager.getConnection(remoteConString, user, passwd).unwrap(OracleConnection.class);
            } catch (SQLException ex) {
                DbUtilities.printSQLException(ex);
            }
        }

        loadObjectMapping();

        System.out.println("Connected to DB");
    }

    public void loadObjectMapping() {
        try {
            map = connection.getTypeMap();
            map.put("CITY", Class.forName("model.entities.CityEntity"));
            map.put("PLAYER", Class.forName("model.entities.PlayerEntity"));
            connection.setTypeMap(map);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
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

    public ArrayList<GameSave> getGameSaves() {
        String playerName = Player.getInstance().getName();
        ArrayList<GameSave> gameSaves = new ArrayList<>();

        String qry =
              "SELECT id, save_date, player, character, cities, cards, cures, total_outbreaks, round, history " +
                    "FROM recent_saves rs WHERE rs.player.name = ?";

        try (PreparedStatement selectLastSave = connection.prepareStatement(qry)) {
            selectLastSave.setString(1, playerName);
            ResultSet result = selectLastSave.executeQuery();

            while (result.next()) {
                GameSave save = new GameSave();

                save.id = result.getInt("id");
                save.saveDate = result.getDate("save_date");
                save.player = (PlayerEntity) result.getObject("player");
                save.character = result.getString("character");
                save.cities = (Object[]) result.getArray("cities").getArray(map);
                save.cards = (Object[]) result.getArray("cards").getArray(map);
                save.cures = (Object[]) result.getArray("cures").getArray(map);
                save.totalOutbreaks = result.getInt("total_outbreaks");
                save.round = result.getInt("round");
                save.historialText = result.getString("history");

                gameSaves.add(save);
            }
        } catch (SQLException e) {
            DbUtilities.printSQLException(e);
        }

        return gameSaves;
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
        int actionsLeft = Player.getInstance().getActions();
        String character = MainMenu.getInstance().characterIcon.getCharacter();
        String cities = GameUtilities.getInstance().getCities();
        String cards = GameUtilities.getInstance().getCards();
        String cures = GameUtilities.getInstance().getCures();
        int totalOutbreaks = Integer.parseInt(MainMenu.getInstance().epidemicsCounterLbl.getText());
        int round = Round.getInstance().number;
        String history = MainMenu.getInstance().historialTxtArea.getText();

        String qry = String.format(
              "INSERT INTO game_saves (player, character, cities, cards, cures, total_outbreaks, round, history) VALUES " +
                    "(player('%s', %d), '%s', city_arr(%s), cards_arr(%s), cures_arr(%s), %d, %d, '%s')",
              playerName, actionsLeft, character, cities, cards, cures, totalOutbreaks, round, history);

        try (Statement insertGameSave = connection.createStatement()) {
            connection.setAutoCommit(false);
            insertGameSave.executeUpdate(qry);
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

    public boolean tryLoadingLastGame() {
        boolean isLoadingSuccessful = true;
        String playerName = Player.getInstance().getName();

        String qry =
              "SELECT id, save_date, player, character, cities, cards, cures, total_outbreaks, round, history " +
                    "FROM recent_saves rs WHERE rs.player.name = ? AND rownum = 1";

        try (PreparedStatement selectLastSave = connection.prepareStatement(qry)) {
            Map<String, Class<?>> map = connection.getTypeMap();
            map.put("CITY", Class.forName("model.entities.CityEntity"));
            map.put("PLAYER", Class.forName("model.entities.PlayerEntity"));
            connection.setTypeMap(map);

            selectLastSave.setString(1, playerName);
            ResultSet result = selectLastSave.executeQuery(qry);

            GameSave save = new GameSave();

            while (result.next()) {
                save.id = result.getInt("id");
                save.saveDate = result.getDate("save_date");
                save.player = (PlayerEntity) result.getObject("player");
                save.character = result.getString("character");
                save.cities = (Object[]) result.getArray("cities").getArray(map);
                save.cards = (Object[]) result.getArray("cards").getArray(map);
                save.cures = (Object[]) result.getArray("cures").getArray(map);
                save.totalOutbreaks = result.getInt("total_outbreaks");
                save.round = result.getInt("round");
                save.historialText = result.getString("history");
            }

            GameManager.getInstance().loadSave(save);
        } catch (SQLException e) {
            DbUtilities.printSQLException(e);
            isLoadingSuccessful = false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return isLoadingSuccessful;
    }
}