import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class MyShape extends JComponent {
    private static final long serialVersionUID = 1L;
    public static final Color HIGHLIGHTED_COLOR = new Color(0xDB7093);
    public static final Color SELECTED_COLOR = new Color(0xFF7F50);
    public static final float BOARDER_WIDTH = 6.0f;
    public static final int MIN_WIDTH = (int) (2.5 * BOARDER_WIDTH);
    public static final double ZOOM_IN_RATIO = 1.1;
    public static final double ZOOM_OUT_RATIO = 0.9;

    public enum ShapeType {
        NULL, Circle, Rectangle, Line, Text
    }

    private Color color;
    private boolean isSelected;
    private boolean isPressed;
    private boolean isInside;
    private boolean isDragging;
    private Point moveOrigin;

    public MyShape() {
        super();
        this.color = Color.BLACK;
        this.isSelected = false;
        this.isInside = false;
        this.isDragging = false;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public boolean isInside() {
        return isInside;
    }

    public boolean isDragging() {
        return isDragging;
    }

    public void manualProcessMouseEvent(MouseEvent e) {
        var le = this.getLocalEvent(e);
        switch (le.getID()) {
            case MouseEvent.MOUSE_MOVED:
                this.onMouseMoved(le);
                break;
            case MouseEvent.MOUSE_PRESSED:
                this.onMouseMoved(le);
                if (this.isInside) {
                    this.onMousePressed(le);
                }
                break;
            case MouseEvent.MOUSE_RELEASED:
                this.onMouseReleased(le);
                break;
            case MouseEvent.MOUSE_EXITED:
                this.onMouseExited(le);
                break;
            case MouseEvent.MOUSE_DRAGGED:
                this.onMouseDragged(le);
                break;
            case MouseEvent.MOUSE_WHEEL:
                this.onMouseWheelMoved((MouseWheelEvent) le);
                break;
            default:
                break;
        }
        if(le.isConsumed()){
            e.consume();
        }
    }

    protected MouseEvent getLocalEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_WHEEL) {
            var we = (MouseWheelEvent) e;
            return new MouseWheelEvent(this, e.getID(), e.getWhen(), e.getModifiersEx(), e.getX() - this.getX(),
                    e.getY() - this.getY(), e.getClickCount(), e.isPopupTrigger(), we.getScrollType(),
                    we.getScrollAmount(), we.getWheelRotation());
        } else {
            return new MouseEvent(this, e.getID(), e.getWhen(), e.getModifiersEx(), e.getX() - this.getX(),
                    e.getY() - this.getY(), e.getClickCount(), e.isPopupTrigger(), e.getButton());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        var g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (this.isInside && !this.isPressed) {
            g2d.setColor(HIGHLIGHTED_COLOR);
        } else if (this.isSelected) {
            g2d.setColor(SELECTED_COLOR);
        } else {
            g2d.setColor(this.color);
        }

        var s = this.getShape();
        g2d.setStroke(new BasicStroke(BOARDER_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        g2d.draw(s);
        g2d.setColor(this.color);
        g2d.fill(s);
    }

    protected abstract Shape getShape();

    protected abstract boolean checkMousePos(MouseEvent e);

    protected void onMousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (this.isInside) {
                this.isPressed = true;
                this.moveOrigin = e.getPoint();
                repaint();
                e.consume();
            }
        }
    }

    protected void onMouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (this.isPressed) {
                if (this.isInside && !this.isDragging) {
                    this.toggle();
                }
                this.isPressed = false;
                this.isDragging = false;
                repaint();
                e.consume();
            }
        }
    }

    protected void onMouseMoved(MouseEvent e) {
        this.isInside = checkMousePos(e);
        repaint();
    }

    protected void onMouseDragged(MouseEvent e) {
        if (this.isSelected && this.isPressed) {
            this.isDragging = true;

            var p = this.getLocation();
            p.translate((int) (e.getX() - moveOrigin.getX()), (int) (e.getY() - moveOrigin.getY()));
            this.setLocation(p);

            repaint();
            e.consume();
        }
    }

    protected void onMouseExited(MouseEvent e) {
        this.isInside = false;
        repaint();
    }

    protected void onMouseWheelMoved(MouseWheelEvent e) {
        if (this.isSelected) {
            var ratio = e.getWheelRotation() == -1 ? ZOOM_IN_RATIO : ZOOM_OUT_RATIO;
            var newWidth = (int) (this.getWidth() * ratio);
            var newHeight = (int) (this.getHeight() * ratio);
            if (newWidth >= MIN_WIDTH && newHeight >= MIN_WIDTH) {
                var dw = newWidth - this.getWidth();
                var dh = newHeight - this.getHeight();
                var pos = this.getLocation();
                pos.translate(-dw / 2, -dh / 2);
                this.setLocation(pos);
                this.setSize(newWidth, newHeight);
                repaint();
            }
        }
    }

    protected void toggle() {
        this.isSelected = !isSelected;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
