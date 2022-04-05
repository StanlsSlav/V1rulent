import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class Pause450 extends JFrame {

    public Pause450() {

        double y = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        double x = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        setSize(960, 540);
        setResizable(false);
        getWindows()[0].setLocation((int) x / 2 - getWidth() / 2, (int) y / 2 - getHeight() / 2);

        setForeground(Color.red);

        JButton botoonContinue = new JButton();


        botoonContinue.setOpaque(true);
        botoonContinue.setIcon(new ImageIcon(("img/pause/540/continue.png")));
        botoonContinue.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseClicked(e);
                botoonContinue.setIcon(new ImageIcon(("img/pause/540/continue hover.png")));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseClicked(e);
                botoonContinue.setIcon(new ImageIcon(("img/pause/540/continue.png")));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                botoonContinue.setIcon(new ImageIcon(("img/pause/540/continue click.png")));

            }

        });

        add(botoonContinue);
        botoonContinue.setBounds(357, 248, 240, 47);

        botoonContinue.setFocusPainted(false);
        botoonContinue.setBorderPainted(false);


        JButton botoonSettings = new JButton();
        add(botoonSettings);
        botoonSettings.setBounds(357, 318, 240, 47);

        botoonSettings.setFocusPainted(false);
        botoonSettings.setBorderPainted(false);
        botoonSettings.setContentAreaFilled(false);

        botoonSettings.setOpaque(true);
        botoonSettings.setIcon(new ImageIcon(("img/pause/540/settings.png")));
        botoonSettings.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseClicked(e);
                botoonSettings.setIcon(new ImageIcon(("img/pause/540/settings hover.png")));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseClicked(e);
                botoonSettings.setIcon(new ImageIcon(("img/pause/540/settings.png")));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                botoonSettings.setIcon(new ImageIcon(("img/pause/540/settings click.png")));
            }

        });

        JButton exit = new JButton();
        add(exit);
        exit.setBounds(357, 386, 240, 47);

        exit.setFocusPainted(false);
        exit.setBorderPainted(false);
        exit.setContentAreaFilled(false);

        exit.setOpaque(true);
        exit.setIcon(new ImageIcon(("img/pause/540/Exit.png")));
        exit.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseClicked(e);
                exit.setIcon(new ImageIcon(("img/pause/540/Exit hover.png")));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseClicked(e);
                exit.setIcon(new ImageIcon(("img/pause/540/Exit.png")));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                exit.setIcon(new ImageIcon(("img/pause/540/Exit click.png")));
            }


        });

        setLayout(null);
        setVisible(true);

        try {
            FondoSwing fondo = new FondoSwing(ImageIO.read(new File("img/pause/540/PAUSE 540.jpg")));
            JPanel panel = (JPanel) this.getContentPane();
            panel.setBorder(fondo);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


    }


    public static void main(String[] args) {

        Pause450 btn = new Pause450();

    }


}
