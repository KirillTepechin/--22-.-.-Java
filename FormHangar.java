import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class FormHangar {
    private JPanel mainPanel;
    private DrawPanel drawPanel;
    private JButton buttonSetArmoredVehicle;
    private JButton buttonTake;
    private JFormattedTextField formattedTextField;
    private JTextField textFieldName;
    private JList<String> listBoxHangars;
    private JButton buttonDeleteHangar;
    private JButton buttonAddHangar;
    private JButton buttonTakeFromCollection;
    private JToolBar menuBar;
    private JButton buttonSave;
    private JButton buttonLoad;
    private JButton buttonSaveLevel;
    private JButton buttonLoadLevel;
    private final HangarCollection hangarCollection;
    private final DefaultListModel<String> hangarList;
    private final Stack<Transport> taken;

    public FormHangar() {
        taken = new Stack<>();
        hangarList = new DefaultListModel<>();
        listBoxHangars.setModel(hangarList);
        hangarCollection = drawPanel.getHangarCollection();

        buttonSetArmoredVehicle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                frame.setSize(1000, 500);
                FormAAGConfig formAAGConfig=new FormAAGConfig(FormHangar.this);
                frame.add(formAAGConfig.getMainPanel());
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
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
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileSaveDialog = new JFileChooser();
                fileSaveDialog.setFileFilter(new FileNameExtensionFilter("Текстовый файл", "txt"));
                int result = fileSaveDialog.showSaveDialog(drawPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    if (hangarCollection.saveData(fileSaveDialog.getSelectedFile().getAbsolutePath() + ".txt")) {
                        JOptionPane.showMessageDialog(drawPanel, "Файл успешно сохранен", "Результат", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(drawPanel, "Файл не сохранен", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        buttonLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileOpenDialog = new JFileChooser();
                fileOpenDialog.setFileFilter(new FileNameExtensionFilter("Текстовый файл", "txt"));
                int result = fileOpenDialog.showOpenDialog(drawPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    if (hangarCollection.loadData(fileOpenDialog.getSelectedFile().getAbsolutePath())) {
                        JOptionPane.showMessageDialog(drawPanel, "Файл успешно загружен", "Success", JOptionPane.INFORMATION_MESSAGE);
                        reloadLevels();
                        drawPanel.repaint();
                    } else {
                        JOptionPane.showMessageDialog(drawPanel, "Файл не загружен", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        buttonSaveLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileSaveDialog = new JFileChooser();
                fileSaveDialog.setFileFilter(new FileNameExtensionFilter("Текстовый файл", "txt"));
                if (listBoxHangars.getSelectedValue() == null) {
                    JOptionPane.showMessageDialog(drawPanel, "Выберите ангар", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int result = fileSaveDialog.showSaveDialog(drawPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    if (hangarCollection.saveHangar(fileSaveDialog.getSelectedFile().getAbsolutePath() + ".txt", listBoxHangars.getSelectedValue())) {
                        JOptionPane.showMessageDialog(drawPanel, "Файл успешно сохранен", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(drawPanel, "Файл не сохранен", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        buttonLoadLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileOpenDialog = new JFileChooser();
                fileOpenDialog.setFileFilter(new FileNameExtensionFilter("Текстовый файл", "txt"));
                int result = fileOpenDialog.showOpenDialog(drawPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    if (hangarCollection.loadHangar(fileOpenDialog.getSelectedFile().getAbsolutePath())) {
                        JOptionPane.showMessageDialog(drawPanel, "Файл успешно загружен", "Success", JOptionPane.INFORMATION_MESSAGE);
                        reloadLevels();
                        drawPanel.repaint();
                    } else {
                        JOptionPane.showMessageDialog(drawPanel, "Файл не загружен", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
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

    public void setArmoredVehicle(Transport armoredVehicle){
        Hangar hangar = hangarCollection.getValue(listBoxHangars.getSelectedValue());
        if(hangar!=null) {
            if (hangar.add(hangar, armoredVehicle) > -1) {
                drawPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "Ангар переполнен!");
            }
        }
    }

}
