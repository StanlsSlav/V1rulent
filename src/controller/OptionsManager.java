package controller;


import model.game.Difficulty;
import model.game.Map;
import model.game.Player;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import view.MainMenu;

import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import java.io.File;

/**
 * Controlador para las configuraciones del juego
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

    @Element(name = "player_name")
    public String playerName;

    @Element(name = "difficulty")
    public Difficulty difficulty;

    @Element(name = "epidemics_threshold")
    public int epidemicsThreshold;

    public int virusesThreshold = Map.getInstance().cities.size() * 3;

    public SpinnerModel epidemicsLimits = new SpinnerNumberModel(1, 1, 15, 1);

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

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
        setDifficulty(dummy.difficulty);
        epidemicsThreshold = dummy.epidemicsThreshold;
    }

    private void createDefaultXmlSettings() {
        File xmlFile = new File("src/assets/settings.xml");

        if (xmlFile.exists() && !xmlFile.delete()) {
            return;
        }

        Serializer serializer = new Persister();
        OptionsManager defaultOptions = new OptionsManager();

        defaultOptions.playerName = "Unknown";
        defaultOptions.difficulty = Difficulty.Easy;
        defaultOptions.epidemicsThreshold = 8;

        try {
            serializer.write(defaultOptions, xmlFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
