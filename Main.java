import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame=new JFrame();
        frame.setSize(900,600);
        frame.setResizable(false);
        frame.add(new FormAAG().getPanelAAG());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
