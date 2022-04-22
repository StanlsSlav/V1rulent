package utils;


import model.Location;
import model.base.Colour;
import model.base.Panel;
import model.game.City;
import model.game.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Utilities {
    public static boolean isLeftButtonPressed(MouseEvent event) {
        return event.getButton() == MouseEvent.BUTTON1;
    }

    public static MouseAdapter exitOnClick = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);

            if (isLeftButtonPressed(e)) {
                System.exit(0);
            }
        }
    };

    public static void centerScreen(JFrame mainFrame) {
        double y = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        double x = Toolkit.getDefaultToolkit().getScreenSize().getWidth();

        // Centra la ventana del juego al centro del monitor
        Window.getWindows()[0].setLocation((int) x / 2 - mainFrame.getWidth() / 2,
              (int) y / 2 - mainFrame.getHeight() / 2);
    }

    public static void switchImage(JPanel panel, String imageName) {
        Panel panelToSwitch = (Panel) panel;
        panelToSwitch.setBackgroundImage(panel.getToolkit().createImage("src/assets/img/" + imageName + ".png"));

        // Quita los artefactos
        panel.updateUI();
    }

    public static void switchToCard(JPanel rootPanel, String cardName) {
        CardLayout cardLayout = (CardLayout) rootPanel.getLayout();
        cardLayout.show(rootPanel, cardName);

        System.out.printf("Switching to %s%n", cardName);
    }

    public static void loadCities() {
        File citiesFile = new File("src/assets/cities.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(citiesFile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] dotCommaSplit = line.split(";");
                String color = dotCommaSplit[1];
                String[] location = dotCommaSplit[2].split(",");

                City city = new City();

                city.setName(dotCommaSplit[0]);
                city.setLocation(new Location(Integer.parseInt(location[0]), Integer.parseInt(location[1])));
                city.setColor(Colour.valueOf(color));

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
                          .orElseThrow(() -> new RuntimeException(String.format("'%s' was not found", connectedCity))));
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
}
