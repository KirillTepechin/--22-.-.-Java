import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame=new JFrame();
        frame.setSize(900,600);
        FormHangar formHangar = new FormHangar();
        frame.add(formHangar.getMainPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        /*
        AntiAircraftGun armoredVehicle=new AntiAircraftGun(2,2,Color.GREEN,Color.BLACK,true,true);
        GunsInterface g = new GunsThree();
        g.setAmount(2);
        armoredVehicle.setGuns(g);
        armoredVehicle.forEach(System.out::println);
        */
    }
}
