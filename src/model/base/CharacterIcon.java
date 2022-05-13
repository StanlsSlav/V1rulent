package model.base;


import utils.GeneralUtilities;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Represents an icon for a character
 */
public class CharacterIcon extends JLabel {
    public CharacterIcon() {
        setSize(433, 270);
        setLocation(1468, 769);
        assignRandomCharacter();
    }

    private String character;

    /**
     * Assign a random character and icon for the current instance
     *
     * <p>
     * The random characters are picked from <i>src/assets/img/characters/</i> directory
     */
    public void assignRandomCharacter() {
        final File BASE_CHAR_DIR = new File("src/assets/img/characters/");
        String[] characterImages = BASE_CHAR_DIR.list();

        if (characterImages != null) {
            setCharacter(characterImages[GeneralUtilities.rand.nextInt(characterImages.length)]
                  .replace(".png", ""));
        }

        String characterImage = String.format("%s\\%s.png", BASE_CHAR_DIR.getPath(), character);

        if (!new File(characterImage).exists()) {
            new FileNotFoundException(characterImage).printStackTrace();
        }

        setIcon(new ImageIcon(characterImage));
    }

    public void setCharacter(String character) {
        this.character = character;
        setIcon(new ImageIcon(String.format("src/assets/img/characters/%s.png", character)));
    }

    public String getCharacter() {
        return character;
    }
}