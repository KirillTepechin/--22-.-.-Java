import java.awt.*;

public class GunsOne implements GunsInterface {
    private GunsCount guns;
    public void setAmount(int amount) {
        switch (amount) {
            case 4 : guns = GunsCount.FOUR;break;
            case 6 : guns = GunsCount.SIX;break;
            default : guns = GunsCount.TWO;break;
        }
    }
    public void drawGuns(Graphics2D g, Color color, float x, float y) {
        int shiftX = 30;
        int shiftY = 30;
        switch (guns) {
            case TWO:
                drawTwo(g,color,x,y);
                break;
            case FOUR:
                drawFour(g,color,x,y);
                break;
            case SIX:
                drawSix(g,color,x,y);
                break;
        }
    }
    public void drawTwo(Graphics2D g, Color color, float x, float y){
        int shiftX = 30;
        int shiftY = 30;

        g.setStroke(new BasicStroke(3));
        g.setColor(color);

        g.drawLine((int)x + 12 + 2 + shiftX, (int)y - 5 + 2 + shiftY, (int)x + 37+ 2 + shiftX, (int)y - 15 + 2 + shiftY);
        g.drawLine((int)x + 15 + shiftX, (int)y + shiftY, (int)x + 70 + shiftX, (int)y - 25 + shiftY);

        g.drawLine((int)x + 12 + 2 + 10 + shiftX, (int)y - 5 + 2 + shiftY, (int)x + 37+ 2 + 10  + shiftX, (int)y - 15 + 2 + shiftY);
        g.drawLine((int)x + 15 + 10+ shiftX, (int)y + shiftY, (int)x + 70+ 10 + shiftX, (int)y - 25 + shiftY);
    }
    public void drawFour(Graphics2D g, Color color, float x, float y){
        int shiftX = 30;
        int shiftY = 30;

        drawTwo(g, color, x, y);
        g.drawLine((int)x + 12 + 2-10 + shiftX, (int)y - 5 + 2 + shiftY, (int)x + 37+ 2-10 + shiftX, (int)y - 15 + 2 + shiftY);
        g.drawLine((int)x + 15-10 + shiftX, (int)y + shiftY, (int)x + 70 -10+ shiftX, (int)y - 25 + shiftY);

        g.drawLine((int)x + 12 + 2 + 10 +10 + shiftX, (int)y - 5 + 2 + shiftY, (int)x + 37+ 2 + 10+10  + shiftX, (int)y - 15 + 2 + shiftY);
        g.drawLine((int)x + 15 + 10+10+ shiftX, (int)y + shiftY, (int)x + 70+ 10+10 + shiftX, (int)y - 25 + shiftY);
    }
    public void drawSix(Graphics2D g, Color color, float x, float y){
        int shiftX = 30;
        int shiftY = 30;

        drawFour(g,color,x,y);
        g.drawLine((int)x + 12 + 2 + 10 +10 + shiftX, (int)y - 5 + 2 +7 + shiftY, (int)x + 37+ 2 + 10+10  + shiftX, (int)y - 15 + 2+7 + shiftY);
        g.drawLine((int)x + 15 + 10+10+ shiftX, (int)y+7 + shiftY, (int)x + 70+ 10+10 + shiftX, (int)y - 25+7 + shiftY);

        g.drawLine((int)x + 12 + 2 + 10 +10 + shiftX, (int)y - 5 + 2 +15 + shiftY, (int)x + 37+ 2 + 10+10  + shiftX, (int)y - 15 + 2+15 + shiftY);
        g.drawLine((int)x + 15 + 10+10+ shiftX, (int)y+15 + shiftY, (int)x + 70+ 10+10 + shiftX, (int)y - 25+15 + shiftY);
    }
    @Override
    public String toString() {
        return GunsOne.class.getSimpleName() + '.' + (guns.ordinal()+1)*2;
    }
}
