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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

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

        // Deja un espacio entre las ciudades contagiadas y las acciones del usuario
        MainMenu.getInstance().historialTxtArea.append("\n");

        updateGameState();
        Player.getInstance().refillActions();
    }

    public void updateGameState() {
        SwingUtilities.invokeLater(() -> {
            MainMenu.getInstance().yellowTotalVirusesLbl.setText(String.valueOf(totalViruses.get(Colour.Yellow)));
            MainMenu.getInstance().blueTotalVirusesLbl.setText(String.valueOf(totalViruses.get(Colour.Blue)));
            MainMenu.getInstance().greenTotalVirusesLbl.setText(String.valueOf(totalViruses.get(Colour.Green)));
            MainMenu.getInstance().redTotalVirusesLbl.setText(String.valueOf(totalViruses.get(Colour.Red)));
        });
    }

    public void resetGame() {
        Player.resetInstance();
        Round.resetInstance();

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

    private boolean hasEpidemicsPassedThreshold() {
        int currentEpidemicsCount = Integer.parseInt(MainMenu.getInstance().epidemicsCounterLbl.getText());
        return currentEpidemicsCount >= OptionsManager.getInstance().epidemicsThreshold;
    }

    private boolean haveVirusesPassedThreshold() {
        return totalViruses.values()
              .stream()
              .mapToInt(val -> val)
              .sum() > OptionsManager.getInstance().getVirusesThreshold();
    }

    private boolean isNoMoreVirusesLeft() {
        return totalViruses.values()
              .stream()
              .mapToInt(val -> val)
              .sum() == 0;
    }

    public void incrementEpidemicsCounter() {
        JLabel epidemicsCounterLbl = MainMenu.getInstance().epidemicsCounterLbl;
        int currentEpidemicsCount = Integer.parseInt(epidemicsCounterLbl.getText());

        MainMenu.getInstance().epidemicsCounterLbl.setText(currentEpidemicsCount + 1 + "");
    }

    public void incrementColourVirus(Colour colour) {
        totalViruses.put(colour, totalViruses.get(colour) + 1);
        updateGameState();
    }

    public void decrementColourVirus(Colour colour) {
        totalViruses.put(colour, totalViruses.get(colour) - 1);
        updateGameState();
    }

    public void addNewCityCard() {
        ArrayList<JLabel> cardsLbls = MainMenu.getInstance().cardsLbls;
        int totalCards = cardsLbls.size();

        for (int i = 0; i < totalCards; i++) {
            CityCard card = (CityCard) cardsLbls.get(i);

            if (card.getColour() == null) {
                card.setColour(GeneralUtilities.getRandomColour());
                break;
            }

            if (i == totalCards - 1) {
                int randomPosition = GeneralUtilities.rand.nextInt(totalCards);
                CityCard randomCity = ((CityCard) cardsLbls.get(randomPosition));

                randomCity.setColour(GeneralUtilities.getRandomColour());
            }
        }
    }

    public void updateSaveTable(ArrayList<GameSave> gameSaves) {
        JTable saveTable = MainMenu.getInstance().gameSelectionSavesTable;
        int rowCount = saveTable.getRowCount();

        if (gameSaves.size() < 1) {
            return;
        }

        for (int i = 0; i < rowCount; i++) {
            saveTable.setValueAt(gameSaves.get(i).saveDate.toString(), i, 0);
        }
    }

    public void saveGame() {
        OptionsManager.getInstance().saveSettings();
        DbManager.getInstance().saveGame();
    }

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
