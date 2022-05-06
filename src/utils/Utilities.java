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

public class Utilities {
    public static final Random rand = new Random();

    public static void exit(int status) {
        GameManager.getInstance().saveGame();
        DbManager.getInstance().disconnect();
        System.exit(status);
    }

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

            exit(0);
        }
    };

    public static void switchImage(JPanel panel, String imageName) {
        invokeLater(() -> {
            Panel panelToSwitch = (Panel) panel;
            panelToSwitch.setBackgroundImage(panel.getToolkit().createImage("src/assets/img/" + imageName + ".png"));

            // Quita los artefactos
            panel.updateUI();
        });
    }

    public static void switchToCard(JPanel rootPanel, String cardName) {
        invokeLater(() -> {
            CardLayout cardLayout = (CardLayout) rootPanel.getLayout();
            cardLayout.show(rootPanel, cardName);
        });
    }

    public static void loadCities() {
        Map.getInstance().cities = new ArrayList<>();

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

                Map.getInstance().cities.add(city);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (EOFException ignored) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        loadCityConnections();
    }

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

    public static Colour getRandomColour() {
        return Colour.values()[rand.nextInt(Colour.values().length)];
    }

    public static City getRandomCityForColour(Colour colour) {
        ArrayList<City> sameColouredCities = new ArrayList<>();

        for (City city : Map.getInstance().cities) {
            if (city.getColour() == colour) {
                sameColouredCities.add(city);
            }
        }

        return sameColouredCities.get(rand.nextInt(sameColouredCities.size()));
    }
}
