package utils;


import controller.DbManager;
import controller.GameManager;
import model.base.Colour;
import model.base.Panel;
import model.game.City;
import model.game.Map;

import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static javax.swing.SwingUtilities.invokeLater;

/**
 * Utilities used all around the game
 */
public class GeneralUtilities {
    public static final Random rand = new Random();

    public static void exit(int status, boolean shouldSaveState) {
        if (shouldSaveState) {
            GameManager.getInstance().saveGame();
        }

        DbManager.getInstance().disconnect();
        System.exit(status);
    }

    /**
     * Detect if the left mouse button is pressed from the according {@code event}
     *
     * @param event The mouse event from which to determine if the left mouse button was clicked
     *
     * @return True if the left mouse button was clicked; otherwise False
     */
    public static boolean isLeftButtonPressed(MouseEvent event) {
        return event.getButton() == MouseEvent.BUTTON1;
    }

    public static MouseAdapter exitOnClick = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if (!isLeftButtonPressed(e)) {
                return;
            }

            exit(0, true);
        }
    };

    /**
     * Switch the background image of an {@code panel}
     *
     * @param panel The panel with the old background image to be replaced
     * @param imageName The name of the new background image
     */
    public static void switchImage(JPanel panel, String imageName) {
        invokeLater(() -> {
            Panel panelToSwitch = (Panel) panel;
            panelToSwitch.setBackgroundImage(panel.getToolkit().createImage("src/assets/img/" + imageName + ".png"));

            // Remove artefacts
            panel.updateUI();
        });
    }

    /**
     * Switch to the proper {@code cardName}, based on the {@code rootPanel}
     *
     * @param rootPanel The panel to get its contents switched
     * @param cardName The card name to switch to
     */
    public static void switchToCard(JPanel rootPanel, String cardName) {
        invokeLater(() -> {
            CardLayout cardLayout = (CardLayout) rootPanel.getLayout();
            cardLayout.show(rootPanel, cardName);
        });
    }

    /**
     * Load the cities from the <i>cities.csv</i> into {@link Map#cities}
     */
    public static void loadCities() {
        Map.getInstance().setCities(new ArrayList<>());

        final File CITIES_FILE = new File("src/assets/cities.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(CITIES_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] dotCommaSplit = line.split(";");
                String color = dotCommaSplit[1];
                String[] location = dotCommaSplit[2].split(",");

                City city = new City();

                city.setName(dotCommaSplit[0]);
                city.setPoint(new Point(Integer.parseInt(location[0]), Integer.parseInt(location[1])));
                city.setColour(Colour.valueOf(color));

                Map.getInstance().getCities().add(city);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (EOFException ignored) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        loadCityConnections();
    }

    /**
     * Load the proper city connections for each city in {@code Map#cities}
     */
    private static void loadCityConnections() {
        File citiesFile = new File("src/assets/cities.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(citiesFile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] dotCommaSplit = line.split(";");
                String cityName = dotCommaSplit[0];
                String[] connectedCitiesNames = dotCommaSplit[3].split(",");
                ArrayList<City> connectedCities = new ArrayList<>();

                City toModify = Map.getInstance()
                      .cities.stream()
                      .filter(x -> x.getName().equals(cityName))
                      .findFirst()
                      .orElseThrow(() -> new RuntimeException(String.format("'%s' was not found", cityName)));

                for (String connectedCity : connectedCitiesNames) {
                    connectedCities.add(Map.getInstance()
                          .cities.stream()
                          .filter(x -> x.getName().equals(connectedCity))
                          .findFirst()
                          .orElseThrow(() -> new RuntimeException(
                                String.format("'%s' was not found", connectedCity))));
                }

                toModify.setConnectedCities(connectedCities);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (EOFException ignored) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get a random colour
     *
     * @return The random colour
     */
    public static Colour getRandomColour() {
        return Colour.values()[rand.nextInt(Colour.values().length)];
    }

    /**
     * Get a random city with its colour matching the passed {@code colour}
     *
     * @param colour The colour from which to find the city
     *
     * @return The random city
     */
    public static City getRandomCityForColour(Colour colour) {
        ArrayList<City> sameColouredCities = new ArrayList<>();

        for (City city : Map.getInstance().getCities()) {
            if (city.getColour() == colour) {
                sameColouredCities.add(city);
            }
        }

        return sameColouredCities.get(rand.nextInt(sameColouredCities.size()));
    }
}