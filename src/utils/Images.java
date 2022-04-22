package utils;


import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;


public enum Images {
    MainMenuBg(),
    CreditsMenuBg(),
    GameBg(),
    PauseMenuBg(),
    Logo();

    Images() {
        String extensionlessImagePath = "src/assets/" + this.name();
        File imageFile = new File(extensionlessImagePath + ".png");

        if (imageFile.exists()) {
            image = Toolkit.getDefaultToolkit().createImage(imageFile.getAbsolutePath());
            return;
        }

        image = Toolkit.getDefaultToolkit().createImage(extensionlessImagePath + ".png");
    }

    private final Image image;

    public Image get() {
        return Toolkit.getDefaultToolkit().getImage("src/assets/" + this.name() + ".png");
    }
}
