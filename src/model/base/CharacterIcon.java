package model.base;


import utils.Utilities;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.io.File;
import java.io.FileNotFoundException;


public class CharacterIcon extends JLabel {
    public CharacterIcon() {
        setSize(433, 270);
        setLocation(1468, 769);
        assignRandomCharacter();
    }

    public void assignRandomCharacter() {
        final File BASE_CHAR_DIR = new File("src/assets/img/characters/");
        String[] characterImages = BASE_CHAR_DIR.list();

        assert characterImages != null;
        String characterImage = String.format("%s\\%s",
              BASE_CHAR_DIR.getPath(), characterImages[Utilities.rand.nextInt(characterImages.length)]);

        if (!new File(characterImage).exists()) {
            new FileNotFoundException(characterImage).printStackTrace();
        }

        setIcon(new ImageIcon(characterImage));
    }
}
