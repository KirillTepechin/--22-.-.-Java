import java.awt.*;

public class Hangar<T extends Transport, G extends GunsInterface> {
    public T[] places;

    private final int pictureWidth;

    private final int pictureHeight;

    private final int placeSizeWidth = 210;

    private final int placeSizeHeight = 80;

    public Hangar(int picWidth, int picHeight) {
        int width = picWidth / placeSizeWidth;
        int height = picHeight / placeSizeHeight;
        places = (T[]) new Transport[width * height];
        pictureWidth = picWidth;
        pictureHeight = picHeight;
    }

    public int add(Hangar<T, G> h, T armoredVehicle) {
        for (int i = 0; i < h.places.length; i++) {
            if (h.places[i] == null) {
                h.places[i] = armoredVehicle;
                h.places[i].setPosition(20 + i % 3 * placeSizeWidth + 5, i / 3 * (placeSizeHeight + 9) + 12, pictureWidth, pictureHeight);
                return i;
            }
        }
        return -1;
    }

    public T delete(Hangar<T, G> h, int index) {
        if (index >= h.places.length || index < 0) {
            return null;
        }
        T tmp = h.places[index];
        h.places[index] = null;
        return tmp;
    }

    private boolean greaterThan(Hangar<T, G> hangar, ArmoredVehicle armoredVehicle) {
        float maxWeight = 0;
        for (T tr : hangar.places) {
            ArmoredVehicle aV = (ArmoredVehicle) tr;
            if (aV.weight > maxWeight) {
                maxWeight = aV.weight;
            }
        }
        return maxWeight > armoredVehicle.weight;
    }

    private boolean lessThan(Hangar<T, G> hangar, ArmoredVehicle armoredVehicle) {
        float maxWeight = 0;
        for (T tr : hangar.places) {
            ArmoredVehicle aV = (ArmoredVehicle) tr;
            if (aV.weight > maxWeight) {
                maxWeight = aV.weight;
            }
        }
        return maxWeight < armoredVehicle.weight;
    }

    public void draw(Graphics g) {
        drawMarking(g);
        for (int i = 0; i < places.length; i++) {
            if (places[i] != null) {
                places[i].drawTransport(g);
            }
        }
    }

    private void drawMarking(Graphics g) {
        int shift = 9;
        for (int i = 0; i < pictureWidth / placeSizeWidth; i++) {
            for (int j = 0; j < pictureHeight / placeSizeHeight + 1; ++j) {//линия разметки места
                g.drawLine(i * placeSizeWidth, j * (placeSizeHeight + shift), i * placeSizeWidth + placeSizeWidth / 2 + 50, j * (placeSizeHeight + shift));
            }
            g.drawLine(i * placeSizeWidth, 0, i * placeSizeWidth, (pictureHeight / placeSizeHeight) * (placeSizeHeight + shift));

        }
    }

}
