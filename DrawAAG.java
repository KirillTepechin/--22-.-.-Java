import javax.swing.*;
import java.awt.*;

public class DrawAAG extends JPanel {

    private Transport armoredVehicle;
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (armoredVehicle != null) {
            armoredVehicle.drawTransport(g);
        }
    }
    public Transport getArmoredVehicle() {
        return armoredVehicle;
    }
    public void setArmoredVehicle(Transport armoredVehicle) {
        this.armoredVehicle=armoredVehicle;
    }

}

