import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class MyLine extends MyShape {
    private static final long serialVersionUID = 1L;
    public static final float MAX_LINE_WIDTH = 20.0f;
    public static final float MIN_LINE_WIDTH = 2.0f;

    private float lineWidth;
    private boolean type;// true if \, false if /

    public MyLine(Point startPoint, Point endPoint, Color color) {
        super();
        this.lineWidth = 4.0f;

        if (startPoint.getX() > endPoint.getX()) {
            var temp = startPoint;
            startPoint = endPoint;
            endPoint = temp;
        }

        var width = (int) (Math.abs(startPoint.getX() - endPoint.getX()) + MAX_LINE_WIDTH);
        var height = (int) (Math.abs(startPoint.getY() - endPoint.getY()) + MAX_LINE_WIDTH);
        this.setSize(width, height);

        if (startPoint.getY() < endPoint.getY()) {
            this.type = true;
            startPoint.translate(-(int) MAX_LINE_WIDTH / 2, -(int) MAX_LINE_WIDTH / 2);
            this.setLocation(startPoint);
        } else {
            this.type = false;
            startPoint.translate(-(int) MAX_LINE_WIDTH / 2, -height + (int) MAX_LINE_WIDTH / 2);
            this.setLocation(startPoint);
        }

        this.setColor(color);
    }

    @Override
    protected void paintComponent(Graphics g) {
        var g2d = (Graphics2D) g;
        // 平滑绘制（反锯齿）
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (this.isInside() && !this.isPressed()) {
            g2d.setColor(HIGHLIGHTED_COLOR);
        } else if (this.isSelected()) {
            g2d.setColor(SELECTED_COLOR);
        } else {
            g2d.setColor(this.getColor());
        }

        var s = this.getShape();
        g2d.setStroke(new BasicStroke(this.lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        g2d.draw(s);
    }

    @Override
    protected Shape getShape() {
        return new Line2D.Double(this.getStartPoint(), this.getEndPoint());
    }

    @Override
    protected boolean checkMousePos(MouseEvent e) {
        var sp = this.getStartPoint();
        var x1 = sp.getX();
        var y1 = sp.getY();
        var ep = this.getEndPoint();
        var x2 = ep.getX();
        var y2 = ep.getY();

        var a = y1 - y2;
        var b = x2 - x1;
        var c = x1 * y2 - x2 * y1;

        var dis = Math.abs(a * e.getX() + b * e.getY() + c) / Math.sqrt(a * a + b * b);

        return dis <= this.lineWidth;
    }

    @Override
    protected void onMousePressed(MouseEvent e) {
        super.onMousePressed(e);
        if(this.isSelected()){
            if (e.getButton() == MouseEvent.BUTTON3) {
                if (this.lineWidth > MIN_LINE_WIDTH) {
                    this.lineWidth -= 2.0f;
                    repaint();
                }
                e.consume();
            } else if (e.getButton() == MouseEvent.BUTTON2) {
                if(this.lineWidth < MAX_LINE_WIDTH){
                    this.lineWidth += 2.0f;
                    repaint();
                }
                e.consume();
            }
        }
    }

    private Point getStartPoint() {
        if (type) {
            return new Point((int) MAX_LINE_WIDTH / 2, (int) MAX_LINE_WIDTH / 2);
        } else {
            return new Point((int) MAX_LINE_WIDTH / 2, this.getHeight() - (int) MAX_LINE_WIDTH / 2);
        }
    }

    private Point getEndPoint() {
        if (type) {
            return new Point(this.getWidth() - (int) MAX_LINE_WIDTH / 2, this.getHeight() - (int) MAX_LINE_WIDTH / 2);
        } else {
            return new Point(this.getWidth() - (int) MAX_LINE_WIDTH / 2, (int) MAX_LINE_WIDTH / 2);
        }
    }
}
