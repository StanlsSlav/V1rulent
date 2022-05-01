package controller;


import model.game.Difficulty;
import model.game.Map;
import model.game.Player;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Controlador para las configuraciones del juego
 */
public class OptionsManager {
    private static OptionsManager instance;

    public static OptionsManager getInstance() {
        if (instance == null) {
            instance = new OptionsManager();
        }

        return instance;
    }

    public String playerName;
    public Difficulty difficulty;
    public int epidemicsThreshold = 8;
    public int virusesThreshold = Map.getInstance().cities.size() * 3;

    public SpinnerModel epidemicsLimits = new SpinnerNumberModel(1, 1, 15, 1);

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void loadSettingsFromXml() {
        File xmlFile = new File("src/assets/settings.xml");

        if (!xmlFile.exists()) {
            createDefaultXmlSettings();
        }

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        Document dom;

        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return;
        }

        try {
            dom = docBuilder.parse(xmlFile);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
            return;
        }

        Element doc = dom.getDocumentElement();

        Player.getInstance().setName(getTextValue(doc, "player_name"));
        setDifficulty(Difficulty.valueOf(getTextValue(doc, "difficulty")));
        epidemicsThreshold = Integer.parseInt(getTextValue(doc, "epidemics_threshold"));
    }

    private void createDefaultXmlSettings() {
        File xmlFile = new File("src/assets/settings.xml");

        try {
            xmlFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        Document doc;
        Element element;

        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return;
        }

        doc = docBuilder.newDocument();
        Element root = doc.createElement("config");

        element = doc.createElement("player_name");
        element.appendChild(doc.createTextNode(Player.getInstance().name));
        root.appendChild(element);

        element = doc.createElement("difficulty");
        element.appendChild(doc.createTextNode(Difficulty.Easy.name()));
        root.appendChild(element);

        element = doc.createElement("epidemics_threshold");
        element.appendChild(doc.createTextNode("8"));
        root.appendChild(element);

        doc.appendChild(root);

        Transformer tr = null;
        try {
            tr = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }

        assert tr != null;
        tr.setOutputProperty(OutputKeys.INDENT, "yes");
        tr.setOutputProperty(OutputKeys.METHOD, "xml");
        tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tr.setOutputProperty("{http://xml.Apache.org/xslt}indent-amount", "4");

        try {
            tr.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(xmlFile)));
        } catch (TransformerException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveSettings() {
        File xmlFile = new File("src/assets/settings.xml");

        try {
            xmlFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        Document doc;
        Element element;

        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return;
        }

        doc = docBuilder.newDocument();
        Element root = doc.createElement("config");

        element = doc.createElement("player_name");
        element.appendChild(doc.createTextNode(this.playerName));
        root.appendChild(element);

        element = doc.createElement("difficulty");
        element.appendChild(doc.createTextNode(Difficulty.Easy.name()));
        root.appendChild(element);

        element = doc.createElement("epidemics_threshold");
        element.appendChild(doc.createTextNode(String.valueOf(epidemicsThreshold)));
        root.appendChild(element);

        doc.appendChild(root);

        Transformer tr = null;
        try {
            tr = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }

        assert tr != null;
        tr.setOutputProperty(OutputKeys.INDENT, "yes");
        tr.setOutputProperty(OutputKeys.METHOD, "xml");
        tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tr.setOutputProperty("{http://xml.Apache.org/xslt}indent-amount", "4");

        try {
            // FIXME: Null and produces empty xml
            tr.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(xmlFile)));
        } catch (TransformerException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String getTextValue(Element doc, String tag) {
        String value = "";
        NodeList nl = doc.getElementsByTagName(tag);

        if (nl.getLength() > 0 && nl.item(0).hasChildNodes()) {
            value = nl.item(0).getFirstChild().getNodeValue();
        }

        return value;
    }
}
