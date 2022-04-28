package model;


import model.game.Player;
import view.MainMenu;

public class Logger {
    private static Logger instance;

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }

        return instance;
    }

    @SafeVarargs
    public final <T> void log(String message, T... parameters) {
        log(true, message, parameters);
    }

    @SafeVarargs
    public final <T> void log(boolean logActionsLeft, String message, T... parameters) {
        for (T parameter : parameters) {
            message = message.replaceFirst("%p", parameter.toString());
        }

        MainMenu.getInstance().historialTxtArea.append(message + "\n");

        if (logActionsLeft) {
            MainMenu.getInstance().historialTxtArea.append(Player.getInstance().actions + " actions left\n\n");
        }
    }
}
