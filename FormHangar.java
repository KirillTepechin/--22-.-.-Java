import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormHangar {
    private JPanel mainPanel;
    private JButton buttonSetAAG;
    private JButton buttonSetArmoredVehicle;
    private JButton buttonTake;
    private JFormattedTextField formattedTextField;
    private DrawPanel drawPanel;

    public FormHangar() {
        Hangar<Transport, GunsInterface> hangar = drawPanel.getHangar();
        buttonSetAAG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color dialog = JColorChooser.showDialog(null, "Основной цвет", Color.GREEN);
                if (dialog != null) {
                    Color dialogDop = JColorChooser.showDialog(null, "Дополнительный цвет", Color.BLACK);
                    if (dialogDop != null) {
                        Transport AAG = new AntiAircraftGun(100, 1000, dialog, dialogDop, true, true);
                        if (hangar.add(hangar, AAG) != -1) {
                            drawPanel.repaint();
                        } else {
                            JOptionPane.showMessageDialog(null, "Ангар переполнен");
                        }
                    }
                }
            }
        });
        buttonSetArmoredVehicle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color dialog = JColorChooser.showDialog(null, "Основной цвет", Color.GREEN);
                if (dialog != null) {
                    Transport armoredVehicle = new ArmoredVehicle(100, 1000, dialog);
                    if (hangar.add(hangar, armoredVehicle) != -1) {
                        drawPanel.repaint();
                    } else {
                        JOptionPane.showMessageDialog(null, "Ангар переполнен!");
                    }
                }
            }
        });
        buttonTake.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String index = formattedTextField.getText();
                if (!index.equals("")) {
                    var armoredVehicle = hangar.delete(hangar, Integer.parseInt(index));
                    if (armoredVehicle != null) {
                        JFrame frame = new JFrame();
                        frame.setSize(900, 600);
                        frame.setResizable(false);
                        FormAAG formAAG = new FormAAG();
                        formAAG.setArmoredVehicle(armoredVehicle);
                        frame.add(formAAG.getPanelAAG());
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                        drawPanel.repaint();
                    }
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
