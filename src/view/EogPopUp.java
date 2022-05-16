package view;


import controller.DbManager;
import utils.GeneralUtilities;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static utils.GeneralUtilities.switchToCard;

/**
 * Represents the pop-up at the end of the game
 */
public class EogPopUp extends JDialog {
    private JPanel contentPane;
    private JButton goToMainMenuBtn;
    private JButton playAgainBtn;
    private JButton exitBtn;
    private JTextArea popUpTxtArea;

    private boolean isShown = false;

    /**
     * Create a default end of game pop-up
     *
     * @param message The message to write in the pop-up
     * @param hasPlayerLost Indicate whenever the player has lost to save the result in the DB as a lost;
     * otherwise a win will be saved in the DB
     */
    public EogPopUp(String message, boolean hasPlayerLost) {
        setTitle("EndOfGame");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(goToMainMenuBtn);

        popUpTxtArea.setText(message + "\n");
        popUpTxtArea.append("What do you wanna do now?");
        popUpTxtArea.setEnabled(false);
        popUpTxtArea.setFont(popUpTxtArea.getFont().deriveFont(Font.ITALIC, popUpTxtArea.getFont().getSize() + 4));
        popUpTxtArea.setDisabledTextColor(Color.BLACK);

        goToMainMenuBtn.addActionListener(e -> goToMainMenu());
        playAgainBtn.addActionListener(e -> playAgain());
        exitBtn.addMouseListener(GeneralUtilities.exitOnClick);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                playAgain();
            }
        });

        contentPane.registerKeyboardAction(e -> playAgain(),
              KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        String matchResult = hasPlayerLost ? "loss" : "victory";
        DbManager.getInstance().insertNewMatchResult(matchResult);

        pack();
        setResizable(false);
        setVisible(true);
    }

    private void goToMainMenu() {
        switchToCard(MainMenu.getInstance().switcherPanel, "MainMenu");
        switchToCard(MainMenu.getInstance().rootPanel, "MenusPanel");
        dispose();
    }

    private void playAgain() {
        MainMenu.getInstance().initializeNewGame();
        dispose();
    }
}