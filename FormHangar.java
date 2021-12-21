import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyException;
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
    private Logger logger;

    public FormHangar() {
        logger=LogManager.getRootLogger();
        taken = new Stack<>();
        hangarList = new DefaultListModel<>();
        listBoxHangars.setModel(hangarList);
        hangarCollection = drawPanel.getHangarCollection();

        buttonSetArmoredVehicle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                frame.setSize(1000, 500);
                FormAAGConfig formAAGConfig = new FormAAGConfig(FormHangar.this);
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
                    Hangar hangar = hangarCollection.getValue(listBoxHangars.getModel().getElementAt(listBoxHangars.getSelectedIndex()));
                    Transport armoredVehicle = null;

                    try {
                        armoredVehicle = hangar.delete(hangar, Integer.parseInt(index));
                        logger.info("Изъят траснпорт "+ armoredVehicle +" с места "+ formattedTextField.getText());
                    } catch (HangarNotFoundException hangarNotFoundException) {
                        logger.warn(hangarNotFoundException.getMessage());
                        JOptionPane.showMessageDialog(drawPanel, hangarNotFoundException.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                    catch (Exception exception){
                        logger.fatal("Неизвестная ошибка уровня fatal!!! - "  + exception.getMessage());
                    }
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
                    logger.info("Из коллекции изьят транспорт");
                } else {
                    JOptionPane.showMessageDialog(null, "Стек пуст!");
                }
            }
        });
        buttonDeleteHangar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listBoxHangars.getSelectedIndex() > -1) {
                    logger.info("Удалили ангар "+ listBoxHangars.getModel().getElementAt(listBoxHangars.getSelectedIndex()));
                    hangarCollection.delHangar((String) listBoxHangars.getModel().getElementAt(listBoxHangars.getSelectedIndex()));
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
                logger.info("Добавили ангар "+textFieldName.getText());
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
                    try {
                        hangarCollection.saveData(fileSaveDialog.getSelectedFile().getAbsolutePath() + ".txt");
                        JOptionPane.showMessageDialog(drawPanel, "Файл успешно сохранен", "Результат", JOptionPane.INFORMATION_MESSAGE);
                        logger.info("Сохранили в "+ fileSaveDialog.getSelectedFile().getAbsolutePath()+" .txt");
                    } catch (IOException IOException) {
                        JOptionPane.showMessageDialog(drawPanel, "Файл не сохранен", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                    catch (Exception exception){
                        logger.fatal("Неизвестная ошибка уровня fatal!!! - "  + exception.getMessage());
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
                    try {
                        hangarCollection.loadData(fileOpenDialog.getSelectedFile().getAbsolutePath());
                        JOptionPane.showMessageDialog(drawPanel, "Файл успешно загружен", "Success", JOptionPane.INFORMATION_MESSAGE);
                        reloadLevels();
                        drawPanel.repaint();
                        logger.info("Загрузили файл из "+ fileOpenDialog.getSelectedFile().getAbsolutePath());
                    } catch (FileNotFoundException fileNotFoundException) {
                        JOptionPane.showMessageDialog(drawPanel, "Файл не загружен", "Error", JOptionPane.ERROR_MESSAGE);
                        logger.warn("Файл не найден");
                    } catch (HangarOverflowException hangarOverflowException) {
                        JOptionPane.showMessageDialog(drawPanel, "Файл не загружен", "Error", JOptionPane.ERROR_MESSAGE);
                        logger.warn("Ангар переполнен");
                    }
                    catch (Exception exception){
                        logger.fatal("Неизвестная ошибка уровня fatal!!! - "  + exception.getMessage());
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
                    try {
                        hangarCollection.saveHangar(fileSaveDialog.getSelectedFile().getAbsolutePath() + ".txt", listBoxHangars.getSelectedValue());
                        JOptionPane.showMessageDialog(drawPanel, "Файл успешно сохранен", "Success", JOptionPane.INFORMATION_MESSAGE);
                        logger.info("Сохранили отдельный ангар в "+ fileSaveDialog.getSelectedFile().getAbsolutePath()+" .txt");
                    } catch (KeyException keyException) {
                        JOptionPane.showMessageDialog(drawPanel, "Файл не сохранен", "Error", JOptionPane.ERROR_MESSAGE);
                        logger.warn("В ангарах нет такого ангара");
                    } catch (IOException ioException) {
                        JOptionPane.showMessageDialog(drawPanel, "Файл не сохранен", "Error", JOptionPane.ERROR_MESSAGE);
                        logger.warn("Неверный ввод данных");
                    }
                    catch (Exception exception){
                        logger.fatal("Неизвестная ошибка уровня fatal!!! - "  + exception.getMessage());
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
                    try {
                        hangarCollection.loadHangar(fileOpenDialog.getSelectedFile().getAbsolutePath());
                        JOptionPane.showMessageDialog(drawPanel, "Файл успешно загружен", "Success", JOptionPane.INFORMATION_MESSAGE);
                        reloadLevels();
                        drawPanel.repaint();
                        logger.info("Загрузили отдельный ангар из файла - "+ fileOpenDialog.getSelectedFile().getAbsolutePath());
                    } catch (FileNotFoundException fileNotFoundException) {
                        JOptionPane.showMessageDialog(drawPanel, "Файл не загружен", "Error", JOptionPane.ERROR_MESSAGE);
                        logger.warn("Файл не найден");
                    } catch (HangarOverflowException hangarOverflowException) {
                        JOptionPane.showMessageDialog(drawPanel, "Файл не загружен", "Error", JOptionPane.ERROR_MESSAGE);
                        logger.warn("Ангар переполнен");
                    }
                    catch (Exception exception){
                        logger.fatal("Неизвестная ошибка уровня fatal!!! - "  + exception.getMessage());
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

    public void setArmoredVehicle(Transport armoredVehicle) {
        Hangar hangar = hangarCollection.getValue(listBoxHangars.getSelectedValue());
        if (hangar != null && armoredVehicle!=null) {
            try {
                hangar.add(hangar, armoredVehicle);
                drawPanel.repaint();
                logger.info("Добавлен транспорт "+ armoredVehicle);
               //throw new Exception();
            } catch (HangarOverflowException e) {
                JOptionPane.showMessageDialog(null, "Ангар переполнен!");
                logger.warn("Бронетранспорт не удалось поставить");
            }
            catch (Exception e){
                logger.fatal("Неизвестная ошибка уровня fatal!!! - ");
            }
        }
    }

}
