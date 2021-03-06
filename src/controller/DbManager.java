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
 * Controller for the interactions between the game and DB
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
    private Map<String, Class<?>> map;

    private final String user = "PND_V1RULENT";
    private final String passwd = "PASSWD";

    /**
     * Connect to the DB. <i>oracle.ilerna.com</i> respectively.<p>
     * If the connection failed then it'll try switching automatically to the internal <i>192.168.3.26</i>.<p>
     * The resulting connection will be unwrapped to an {@link OracleConnection}
     */
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

    /**
     * Set the DB to Java mapping for the current {@code connection}
     */
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

    /**
     * Disconnect from the DB if there is an active connection
     */
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

    /**
     * Get the top 10 player from the DB
     *
     * @return The names of the top 10 players, formatted as follow "%.10s		%d"
     */
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

    /**
     * Get the latest game saves from the DB based on the current
     *
     * @return The game saves
     */
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

    /**
     * Insert a match result with the currently loaded variables into the DB
     *
     * @param result Indicates if the player won or lost the game/match
     */
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

    /**
     * Save the current state of the game into the DB
     */
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

    /**
     * Try loading the last game save for the current player
     *
     * @return True if loading is successful; otherwise False
     */
    public boolean tryLoadingLastGame() {
        boolean isLoadingSuccessful = true;
        String playerName = Player.getInstance().getName();

        String qry =
              "SELECT id, save_date, player, character, cities, cards, cures, total_outbreaks, round, history " +
                    "FROM recent_saves rs WHERE rs.player.name = ? AND rownum = 1";

        try (PreparedStatement selectLastSave = connection.prepareStatement(qry)) {
            selectLastSave.setString(1, playerName);
            ResultSet result = selectLastSave.executeQuery();

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
        }

        return isLoadingSuccessful;
    }
}