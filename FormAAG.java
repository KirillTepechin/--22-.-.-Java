import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class FormAAG{
    private JButton buttonCreate;
    private JPanel panelAAG;
    private JButton buttonRight;
    private JButton buttonLeft;
    private JButton buttonDown;
    private JButton buttonUp;

    private AntiAircraftGun Aag;
    private Graphics g;
    private void Draw() {
        Aag.DrawTransport(g);
    }
    public FormAAG() {
        buttonDown.setIcon(new ImageIcon("files/StrelkaDown.png"));
        buttonUp.setIcon(new ImageIcon("files/StrelkaUp.png"));
        buttonLeft.setIcon(new ImageIcon("files/StrelkaLeft.png"));
        buttonRight.setIcon(new ImageIcon("files/StrelkaRight.png"));
        buttonCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                g = panelAAG.getGraphics();
                panelAAG.update(g);
                Random rnd = new Random();
                Aag = new AntiAircraftGun();
                Aag.init(rnd.nextInt(200) + 100, rnd.nextInt(1000)+1000, Color.GREEN,
                        Color.BLACK, true, true);
                Aag.setPosition(rnd.nextInt(90)+10,
                        rnd.nextInt(90)+10, panelAAG.getWidth(), panelAAG.getHeight());
                Draw();
            }
        });
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelAAG.update(g);
                String name=e.getActionCommand();
                switch (name) {
                    case "UP" : Aag.moveTransport(Direction.UP); break;
                    case "DOWN" : Aag.moveTransport(Direction.DOWN); break;
                    case "LEFT" : Aag.moveTransport(Direction.LEFT);break;
                    case "RIGHT" : Aag.moveTransport(Direction.RIGHT);break;
                }
                Draw();
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
