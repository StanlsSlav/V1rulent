package view;


import utils.Utilities;

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

    public EogPopUp(String message) {
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
        exitBtn.addActionListener(e -> exit());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                playAgain();
            }
        });

        contentPane.registerKeyboardAction(e ->
              playAgain(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
        setVisible(true);
    }

    private void goToMainMenu() {
        MainMenu.getInstance().switchToMainMenu.mouseClicked(null);
        dispose();
    }

    private void playAgain() {
        MainMenu.getInstance().switchToGameStartMenu.mouseClicked(null);
        dispose();
    }

    private void exit() {
        Utilities.exitOnClick.mouseClicked(null);
        dispose();
    }
}
