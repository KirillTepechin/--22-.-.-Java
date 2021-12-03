import java.awt.*;

public class GunsThree implements GunsInterface{
    private GunsCount guns;
    public void setAmount(int amount) {
        switch (amount) {
            case 4 : guns = GunsCount.FOUR; break;
            case 6 : guns = GunsCount.SIX; break;
            default : guns = GunsCount.TWO; break;
        }
    }
    public void drawGuns(Graphics2D g, Color color, float x, float y) {

        switch (guns){
            case TWO :
                drawTwo(g,color,x,y);
                break;
            case FOUR:
                drawFour(g,color,x,y);
                break;
            case SIX :
                drawSix(g,color,x,y);
                break;
        }
    }
    public void drawTwo(Graphics2D g, Color color, float x, float y){
        int shiftX = 30;
        int shiftY = 30;
        g.setColor(color);
        g.fillRect((int)x+shiftX-2,(int)y+shiftY+1,30,20);

        g.setStroke(new BasicStroke(2));
        g.drawLine((int)x+shiftX-2+5,(int)y+shiftY,(int)x+shiftX-2+10+15,(int)y+shiftY-50);
        g.drawLine((int)x+shiftX-2+5+5+5,(int)y+shiftY,(int)x+shiftX-2+10+15+5+5,(int)y+shiftY-50);
    }
    public void drawFour(Graphics2D g, Color color, float x, float y){
        int shiftX = 30;
        int shiftY = 30;
        drawTwo(g,color,x,y);
        g.drawLine((int)x+shiftX-2+5+5+5,(int)y+shiftY,(int)x+shiftX-2+10+15+5+5,(int)y+shiftY-50);

        g.drawLine((int)x+shiftX-2+5+10,(int)y+shiftY+10,(int)x+shiftX-2+5+10+30,(int)y+shiftY+10-20);

        g.drawLine((int)x+shiftX-2+5+10,(int)y+shiftY+10+10,(int)x+shiftX-2+5+10+30,(int)y+shiftY+10-20+10);
    }
    public void drawSix(Graphics2D g, Color color, float x, float y){
        int shiftX = 30;
        int shiftY = 30;
        drawFour(g,color,x,y);
        g.drawLine((int)x+shiftX-2+5+5,(int)y+shiftY,(int)x+shiftX-2+10+15+5,(int)y+shiftY-50);
        g.drawLine((int)x+shiftX-2+5+10,(int)y+shiftY+10+5,(int)x+shiftX-2+5+10+30,(int)y+shiftY+10-20+5);
        g.drawLine((int)x+shiftX-2+5+10,(int)y+shiftY+10+10,(int)x+shiftX-2+5+10+30,(int)y+shiftY+10-20+10);
    }
}
