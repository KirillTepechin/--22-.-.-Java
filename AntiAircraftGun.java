import java.awt.*;
import java.lang.reflect.Field;
import java.util.Iterator;

public class AntiAircraftGun extends ArmoredVehicle{
    public boolean radiolocation;
    public boolean gun;
    public GunsInterface guns;
    public Color dopColor;

    protected AntiAircraftGun(int maxSpeed, float weight, Color mainColor, Color dopColor, boolean gun, boolean radiolocation) {
        super(maxSpeed, weight, mainColor, 100, 60);
        this.dopColor = dopColor;
        this.radiolocation = radiolocation;
        this.gun = gun;
    }

    public AntiAircraftGun(String info) {
        super(info);
        String[] strs = info.split(String.valueOf(separator));
        if (strs.length == 7) {
            maxSpeed = Integer.parseInt(strs[0]);
            weight = Float.parseFloat(strs[1]);
            mainColor = Color.decode(strs[2]);
            dopColor = Color.decode(strs[3]);
            gun = Boolean.parseBoolean(strs[4]);
            radiolocation = Boolean.parseBoolean(strs[5]);
            if (strs[6].contains("null")) {
                guns = null;
            } else {
                String[] gunInfo = strs[6].split("\\.");
                int amount = Integer.parseInt(gunInfo[1]);
                switch (gunInfo[0]) {
                    case "GunsOne": {
                        guns = new GunsOne();
                        break;
                    }
                    case "GunsTwo": {
                        guns = new GunsTwo();
                        break;
                    }
                    case "GunsThree": {
                        guns = new GunsThree();
                        break;
                    }
                }
                guns.setAmount(amount);
            }
        }
    }

    public void drawTransport(Graphics g) {
        super.drawTransport(g);
        Graphics2D g2d = (Graphics2D) g;
        int shiftX = 30;
        int shiftY = 30;
        //Пушка
        if (gun) {
            guns.drawGuns(g2d, dopColor, startPosX, startPosY);
        }
        //Радиолокатор
        if (radiolocation) {
            g2d.setColor(dopColor);
            g2d.setStroke(new BasicStroke(4));
            g2d.drawLine((int) startPosX + shiftX, (int) startPosY + shiftY, (int) startPosX + shiftX, (int) startPosY - 20 + shiftY);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine((int) startPosX + 10 + shiftX, (int) startPosY + shiftY, (int) startPosX + shiftX, (int) startPosY - 20 + shiftY);

            Polygon p = new Polygon();
            p.addPoint((int) (startPosX + 5 + shiftX), (int) (startPosY - 15 + shiftY));
            p.addPoint(((int) (startPosX - 5 + shiftX)), (int) (startPosY - 25 + shiftY));
            p.addPoint((int) (startPosX + shiftX), (int) (startPosY - 30 + shiftY));
            p.addPoint((int) (startPosX + 10 + shiftX), (int) (startPosY - 20 + shiftY));

            g2d.fillPolygon(p);

            p = new Polygon();
            p.addPoint((int) (startPosX + 15 + shiftX), (int) (startPosY - 15 + shiftY));
            p.addPoint((int) (startPosX + 15 + shiftX), (int) (startPosY - 25 + shiftY));
            p.addPoint((int) (startPosX + 5 + shiftX), (int) (startPosY - 35 + shiftY));
            p.addPoint((int) (startPosX - 5 + shiftX), (int) (startPosY - 35 + shiftY));

            g2d.fillPolygon(p);
        }

    }

    public boolean isRadiolocation() {
        return radiolocation;
    }

    public void setRadiolocation(boolean radiolocation) {
        this.radiolocation = radiolocation;
    }

    public boolean isGun() {
        return gun;
    }

    public void setGun(boolean gun) {
        this.gun = gun;
    }

    public Color getDopColor() {
        return dopColor;
    }

    public void setDopColor(Color dopColor) {
        this.dopColor = dopColor;
    }

    public void setGuns(GunsInterface guns) {
        this.guns = guns;
    }

    @Override
    public String toString() {
        return super.toString() + separator + dopColor.getRGB() + separator + radiolocation + separator + gun + separator + guns;
    }

    public boolean equals(AntiAircraftGun other) {
        if (!equals((ArmoredVehicle) other)) {
            return false;
        }
        if (gun != other.gun) {
            return false;
        }
        if (radiolocation != other.radiolocation) {
            return false;
        }
        if (dopColor != other.dopColor) {
            return false;
        }
        if(guns.getClass()!= other.guns.getClass()){
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AntiAircraftGun))
        {
            return false;
        }
            else
        {
            return equals((AntiAircraftGun)obj);
        }
    }

    @Override
    public int compareTo(Object o) {
        int res=super.compareTo(o);
        if(res==0){
            if (dopColor.getRGB() != ((AntiAircraftGun)o).dopColor.getRGB()) {
                if (dopColor.getRGB() > ((AntiAircraftGun)o).dopColor.getRGB()) {
                    return -1;
                }
                else return 1;
            }
            else if (gun != ((AntiAircraftGun)o).gun) {
                if (gun) {
                    return -1;
                }
                else return 1;
            }
            else if (radiolocation != ((AntiAircraftGun)o).radiolocation) {
                if (radiolocation) {
                    return -1;
                }
                else return 1;
            }
            int thisRealization=0;
            int otherRealization=0;

            if(guns instanceof GunsOne)thisRealization=1;
            else if(guns instanceof GunsTwo)thisRealization=2;
            else if(guns instanceof GunsThree)thisRealization=3;

            if(((AntiAircraftGun)o).guns instanceof GunsOne)otherRealization=1;
            else if(((AntiAircraftGun)o).guns instanceof GunsTwo)otherRealization=2;
            else if(((AntiAircraftGun)o).guns instanceof GunsThree)otherRealization=3;

            if(thisRealization!=otherRealization){
                if(thisRealization>otherRealization){
                    return -1;
                }
                else return 1;
            }
        }
        return 0;
    }

    @Override
    public Iterator<Object> iterator() {
        Iterator<Object> it = new Iterator<Object>() {

            private int current = -1;
            private int allProperties=6;

            @Override
            public boolean hasNext() {
                current++;
                if(guns!=null)
                return current<=allProperties;
                else return current<=allProperties-1;
            }

            @Override
            public String next() {
                if(current==0){
                    return Integer.toString(maxSpeed);
                }
                else if(current==1){
                    return Float.toString(weight);
                }
                else if(current==2) {
                    return Integer.toString(mainColor.getRGB());
                }
                if(current==3){
                    return Boolean.toString(gun);
                }
                else if(current==4){
                    return Boolean.toString(radiolocation);
                }
                else if(current==5) {
                    return Integer.toString(dopColor.getRGB());
                }
                return guns.getClass().getSimpleName();
                }

        };
        return it;
    }
}