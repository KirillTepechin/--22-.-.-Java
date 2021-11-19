import java.awt.*;

public interface Transport {
    void setPosition(int x, int y, int width, int height);
    void moveTransport(Direction direction);
    void drawTransport(Graphics g);
}
