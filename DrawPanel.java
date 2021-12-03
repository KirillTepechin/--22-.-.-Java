import javax.swing.*;
import java.awt.*;

public class DrawPanel extends JPanel {
    private final HangarCollection hangarCollection=new HangarCollection(800,
            450);
    private String selectedItem = null;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (selectedItem != null) {
            if (hangarCollection != null) {
                hangarCollection.getValue(selectedItem).draw(g);
            }
        }
    }

    public HangarCollection getHangarCollection() {
        return hangarCollection;
    }
    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

}


