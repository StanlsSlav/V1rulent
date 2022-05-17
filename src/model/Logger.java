package model;


import model.game.Player;
import view.MainMenu;

/**
 * Custom logger for the game
 *
 * <p>
 * Everything gets logged into the games historial text area
 */
public class Logger {
    private static Logger instance;

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }

        return instance;
    }

    /**
     * Log {@code message} with the proper {@code parameters} and after log the player's left actions
     *
     * <p>
     * The message can be parametrized with the <b>%p</b> tag
     *
     * @param message The message to log
     * @param parameters The parameters to replace the tag with
     */
    public final void log(String message, Object... parameters) {
        log(true, message, parameters);
    }

    /**
     * Log {@code message} with the proper {@code parameters}, if {@code logActionsLeft} is true then it'll also log the
     * player's left actions
     *
     * <p>
     * The message can be parametrized, similarly to {@link String#format(String, Object...)}, with the <b>%p</b> tag
     *
     * @param logActionsLeft Indicate if, after the {@code message}, the player's left actions should be logged
     * @param message The message to log
     * @param parameters The parameters to replace the tag with
     */
    public final void log(boolean logActionsLeft, String message, Object... parameters) {
        for (Object parameter : parameters) {
            message = message.replaceFirst("%p", parameter.toString());
        }

        MainMenu.getInstance().historialTxtArea.append(message + "\n");

        if (logActionsLeft) {
            MainMenu.getInstance().historialTxtArea.append(Player.getInstance().getActions() + " actions left\n\n");
        }
    }
}