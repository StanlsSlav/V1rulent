package utils;


import model.base.CityCard;
import model.base.Colour;
import model.base.CureIcon;
import model.game.City;
import model.game.Map;
import view.MainMenu;

/**
 * Utilities used alongside the game
 */
public class GameUtilities {
    private static GameUtilities instance;

    public static GameUtilities getInstance() {
        if (instance == null) {
            instance = new GameUtilities();
        }

        return instance;
    }

    /**
     * Create an SQL array of cards
     *
     * @return The SQL variant of, a first level, varray of cards
     */
    public String getCards() {
        StringBuilder cardsArrayString = new StringBuilder();

        for (int i = 0; i < MainMenu.getInstance().cardsLbls.size(); i++) {
            CityCard cardLbl = (CityCard) MainMenu.getInstance().cardsLbls.get(i);
            Colour cardColour = cardLbl.getColour();

            if (cardColour == null) {
                cardsArrayString.append("NULL").append(", ");
                continue;
            }

            cardsArrayString.append(String.format("'%s'", cardColour.name())).append(", ");
        }

        return cardsArrayString.substring(0, cardsArrayString.length() - 2);
    }

    /**
     * Create an SQL array of cure states
     *
     * <p>
     * 1 if the cure is unlocked; otherwise 0
     *
     * @return The SQL variant of, a first level, varray of cures
     */
    public String getCures() {
        StringBuilder curesArrayString = new StringBuilder();

        CureIcon yellowCure = (CureIcon) MainMenu.getInstance().yellowCureIcon;
        CureIcon redCure = (CureIcon) MainMenu.getInstance().redCureIcon;
        CureIcon blueCure = (CureIcon) MainMenu.getInstance().blueCureIcon;
        CureIcon greenCure = (CureIcon) MainMenu.getInstance().greenCureIcon;

        curesArrayString.append(yellowCure.isUnlocked() ? 1 : 0).append(", ");
        curesArrayString.append(redCure.isUnlocked() ? 1 : 0).append(", ");
        curesArrayString.append(blueCure.isUnlocked() ? 1 : 0).append(", ");
        curesArrayString.append(greenCure.isUnlocked() ? 1 : 0);

        return curesArrayString.toString();
    }

    /**
     * Create an SQL array of city objects
     *
     * @return The SQL variant of, a first level, varray of city objects
     */
    public String getCities() {
        StringBuilder cityArrayString = new StringBuilder();
        int citiesSize = Map.getInstance().cities.size();

        for (int i = 0; i < citiesSize; i++) {
            City city = Map.getInstance().getCities().get(i);

            if (i != citiesSize - 1) {
                cityArrayString.append(String.format("city('%s', %d), ", city.getName(), city.getTotalViruses()));
                continue;
            }

            // Last item in the array must not end with a comma ','
            cityArrayString.append(String.format("city('%s', %d)", city.getName(), city.getTotalViruses()));
        }

        return cityArrayString.substring(0, cityArrayString.length());
    }
}