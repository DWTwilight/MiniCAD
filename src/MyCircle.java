import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class MyCircle extends MyShape {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private double radius;

    public MyCircle(Point origin, Color color, double radius){
        super();
        this.radius = radius;
        origin.translate(-(int)radius, -(int)radius);
        this.setLocation(origin);
        this.setSize((int)(2*radius), (int)(2*radius));
        this.setColor(color);
    }

    @Override
    protected Shape getShape() {
        // TODO Auto-generated method stub
        return new Arc2D.Double(this.getX(), this.getY(), radius * 2, radius * 2, 0, 360, Arc2D.PIE);
    }

    @Override
    protected boolean checkMousePos(MouseEvent e) {
        // TODO Auto-generated method stub
        return e.getPoint().distance(this.getABSOrigin()) <= radius;
    }

    private Point getABSOrigin(){
        var p = this.getLocation();
        p.translate((int)radius, (int)radius);
        return p;
    }

}
