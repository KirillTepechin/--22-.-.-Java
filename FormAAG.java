import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class FormAAG{
    private JButton buttonCreateAAG;
    private JPanel panelAAG;
    private JButton buttonRight;
    private JButton buttonLeft;
    private JButton buttonDown;
    private JButton buttonUp;
    private JButton buttonCreateArmoredVehicle;

    private Transport armoredVehicle;
    private Graphics g;
    private void draw() {
        armoredVehicle.drawTransport(g);
    }
    public FormAAG() {
        buttonDown.setIcon(new ImageIcon("files/StrelkaDown.png"));
        buttonUp.setIcon(new ImageIcon("files/StrelkaUp.png"));
        buttonLeft.setIcon(new ImageIcon("files/StrelkaLeft.png"));
        buttonRight.setIcon(new ImageIcon("files/StrelkaRight.png"));
        buttonCreateAAG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                g = panelAAG.getGraphics();
                panelAAG.update(g);
                Random rnd = new Random();
                armoredVehicle = new AntiAircraftGun(rnd.nextInt(200) + 100, rnd.nextInt(1000)+1000, Color.GREEN,
                        Color.BLACK, true, true);
                armoredVehicle.setPosition(rnd.nextInt(90)+10,
                        rnd.nextInt(90)+10, panelAAG.getWidth(), panelAAG.getHeight());
                draw();
            }
        });
        buttonCreateArmoredVehicle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                g = panelAAG.getGraphics();
                panelAAG.update(g);
                Random rnd = new Random();
                armoredVehicle = new ArmoredVehicle(rnd.nextInt(200) + 100, rnd.nextInt(1000)+1000, Color.GREEN);
                armoredVehicle.setPosition(rnd.nextInt(90)+10, rnd.nextInt(90)+10, panelAAG.getWidth(), panelAAG.getHeight());
                draw();
            }
        });
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                g = panelAAG.getGraphics();
                panelAAG.update(g);
                String name=e.getActionCommand();
                switch (name) {
                    case "UP" : armoredVehicle.moveTransport(Direction.UP);
                    case "DOWN" : armoredVehicle.moveTransport(Direction.DOWN);
                    case "LEFT" : armoredVehicle.moveTransport(Direction.LEFT);
                    case "RIGHT" : armoredVehicle.moveTransport(Direction.RIGHT);
                }
                draw();
            }
        };
        buttonRight.addActionListener(listener);
        buttonDown.addActionListener(listener);
        buttonUp.addActionListener(listener);
        buttonLeft.addActionListener(listener);
    }
    public JPanel getPanelAAG(){
        return panelAAG;
    }
}
