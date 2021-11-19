import java.awt.*;

public class GunsTwo implements GunsInterface{
    private GunsCount guns;

    public void setAmount(int amount) {
        switch (amount) {
            case 4 : guns = GunsCount.FOUR;break;
            case 6 : guns = GunsCount.SIX;break;
            default : guns = GunsCount.TWO;break;
        }
    }
    public void drawGuns(Graphics2D g, Color color, float x, float y) {

        switch (guns){
            case TWO :
                drawTwo(g,color,x,y);
                break;
            case FOUR :
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
        g.setStroke(new BasicStroke(4));
        g.setColor(color);

        g.drawLine((int)x + 15 +30 + shiftX, (int)y-10 + shiftY, (int)x + 70 + shiftX, (int)y-20 + shiftY);
        g.drawLine((int)x + 15 +30 + shiftX, (int)y + shiftY, (int)x + 70 + shiftX, (int)y-10 + shiftY);
    }
    public void drawFour(Graphics2D g, Color color, float x, float y){
        int shiftX = 30;
        int shiftY = 30;
        drawTwo(g,color,x,y);
        g.drawLine((int)x + shiftX-40,(int)y+shiftY+5,(int)x + shiftX-50,(int)y+shiftY-20+5);
        g.drawLine((int)x + shiftX-40+4,(int)y+shiftY+5,(int)x + shiftX-50,(int)y+shiftY-20+5);

        g.drawLine((int)x + shiftX-50,(int)y+shiftY-20+5,(int)x + shiftX-50+15,(int)y+shiftY-20-15+5);
        g.drawLine((int)x + shiftX-50+4,(int)y+shiftY-20+5+4,(int)x + shiftX-50+15+4,(int)y+shiftY-20-15+5+4);
    }
    public void drawSix(Graphics2D g, Color color, float x, float y){
        int shiftX = 30;
        int shiftY = 30;
        drawFour(g,color,x,y);
        g.drawLine((int)x + shiftX-50+4+4,(int)y+shiftY-20+5+4+4,(int)x + shiftX-50+15+4+4,(int)y+shiftY-20-15+5+4+4);
        g.drawLine((int)x + shiftX-50+4+4+4,(int)y+shiftY-20+5+4+4+4,(int)x + shiftX-50+15+4+4+4,(int)y+shiftY-20-15+5+4+4+4);

    }
}
