package controller;


import model.Logger;
import model.base.CityCard;
import model.base.Colour;
import model.base.CureIcon;
import model.entities.CityEntity;
import model.entities.GameSave;
import model.game.City;
import model.game.Map;
import model.game.Player;
import model.game.Round;
import utils.GeneralUtilities;
import view.EogPopUp;
import view.MainMenu;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

/**
 * Game controller
 */
public class GameManager {
    private static GameManager instance;

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }

        return instance;
    }

    public HashMap<Colour, Integer> totalViruses = new HashMap<Colour, Integer>() {{
        put(Colour.Blue, 0);
        put(Colour.Yellow, 0);
        put(Colour.Green, 0);
        put(Colour.Red, 0);
    }};

    /**
     * Perform the needed actions to start a new round and log it into the historial text
     */
    public void startNewRound() {
        Round.getInstance().initializeNewRound();
        final int virusesCount = 3;

        ArrayList<City> previouslyPickedCities = new ArrayList<>();

        while (previouslyPickedCities.size() < virusesCount) {
            Colour randomColour = GeneralUtilities.getRandomColour();

            Optional<City> toInfect = Map.getInstance().getCities().stream()
                  .filter(city -> city == GeneralUtilities.getRandomCityForColour(randomColour))
                  .findFirst();

            if (toInfect.isPresent() && !previouslyPickedCities.contains(toInfect.get())) {
                toInfect.get().incrementVirusesCount();
                Logger.getInstance().log(false, "%p was infected, now it has %p viruses",
                      toInfect.get().getName(), toInfect.get().getTotalViruses());

                previouslyPickedCities.add(toInfect.get());
            }
        }

        // Put a space between the infected cities and user's actions
        MainMenu.getInstance().historialTxtArea.append("\n");

        updateGameState();
        Player.getInstance().refillActions();
    }

    /**
     * Update the visual values of the total viruses with the values from {@code totalViruses}
     */
    public void updateGameState() {
        SwingUtilities.invokeLater(() -> {
            MainMenu.getInstance().yellowTotalVirusesLbl.setText(String.valueOf(totalViruses.get(Colour.Yellow)));
            MainMenu.getInstance().blueTotalVirusesLbl.setText(String.valueOf(totalViruses.get(Colour.Blue)));
            MainMenu.getInstance().greenTotalVirusesLbl.setText(String.valueOf(totalViruses.get(Colour.Green)));
            MainMenu.getInstance().redTotalVirusesLbl.setText(String.valueOf(totalViruses.get(Colour.Red)));
        });
    }

    /**
     * Reset the variables of the game (player, the rounds, total viruses/infections, ...) to their default
     */
    public void resetGame() {
        Player.resetInstance();
        Round.resetInstance();

        ((CureIcon) MainMenu.getInstance().yellowCureIcon).lock();
        ((CureIcon) MainMenu.getInstance().blueCureIcon).lock();
        ((CureIcon) MainMenu.getInstance().greenCureIcon).lock();
        ((CureIcon) MainMenu.getInstance().redCureIcon).lock();

        totalViruses = new HashMap<Colour, Integer>() {{
            put(Colour.Blue, 0);
            put(Colour.Yellow, 0);
            put(Colour.Green, 0);
            put(Colour.Red, 0);
        }};

        updateGameState();

        for (JLabel lbl : MainMenu.getInstance().cardsLbls) {
            CityCard cityCard = (CityCard) lbl;
            cityCard.reset();
        }

        MainMenu.getInstance().historialTxtArea.setText("");
        startNewRound();
    }

    /**
     * Check for end of game cases
     *
     * <p>
     * The player losses if
     * <ul>
     *     <li>The epidemics passed the set threshold, check {@link GameManager#hasEpidemicsPassedThreshold()}</li>
     *     <li>The viruses passed the set threshold, check {@link GameManager#haveVirusesPassedThreshold()}</li>
     * </ul>
     *
     * <p>
     * The player wins if
     * <ul>
     *     <li>There's no virus left to cure, check {@link GameManager#isNoMoreVirusesLeft()}</li>
     * </ul>
     */
    public void checkEndOfGame() {
        if (isNoMoreVirusesLeft()) {
            new EogPopUp("Awesome! The world has been saved!", false);
            return;
        }

        if (hasEpidemicsPassedThreshold()) {
            new EogPopUp("The world has entered in panic. You have lost!", true);
            return;
        }

        if (haveVirusesPassedThreshold()) {
            new EogPopUp("The virus has engulfed the world. You have lost!", true);
        }
    }

    /**
     * Determine if the current epidemics count passed over the set threshold
     *
     * @return True if current epidemics count did pass over the threshold; otherwise False
     */
    private boolean hasEpidemicsPassedThreshold() {
        int currentEpidemicsCount = Integer.parseInt(MainMenu.getInstance().epidemicsCounterLbl.getText());
        return currentEpidemicsCount >= OptionsManager.getInstance().epidemicsThreshold;
    }

    /**
     * Determine if the current viruses count passed over the set threshold
     *
     * @return True if current viruses count did pass over the threshold; otherwise False
     */
    private boolean haveVirusesPassedThreshold() {
        return totalViruses.values()
              .stream()
              .mapToInt(val -> val)
              .sum() > OptionsManager.getInstance().getVirusesThreshold();
    }

    /**
     * Determine if there's no more viruses left to cure
     *
     * @return True if there's no more viruses to cure; otherwise False
     */
    private boolean isNoMoreVirusesLeft() {
        return totalViruses.values()
              .stream()
              .mapToInt(val -> val)
              .sum() == 0;
    }

    /**
     * Increment the visual value of the epidemics counter
     */
    public void incrementEpidemicsCounter() {
        JLabel epidemicsCounterLbl = MainMenu.getInstance().epidemicsCounterLbl;
        int currentEpidemicsCount = Integer.parseInt(epidemicsCounterLbl.getText());

        MainMenu.getInstance().epidemicsCounterLbl.setText(currentEpidemicsCount + 1 + "");
    }

    /**
     * Increment the virus count of a certain {@code colour} and updates the visual values of it
     *
     * @param colour The virus colour to increment
     */
    public void incrementColourVirus(Colour colour) {
        totalViruses.put(colour, totalViruses.get(colour) + 1);
        updateGameState();
    }

    /**
     * Does the oposite of {@link GameManager#incrementColourVirus(Colour)}
     *
     * <p>
     * Decrement the virus count of a certain {@code colour} and updates the visual values of it
     *
     * @param colour The virus colour to decrement
     */
    public void decrementColourVirus(Colour colour) {
        totalViruses.put(colour, totalViruses.get(colour) - 1);
        updateGameState();
    }

    /**
     * Add a new city card
     */
    public void addNewCityCard() {
        ArrayList<JLabel> cardsLbls = MainMenu.getInstance().cardsLbls;
        int totalCards = cardsLbls.size();

        ArrayList<Colour> coloursToAvoid = getColoursToAvoid();
        Colour cardColour;

        do {
            cardColour = GeneralUtilities.getRandomColour();
        } while (coloursToAvoid.contains(cardColour));

        for (int i = 0; i < totalCards; i++) {
            CityCard card = (CityCard) cardsLbls.get(i);

            if (card.getColour() == null) {
                card.setColour(cardColour);
                break;
            }

            if (i == totalCards - 1) {
                int randomPosition = GeneralUtilities.rand.nextInt(totalCards);
                CityCard randomCity = ((CityCard) cardsLbls.get(randomPosition));

                randomCity.setColour(cardColour);
            }
        }

        checkCardColour(cardColour);
    }

    /**
     * Get colours to avoid when drawing a new city card
     *
     * <p>
     * When a colour gets unlocked, it must be avoided to randomly draw
     *
     * @return A list defining which colours to avoid
     */
    public ArrayList<Colour> getColoursToAvoid() {
        ArrayList<Colour> coloursToAvoid = new ArrayList<>();

        if (((CureIcon) MainMenu.getInstance().blueCureIcon).isUnlocked()) {
            coloursToAvoid.add(Colour.Blue);
        }

        if (((CureIcon) MainMenu.getInstance().yellowCureIcon).isUnlocked()) {
            coloursToAvoid.add(Colour.Yellow);
        }

        if (((CureIcon) MainMenu.getInstance().greenCureIcon).isUnlocked()) {
            coloursToAvoid.add(Colour.Green);
        }

        if (((CureIcon) MainMenu.getInstance().redCureIcon).isUnlocked()) {
            coloursToAvoid.add(Colour.Red);
        }

        return coloursToAvoid;
    }

    /**
     * Check the status of a colour to determine if it should be unlocked or not
     *
     * @param colour The colour to check
     */
    public void checkCardColour(Colour colour) {
        ArrayList<CityCard> cityCards = new ArrayList<>();
        ArrayList<CityCard> foundCards = new ArrayList<>();

        for (JLabel cardLbl : MainMenu.getInstance().cardsLbls) {
            cityCards.add((CityCard) cardLbl);
        }

        for (CityCard cityCard : cityCards) {
            if (cityCard.getColour() == colour) {
                foundCards.add(cityCard);
            }
        }

        if (foundCards.size() == 4) {
            unlockCure(foundCards, colour);
        }
    }

    /**
     * Unlock the cure with the colour defined by {@code cureColourToUnlock} and remove the {@code cardsToConsume} from
     * the player's hand
     *
     * @param cardsToConsume The card to remove from player's hand
     * @param cureColourToUnlock The cure colour to unlock
     */
    private void unlockCure(ArrayList<CityCard> cardsToConsume, Colour cureColourToUnlock) {
        for (CityCard card : cardsToConsume) {
            card.reset();
        }

        switch (cureColourToUnlock) {
            case Blue:
                ((CureIcon) MainMenu.getInstance().blueCureIcon).unlock();
                break;
            case Red:
                ((CureIcon) MainMenu.getInstance().redCureIcon).unlock();
                break;
            case Green:
                ((CureIcon) MainMenu.getInstance().greenCureIcon).unlock();
                break;
            case Yellow:
                ((CureIcon) MainMenu.getInstance().yellowCureIcon).unlock();
                break;
        }
    }

    /**
     * Check if the cure related to the {@code colourToCheck} is locked
     *
     * @param colourToCheck The cure colour to check
     *
     * @return True if the cure related to the {@code colourToCheck} is locked; otherwise False
     */
    public boolean isCureForColourLocked(Colour colourToCheck) {
        boolean isUnlocked = false;

        switch (colourToCheck) {
            case Blue:
                isUnlocked = ((CureIcon) MainMenu.getInstance().blueCureIcon).isUnlocked();
                break;
            case Red:
                isUnlocked = ((CureIcon) MainMenu.getInstance().redCureIcon).isUnlocked();
                break;
            case Green:
                isUnlocked = ((CureIcon) MainMenu.getInstance().greenCureIcon).isUnlocked();
                break;
            case Yellow:
                isUnlocked = ((CureIcon) MainMenu.getInstance().yellowCureIcon).isUnlocked();
                break;
        }

        return !isUnlocked;
    }

    /**
     * Update the save table from the game saves loading menu
     *
     * @param gameSaves The list with the game saves to load
     */
    public void updateSaveTable(ArrayList<GameSave> gameSaves) {
        JTable saveTable = MainMenu.getInstance().gameSelectionSavesTable;
        int rowCount = Math.min(gameSaves.size(), saveTable.getRowCount());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        if (gameSaves.size() < 1) {
            return;
        }

        for (int i = 0; i < rowCount; i++) {
            Date saveDate = gameSaves.get(i).saveDate;

            saveTable.setValueAt(gameSaves.get(i).id, i, 0);
            saveTable.setValueAt(dateFormat.format(saveDate), i, 1);
        }
    }

    /**
     * Save the current game state with its new settings, check {@link OptionsManager#saveSettings()} and
     * {@link DbManager#saveGame()}
     */
    public void saveGame() {
        OptionsManager.getInstance().saveSettings();
        DbManager.getInstance().saveGame();
    }

    /**
     * Load a game from {@code gameSave}
     *
     * @param gameSave The game save from the DB
     */
    public void loadSave(GameSave gameSave) {
        Player.getInstance().setActions(gameSave.player.actionsLeft);
        MainMenu.getInstance().characterIcon.setCharacter(gameSave.character);
        loadCities(gameSave.cities);
        loadCards(gameSave.cards);
        loadCures(gameSave.cures);

        Round.getInstance().number = gameSave.round;
        MainMenu.getInstance().historialTxtArea.setText(gameSave.historialText);
        MainMenu.getInstance().epidemicsCounterLbl.setText(String.valueOf(gameSave.totalOutbreaks));
    }

    /**
     * Load cities from the DB into {@link Map#cities}
     *
     * @param cities The city object array loaded from the DB
     */
    private void loadCities(Object[] cities) {
        ArrayList<City> mapCities = Map.getInstance().getCities();

        for (int i = 0; i < mapCities.size(); i++) {
            City toModify = mapCities.get(i);

            City savedCity = new City();
            savedCity.setName(((CityEntity) cities[i]).name);
            savedCity.setTotalViruses(((CityEntity) cities[i]).viruses);

            if (savedCity.getName().equals(toModify.getName())) {
                toModify.setTotalViruses(savedCity.getTotalViruses());
            }
        }
    }

    /**
     * Similar to {@link GameManager#loadCities(Object[])}, but for the {@link MainMenu#cardsLbls}
     *
     * @param cardColours The card colours array loaded from the DB
     */
    private void loadCards(Object[] cardColours) {
        ArrayList<JLabel> mapCards = MainMenu.getInstance().cardsLbls;

        for (int i = 0; i < cardColours.length; i++) {
            String colourName = (String) cardColours[i];

            if (colourName == null) {
                continue;
            }

            CityCard card = (CityCard) mapCards.get(i);
            card.setColour(Enum.valueOf(Colour.class, colourName));
        }
    }

    /**
     * Similar to {@link GameManager#loadCities(Object[])} and {@link GameManager#loadCards(Object[])}, but for the cure
     * labels from {@link MainMenu}
     *
     * @param cureStates The cure states loaded from the DB
     *
     * @throws RuntimeException Throws when {@code cureStates} length is not 4
     */
    private void loadCures(Object[] cureStates) {
        CureIcon yellowCure = (CureIcon) MainMenu.getInstance().yellowCureIcon;
        CureIcon redCure = (CureIcon) MainMenu.getInstance().redCureIcon;
        CureIcon blueCure = (CureIcon) MainMenu.getInstance().blueCureIcon;
        CureIcon greenCure = (CureIcon) MainMenu.getInstance().greenCureIcon;

        if (cureStates.length != 4) {
            throw new RuntimeException(String.format(
                  "There was %d cure states instead of 4", cureStates.length));
        }

        if (Objects.equals(cureStates[0], BigDecimal.ONE)) {
            yellowCure.unlock();
        }

        if (Objects.equals(cureStates[1], BigDecimal.ONE)) {
            redCure.unlock();
        }

        if (Objects.equals(cureStates[2], BigDecimal.ONE)) {
            blueCure.unlock();
        }

        if (Objects.equals(cureStates[3], BigDecimal.ONE)) {
            greenCure.unlock();
        }
    }
}