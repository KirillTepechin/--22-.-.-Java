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
    private DrawAAG drawAAG;

    private Transport armoredVehicle;
    //private Graphics g;

    public void setArmoredVehicle(Transport armoredVehicle){
        this.armoredVehicle = armoredVehicle;
        drawAAG.setArmoredVehicle(armoredVehicle);
        draw();
    }
    private void draw() {
        drawAAG.repaint();
    }
    public FormAAG() {
        buttonDown.setIcon(new ImageIcon("files/StrelkaDown.png"));
        buttonUp.setIcon(new ImageIcon("files/StrelkaUp.png"));
        buttonLeft.setIcon(new ImageIcon("files/StrelkaLeft.png"));
        buttonRight.setIcon(new ImageIcon("files/StrelkaRight.png"));
        buttonCreateAAG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random rnd = new Random();
                armoredVehicle = new AntiAircraftGun(rnd.nextInt(200) + 100, rnd.nextInt(1000)+1000, Color.GREEN,
                        Color.BLACK, true, true);
                armoredVehicle.setPosition(rnd.nextInt(90)+10,
                        rnd.nextInt(90)+10, drawAAG.getWidth(), drawAAG.getHeight());
                drawAAG.setArmoredVehicle(armoredVehicle);
                draw();
            }
        });
        buttonCreateArmoredVehicle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random rnd = new Random();
                armoredVehicle = new ArmoredVehicle(rnd.nextInt(200) + 100, rnd.nextInt(1000)+1000, Color.GREEN);
                armoredVehicle.setPosition(rnd.nextInt(90)+10, rnd.nextInt(90)+10, drawAAG.getWidth(), drawAAG.getHeight());
                drawAAG.setArmoredVehicle(armoredVehicle);
                draw();
            }
        });
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name=e.getActionCommand();
                drawAAG.setArmoredVehicle(armoredVehicle);
                switch (name) {
                    case "UP" : {
                        armoredVehicle.moveTransport(Direction.UP);
                        break;
                    }
                    case "DOWN" : {
                        armoredVehicle.moveTransport(Direction.DOWN);
                        break;
                    }
                    case "LEFT" : {
                        armoredVehicle.moveTransport(Direction.LEFT);
                        break;
                    }
                    case "RIGHT" : {
                        armoredVehicle.moveTransport(Direction.RIGHT);
                        break;
                    }
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
        return drawAAG;
    }
}
