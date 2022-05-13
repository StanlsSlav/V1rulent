package controller;


import model.game.Difficulty;
import model.game.Map;
import model.game.Player;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import javax.swing.*;
import java.io.File;

/**
 * Controller for the settings game
 */
@Root(name = "config")
public class OptionsManager {
    private static OptionsManager instance;

    public static OptionsManager getInstance() {
        if (instance == null) {
            instance = new OptionsManager();
            instance.loadSettingsFromXml();
        }

        return instance;
    }

    public OptionsManager() {
        this.virusesThreshold = Map.getInstance().getCities().size() * 3;
    }

    @Element(name = "player_name")
    public String playerName;

    @Element(name = "difficulty")
    public Difficulty difficulty;

    @Element(name = "epidemics_threshold")
    public int epidemicsThreshold;

    private int virusesThreshold;

    public SpinnerModel epidemicsLimits = new SpinnerNumberModel(1, 1, 15, 1);

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName.trim();
    }

    public void setEpidemicsThreshold(int epidemicsThreshold) {
        this.epidemicsThreshold = epidemicsThreshold;
    }

    public int getVirusesThreshold() {
        return Map.getInstance().getCities().size() * 3;
    }

    /**
     * Load the player name, difficulty and epidemics threshold from the settings file, if it does not exist then it
     * creates a new one with the default values, check {@link OptionsManager#createDefaultXmlSettings()}
     *
     * <p>
     * If the serialization failed then an exception will be printed, but not thrown!
     */
    public void loadSettingsFromXml() {
        File xmlFile = new File("src/assets/settings.xml");

        if (!xmlFile.exists()) {
            createDefaultXmlSettings();
        }

        Serializer serializer = new Persister();
        OptionsManager dummy;

        try {
            dummy = serializer.read(OptionsManager.class, xmlFile);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Player.getInstance().setName(dummy.playerName);
        setPlayerName(dummy.playerName);
        setDifficulty(dummy.difficulty);
        epidemicsThreshold = dummy.epidemicsThreshold;

        switch (difficulty) {
            default:
                break;
            case Medium:
                epidemicsThreshold = epidemicsThreshold - 2;
                break;
            case Hard:
                epidemicsThreshold = epidemicsThreshold - 4;
            case Extreme:
                epidemicsThreshold = epidemicsThreshold - 6;
        }
    }

    /**
     * Create a default settings file
     *
     * <p>
     * If the deserialization failed then an exception will be printed, not thrown!
     */
    private void createDefaultXmlSettings() {
        File xmlFile = new File("src/assets/settings.xml");
        Serializer serializer = new Persister();
        OptionsManager defaultOptions = new OptionsManager();

        defaultOptions.playerName = Player.getInstance().getName();
        defaultOptions.difficulty = Difficulty.Easy;
        defaultOptions.epidemicsThreshold = 8;

        try {
            serializer.write(defaultOptions, xmlFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Save the current settings to the according file
     */
    public void saveSettings() {
        File xmlFile = new File("src/assets/settings.xml");

        if (xmlFile.exists() && !xmlFile.delete()) {
            return;
        }

        Serializer serializer = new Persister();
        try {
            serializer.write(getInstance(), xmlFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
