
import java.awt.*;

public class AntiAircraftGun extends ArmoredVehicle
{
    private boolean radiolocation;
    private boolean gun;
    private GunsInterface guns;
    private Color dopColor;

    protected AntiAircraftGun(int maxSpeed, float weight, Color mainColor, Color dopColor,
                              boolean gun, boolean radiolocation) {
        super(maxSpeed, weight, mainColor, 100, 60);
        this.dopColor = dopColor;
        this.radiolocation = radiolocation;
        this.gun = gun;
    }
    public void drawTransport(Graphics g)
    {
        super.drawTransport(g);
        Graphics2D g2d = (Graphics2D)g;
        int shiftX = 30;
        int shiftY = 30;
        //Пушка
        if (gun)
        {
            guns.drawGuns(g2d,dopColor,startPosX,startPosY);
        }
        //Радиолокатор
        if (radiolocation)
        {
            g2d.setColor(dopColor);
            g2d.setStroke(new BasicStroke(4));
            g2d.drawLine((int)startPosX +shiftX, (int)startPosY + shiftY,(int) startPosX+shiftX,(int) startPosY - 20 + shiftY);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine((int)startPosX + 10+shiftX,(int) startPosY + shiftY,(int) startPosX+shiftX,(int) startPosY - 20 + shiftY);

            Polygon p=new Polygon();
            p.addPoint((int)(startPosX + 5 + shiftX), (int)(startPosY - 15 + shiftY));
            p.addPoint(((int)(startPosX - 5 + shiftX)), (int)(startPosY - 25 + shiftY));
            p.addPoint((int)(startPosX +shiftX), (int)(startPosY - 30 + shiftY));
            p.addPoint((int)(startPosX + 10 +shiftX), (int)(startPosY - 20 + shiftY));

            g2d.fillPolygon(p);

            p=new Polygon();
            p.addPoint((int)(startPosX + 15+shiftX), (int)(startPosY - 15 + shiftY));
            p.addPoint((int)(startPosX + 15+shiftX), (int)(startPosY - 25 + shiftY));
            p.addPoint((int)(startPosX + 5+shiftX), (int)(startPosY - 35 + shiftY));
            p.addPoint((int)(startPosX - 5+shiftX), (int)(startPosY - 35 + shiftY));

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

}
