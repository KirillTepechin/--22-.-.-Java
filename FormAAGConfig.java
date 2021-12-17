import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.*;;


public class FormAAGConfig{
    private JPanel mainPanel;
    private JSpinner spinnerSpeed;
    private JSpinner spinnerWeight;
    private JCheckBox checkBoxGun;
    private JCheckBox checkBoxRadiolocation;
    private JLabel labelAAG;
    private JLabel labelArmoredVehicle;
    private DrawAAG drawPanel;
    private JPanel panelType;
    private JPanel bluePanel;
    private JPanel greenPanel;
    private JPanel blackPanel;
    private JPanel redPanel;
    private JPanel whitePanel;
    private JPanel yellowPanel;
    private JPanel grayPanel;
    private JPanel magentaPanel;
    private JLabel mainColorLabel;
    private JLabel dopColorLabel;
    private JButton addButton;
    private JButton cancelButton;
    private JPanel ColorPanel;
    private JLabel labelGunsOne;
    private JLabel labelGunsTwo;
    private JLabel labelGunsThree;
    private JRadioButton radioButtonTwo;
    private JRadioButton radioButtonFour;
    private JRadioButton radioButtonSix;
    private final ButtonGroup buttonGroup=new ButtonGroup();
    private Color color;
    private Vehicle armoredVehicle = null;
    private  Vehicle buffer = null;
    private GunsInterface realization;
    public FormAAGConfig(FormHangar formHangar){
        init();
        MouseMotionListener typeListenerDrag =new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                Double dSpeed=(Double)spinnerSpeed.getValue();
                int speed=dSpeed.intValue();
                Double dWeight= (Double) spinnerWeight.getValue();
                float weight = dWeight.floatValue();
                JLabel label=(JLabel)e.getSource();
                switch (label.getText()) {
                    case "Бронетранспорт" : {
                        buffer = new ArmoredVehicle(speed, weight, Color.WHITE);
                        break;
                    }
                    case "Зенитка" : {
                        buffer = new AntiAircraftGun(speed, weight, Color.WHITE, Color.BLACK, checkBoxGun.isSelected(), checkBoxRadiolocation.isSelected());
                        GunsOne defaultRealization=new GunsOne();
                        defaultRealization.setAmount(2);
                        ((AntiAircraftGun) buffer).setGuns(defaultRealization);
                        break;
                    }
                }
            }
        };
        MouseListener typeListenerDrop=new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                if (checkLocation(e, drawPanel)) {
                    try {
                        armoredVehicle= (Vehicle) buffer.clone();
                    } catch (CloneNotSupportedException cloneNotSupportedException) {
                        cloneNotSupportedException.printStackTrace();
                    }
                    armoredVehicle.setPosition(130, 130, drawPanel.getWidth(), drawPanel.getHeight());
                    drawPanel.setArmoredVehicle(armoredVehicle);
                    drawPanel.repaint();
                }
                mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        };
        MouseMotionListener colorListenerDrag = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                JPanel panelColor = (JPanel) e.getSource();
                color=panelColor.getBackground();
            }
        };
        MouseListener colorListenerDrop = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(drawPanel.getArmoredVehicle()!=null){
                    if(checkLocation(e,mainColorLabel)){
                        armoredVehicle.setMainColor(color);
                    }
                    else if(checkLocation(e,dopColorLabel)){
                        AntiAircraftGun AAG = (AntiAircraftGun) armoredVehicle;
                        AAG.setDopColor(color);
                    }
                    drawPanel.repaint();
                }
                mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        };
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame)mainPanel.getParent().getParent().getParent().getParent();
                frame.dispose();
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) mainPanel.getParent().getParent().getParent().getParent();
                formHangar.setArmoredVehicle(drawPanel.getArmoredVehicle());
                frame.dispose();
            }
        });
        MouseMotionListener realizationListenerDrag = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                switch (((JLabel)e.getSource()).getText()){
                    case "1":{
                        realization=new GunsOne();
                        break;
                    }
                    case "2":{
                        realization=new GunsTwo();
                        break;
                    }
                    case "3":{
                        realization=new GunsThree();
                        break;
                    }
                }
                if(radioButtonTwo.isSelected()){
                    realization.setAmount(2);
                }
                if(radioButtonFour.isSelected()){
                    realization.setAmount(4);
                }
                if(radioButtonSix.isSelected()){
                    realization.setAmount(6);
                }
            }
        };
        MouseListener realizationListenerDrop = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (checkLocation(e, drawPanel)) {
                    if (armoredVehicle != null && armoredVehicle instanceof AntiAircraftGun && ((AntiAircraftGun) armoredVehicle).isGun()) {
                        ((AntiAircraftGun) armoredVehicle).setGuns(realization);
                    }
                    drawPanel.repaint();
                }
                mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        };
        labelAAG.addMouseMotionListener(typeListenerDrag);
        labelArmoredVehicle.addMouseMotionListener(typeListenerDrag);
        labelAAG.addMouseListener(typeListenerDrop);
        labelArmoredVehicle.addMouseListener(typeListenerDrop);
        for(Component component: ColorPanel.getComponents()){
            if(component instanceof JPanel){
                component.addMouseMotionListener(colorListenerDrag);
                component.addMouseListener(colorListenerDrop);
            }
        }
        labelGunsOne.addMouseMotionListener(realizationListenerDrag);
        labelGunsOne.addMouseListener(realizationListenerDrop);
        labelGunsTwo.addMouseMotionListener(realizationListenerDrag);
        labelGunsTwo.addMouseListener(realizationListenerDrop);
        labelGunsThree.addMouseMotionListener(realizationListenerDrag);
        labelGunsThree.addMouseListener(realizationListenerDrop);
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }
    public boolean checkLocation(MouseEvent e, JComponent component){
        Point point=component.getLocationOnScreen();
        return e.getXOnScreen() >= point.x && e.getXOnScreen() <= point.x + component.getWidth()
                && e.getYOnScreen() >= point.y && e.getYOnScreen() <= point.y + component.getHeight();
    }
    public void init(){
        SpinnerNumberModel modelSpeed = new SpinnerNumberModel(100.0, 100.0, 1000.0, 1.0);
        SpinnerNumberModel modelWeight = new SpinnerNumberModel(100.0, 100.0, 1000.0, 1.0);
        spinnerSpeed.setModel(modelSpeed);
        spinnerWeight.setModel(modelWeight);
        Border border=new BorderUIResource.LineBorderUIResource(Color.BLACK);
        labelAAG.setBorder(border);
        labelArmoredVehicle.setBorder(border);
        drawPanel.setBorder(border);
        bluePanel.setBorder(border);
        bluePanel.setBackground(Color.blue);
        greenPanel.setBorder(border);
        greenPanel.setBackground(Color.green);
        blackPanel.setBorder(border);
        blackPanel.setBackground(Color.black);
        redPanel.setBorder(border);
        redPanel.setBackground(Color.red);
        whitePanel.setBorder(border);
        whitePanel.setBackground(Color.white);
        yellowPanel.setBorder(border);
        yellowPanel.setBackground(Color.yellow);
        grayPanel.setBorder(border);
        grayPanel.setBackground(Color.gray);
        magentaPanel.setBorder(border);
        magentaPanel.setBackground(Color.magenta);

        mainColorLabel.setBorder(border);
        dopColorLabel.setBorder(border);
        drawPanel.setBorder(border);
        labelGunsOne.setBorder(border);
        labelGunsTwo.setBorder(border);
        labelGunsThree.setBorder(border);

        buttonGroup.add(radioButtonTwo);
        buttonGroup.add(radioButtonFour);
        buttonGroup.add(radioButtonSix);
        radioButtonTwo.setSelected(true);
    }
}
