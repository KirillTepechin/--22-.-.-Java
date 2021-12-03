import javax.swing.*;
import java.awt.*;

public class DrawPanel extends JPanel {

    private final Hangar<Transport, GunsInterface> hangar = new Hangar<>(800, 450);

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (hangar != null) {
            hangar.draw(g);
        }
    }

    public Hangar<Transport, GunsInterface> getHangar() {
        return hangar;
    }
}


