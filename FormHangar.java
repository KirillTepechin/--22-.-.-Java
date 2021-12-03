import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class FormHangar {
    private JPanel mainPanel;
    private JButton buttonSetAAG;
    private JButton buttonSetArmoredVehicle;
    private JButton buttonTake;
    private JFormattedTextField formattedTextField;
    private DrawPanel drawPanel;
    private JTextField textFieldName;
    private JList<String> listBoxHangars;
    private JButton buttonDeleteHangar;
    private JButton buttonAddHangar;
    private JButton buttonTakeFromCollection;
    private final HangarCollection hangarCollection;
    private final DefaultListModel<String> hangarList;
    private final Stack<Transport> taken;

    public FormHangar() {
        taken = new Stack<>();
        hangarList = new DefaultListModel<>();
        listBoxHangars.setModel(hangarList);
        hangarCollection = drawPanel.getHangarCollection();
        buttonSetAAG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color dialog = JColorChooser.showDialog(null, "Основной цвет", Color.GREEN);
                if (dialog != null) {
                    Color dialogDop = JColorChooser.showDialog(null, "Дополнительный цвет", Color.BLACK);
                    if (dialogDop != null) {
                        Transport AAG = new AntiAircraftGun(100, 1000, dialog, dialogDop, true, true);
                        Hangar hangar = hangarCollection.getValue(listBoxHangars.getSelectedValue());
                        if (hangar.add(hangar, AAG) > -1) {
                            drawPanel.repaint();
                        } else {
                            JOptionPane.showMessageDialog(null, "Ангар переполнен!");
                        }
                    }
                }
            }
        });
        buttonSetArmoredVehicle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listBoxHangars.getSelectedIndex() > -1) {
                    Color dialog = JColorChooser.showDialog(null, "Основной цвет", Color.GREEN);
                    if (dialog != null) {
                        Transport armoredVehicle = new ArmoredVehicle(100, 1000, dialog);
                        Hangar hangar = hangarCollection.getValue(listBoxHangars.getSelectedValue());
                        if (hangar.add(hangar, armoredVehicle) > -1) {
                            drawPanel.repaint();
                        } else {
                            JOptionPane.showMessageDialog(null, "Ангар переполнен!");
                        }
                    }
                }
            }
        });
        buttonTake.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String index = formattedTextField.getText();
                if (!index.equals("")) {
                    Hangar hangar = hangarCollection.getValue((String) listBoxHangars.getModel().getElementAt(listBoxHangars.getSelectedIndex()));
                    var armoredVehicle = hangar.delete(hangar, Integer.parseInt(index));
                    if (armoredVehicle != null) {
                        taken.add(armoredVehicle);
                    }
                    drawPanel.repaint();
                }
            }
        });
        buttonTakeFromCollection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!taken.empty()) {
                    JFrame frame = new JFrame();
                    frame.setSize(900, 600);
                    frame.setResizable(false);
                    FormAAG formAAG = new FormAAG();
                    formAAG.setArmoredVehicle(taken.pop());
                    frame.add(formAAG.getPanelAAG());
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                    drawPanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Стек пуст!");
                }
            }
        });
        buttonDeleteHangar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listBoxHangars.getSelectedIndex() > -1) {
                    hangarCollection.DelParking((String) listBoxHangars.getModel().getElementAt(listBoxHangars.getSelectedIndex()));
                    reloadLevels();
                    drawPanel.repaint();
                }
            }
        });
        buttonAddHangar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textFieldName.getText();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Введите имя паровки");
                    return;
                }
                hangarCollection.addHangar(name);
                reloadLevels();
                drawPanel.repaint();
            }
        });
        listBoxHangars.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                drawPanel.setSelectedItem(listBoxHangars.getSelectedValue());
                drawPanel.repaint();
            }
        });
    }

    private void reloadLevels() {
        hangarCollection.updateKeys();
        int index = listBoxHangars.getSelectedIndex();
        hangarList.removeAllElements();
        int i = 0;
        for (String name : hangarCollection.Keys) {
            hangarList.add(i, name);
            i++;
        }
        int itemsCount = hangarList.size();
        if (itemsCount > 0 && (index < 0 || index >= itemsCount)) {
            listBoxHangars.setSelectedIndex(0);
        } else if (index >= 0 && index < itemsCount) {
            listBoxHangars.setSelectedIndex(index);
        }
        drawPanel.repaint();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
