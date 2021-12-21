import java.awt.*;
import java.util.Comparator;

public class ArmoredVehicleComparer implements Comparator<Vehicle> {
    public int compare(Vehicle x, Vehicle y) {
        if (x instanceof AntiAircraftGun &&(y instanceof ArmoredVehicle &&!(y instanceof AntiAircraftGun)))
        return 1;
        else if ((x instanceof ArmoredVehicle &&!(x instanceof AntiAircraftGun)) &&y instanceof AntiAircraftGun)
        return -1;
        else if (x instanceof AntiAircraftGun &&y instanceof AntiAircraftGun)
        return comparerAntiAircraftGun((AntiAircraftGun) x, (AntiAircraftGun) y);
        else return comparerArmoredVehicle((ArmoredVehicle) x, (ArmoredVehicle) y);

    }

    private int comparerArmoredVehicle(ArmoredVehicle x, ArmoredVehicle y) {
        if (x.maxSpeed != y.maxSpeed) {
            return Integer.compare(x.maxSpeed,y.maxSpeed);
        }
        if (x.weight != y.weight) {
            return Float.compare(x.weight,y.weight);
        }
        if (x.mainColor != y.mainColor) {
            return Integer.compare(x.mainColor.getRGB(), y.mainColor.getRGB());
        }
        return 0;
    }

    private int comparerAntiAircraftGun(AntiAircraftGun x, AntiAircraftGun y) {
        var res = comparerArmoredVehicle(x, y);
        if (res != 0) {
            return res;
        }
        if (x.dopColor != y.dopColor) {
            return Integer.compare(x.dopColor.getRGB(), y.dopColor.getRGB());
        }
        if (x.gun != y.gun) {
            return Boolean.compare(x.gun,y.gun);
        }
        if (x.radiolocation != y.radiolocation) {
            return Boolean.compare(x.radiolocation,y.radiolocation);
        }
        int xRealization=0;
        int yRealization=0;

        if(x.guns instanceof GunsOne)xRealization=1;
        else if(x.guns instanceof GunsTwo)xRealization=2;
        else if(x.guns instanceof GunsThree)xRealization=3;

        if(y.guns instanceof GunsOne)yRealization=1;
        else if(y.guns instanceof GunsTwo)yRealization=2;
        else if(y.guns instanceof GunsThree)yRealization=3;

        if(xRealization!=yRealization){
            return Integer.compare(xRealization, yRealization);
        }
        return 0;
    }
}