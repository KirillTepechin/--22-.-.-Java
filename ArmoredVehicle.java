import java.awt.*;

public class ArmoredVehicle extends Vehicle {
    protected int carWeight = 90;
    protected int carHeight = 50;
    public ArmoredVehicle(int maxSpeed, float weight, Color mainColor)
    {
        this.maxSpeed = maxSpeed;
        this.weight = weight;
        this.mainColor = mainColor;
    }
    protected ArmoredVehicle(int maxSpeed, float weight, Color mainColor, int carWidth, int
            carHeight)
    {
        this.maxSpeed = maxSpeed;
        this.weight = weight;
        this.mainColor= mainColor;
        this.carWeight = carWidth;
        this.carHeight = carHeight;
    }


    public void drawTransport(Graphics g) {
        Graphics2D g2d =(Graphics2D)g;

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

    }

    @Override
    public void moveTransport(Direction direction) {
        float step = getMaxSpeed() * 100 / getWeight();
        switch (direction)
        {
            // вправо
            case RIGHT:
                if (startPosX + step < pictureWidth - carWeight)
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
                if (startPosY + step < pictureHeight - carHeight)
                {
                    startPosY += step;
                }
                break;
        }
    }
}
