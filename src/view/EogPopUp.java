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

public class EogPopUp extends JDialog {
    private JPanel contentPane;
    private JButton goToMainMenuBtn;
    private JButton playAgainBtn;
    private JButton exitBtn;
    private JTextArea popUpTxtArea;

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
        setVisible(true);
    }

    private void goToMainMenu() {
        MainMenu.getInstance().creditsBackButton.doClick();
        dispose();
    }

    private void playAgain() {
        MainMenu.getInstance().playButton.doClick();
        dispose();
    }
}
