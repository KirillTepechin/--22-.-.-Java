import java.awt.*;

public abstract class Vehicle implements Transport {
    protected float startPosX;
    protected float startPosY;
    protected int pictureWidth;
    protected int pictureHeight;
    public int maxSpeed;
    public float weight;
    public Color mainColor;
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
    public void setPosition(int x, int y, int width, int height)
    {
        startPosX=x;
        startPosY=y;
        pictureWidth=width;
        pictureHeight=height;
    }
    public abstract void drawTransport(Graphics g);
    public abstract void moveTransport(Direction direction);
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
