import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class MyCircle extends MyShape {
    private static final long serialVersionUID = 1L;

    public MyCircle(Point origin, Color color, int radius) {
        super();
        origin.translate(-radius, -radius);
        this.setLocation(origin);
        this.setSize(2 * radius, 2 * radius);
        this.setColor(color);
    }

    @Override
    protected Shape getShape() {
        return new Arc2D.Double(MyShape.BOARDER_WIDTH / 2, MyShape.BOARDER_WIDTH / 2, this.getDrawWidth(), this.getDrawWidth(), 0, 360, Arc2D.PIE);
    }

    @Override
    protected boolean checkMousePos(MouseEvent e) {
        return (e.getPoint().distance(new Point((int)this.getRadius(), (int)this.getRadius()))) <= this.getRadius();
    }

    public double getDrawWidth(){
        return this.getWidth() - MyShape.BOARDER_WIDTH;
    }

    public double getRadius(){
        return this.getWidth() / 2.0;
    }
}
