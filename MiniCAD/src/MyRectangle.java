import java.awt.*;
import java.awt.event.*;

public class MyRectangle extends MyShape {
    private static final long serialVersionUID = 1L;

    public MyRectangle(Point center, Color color, int width, int height) {
        super();
        center.translate(-width / 2, -height / 2);
        this.setLocation(center);
        this.setSize(width, height);
        this.setColor(color);
    }

    public MyRectangle(int x, int y, Color color, int width, int height){
        super();
        this.setLocation(x, y);
        this.setSize(width, height);
        this.setColor(color);
    }

    @Override
    protected Shape getShape() {
        return new Rectangle((int)(MyShape.BOARDER_WIDTH / 2), (int)(MyShape.BOARDER_WIDTH / 2), this.getDrawWidth(), this.getDrawHeight());
    }

    @Override
    protected boolean checkMousePos(MouseEvent e) {
        return e.getX() >= 0 && e.getX() <= this.getWidth() && e.getY() >= 0 && e.getY() <= this.getHeight();
    }
    
    private int getDrawWidth(){
        return this.getWidth() - (int)MyShape.BOARDER_WIDTH;
    }

    private int getDrawHeight(){
        return this.getHeight() - (int)MyShape.BOARDER_WIDTH;
    }
}
