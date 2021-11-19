import java.awt.*;

public class AntiAircraftGun
{
    private boolean radiolocation;
    private boolean gun;
    private Guns guns;
    private float startPosX;
    private float startPosY;
    private int pictureWidth;
    private int pictureHeight;
    private final int AAG_WIDTH = 100;
    private final int AAG_HEIGHT = 60;
    private int maxSpeed;
    private float weight;
    private Color mainColor;
    private Color dopColor;

    public void init(int maxSpeed, float weight, Color mainColor, Color dopColor,
                     boolean gun, boolean radiolocation)
    {
        this.setMaxSpeed(maxSpeed);
        this.setWeight(weight);
        this.setMainColor(mainColor);
        this.setDopColor(dopColor);
        this.setGun(gun);
        this.setRadiolocation(radiolocation);
        guns=new Guns();
        int rnd= 1 + (int) (Math.random() * 3);
        guns.setAmount(rnd*2);

    }
    public void setPosition(int x, int y, int width, int height)
    {
        startPosX = x;
        startPosY = y;
        pictureWidth = width;
        pictureHeight = height;
    }
    public void moveTransport(Direction direction)
    {
        float step = getMaxSpeed() * 100 / getWeight();
        switch (direction)
        {
            // вправо
            case RIGHT:
                if (startPosX + step < pictureWidth - AAG_WIDTH)
                {
                    startPosX += step;
                }
                break;
            //влево
            case LEFT:
                if (startPosX - step > 0)
                {
                    startPosX -= step;
                }
                break;
            //вверх
            case UP:
                if (startPosY - step > 0)
                {
                    startPosY -= step;
                }
                break;
            //вниз
            case DOWN:
                if (startPosY + step < pictureHeight - AAG_HEIGHT)
                {
                    startPosY += step;
                }
                break;
        }

    }
    public void DrawTransport(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;

        g2d.setColor(mainColor);
        g2d.setStroke(new BasicStroke(3));
        Color BackgroundColor =new Color(238,238,238);
        int shiftX = 30;
        int shiftY = 30;

        //Башня
        g2d.fillRect((int)startPosX - 3 + shiftX, (int)startPosY - 10 + shiftY, 60, 20);
        //Корпус
        g2d.fillRect((int)startPosX - 45 + shiftX, (int)startPosY + 5 + shiftY, 140 , 15);
        //Гусеницы
        g2d.drawOval((int)startPosX - 51 + shiftX, (int)startPosY + 18 + shiftY, 25, 24);
        g2d.drawOval((int)startPosX + 75 + shiftX, (int)startPosY + 18 + shiftY, 25, 24);
        g2d.setColor(BackgroundColor);
        g2d.fillRect((int)startPosX - 44 + shiftX, (int)startPosY + 20 + shiftY, 137, 23);
        g2d.setColor(mainColor);
        g2d.drawLine((int)startPosX - 45 + shiftX, (int)startPosY + 42 + shiftY, (int)startPosX + 95+shiftX , (int)startPosY + 42 + shiftY);

        g2d.drawOval((int)startPosX - 46 + shiftX, (int)startPosY + 19 + shiftY, 20, 20);
        int t = 25;
        for (int i = 0; i < 4; i++)
        {
            g2d.drawOval((int)startPosX - 43 + t + shiftX, (int)startPosY + 29 + shiftY, 10, 10);
            t += 25;
        }
        g2d.drawOval((int)startPosX + 75 + shiftX, (int)startPosY + 19 + shiftY, 20, 20);
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

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Color getMainColor() {
        return mainColor;
    }

    public void setMainColor(Color mainColor) {
        this.mainColor = mainColor;
    }

    public Color getDopColor() {
        return dopColor;
    }

    public void setDopColor(Color dopColor) {
        this.dopColor = dopColor;
    }
}
