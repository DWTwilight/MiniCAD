import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import java.io.*;

public class MyPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final float STROKE_WIDTH = 4.0f;
    private static final BasicStroke STROKE = new BasicStroke(STROKE_WIDTH, BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_MITER, 10.0f, new float[] { 5.0f, 5.0f }, 0.0f);
    private static final Color TIP_COLOR = new Color(0x5F9EA0);

    class MyListener extends MouseAdapter implements Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub
            onMousePressed(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub
            onMouseReleased(e);
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            onMouseMoved(e);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            // TODO Auto-generated method stub
            onMouseDragged(e);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            onMouseExited(e);
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            onMouseWheelMoved(e);
        }
    }

    private boolean createMode;
    private MyShape.ShapeType shape;
    private boolean confirmFlag;
    private Point origin;
    private Point mousePos;
    private MyListener mouseListener;
    private Color currentColor;

    public MyPanel() {
        super();
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        this.createMode = false;
        this.shape = MyShape.ShapeType.NULL;
        this.confirmFlag = false;
        this.currentColor = Color.BLACK;
        this.mouseListener = new MyListener();
        this.addMouseListener(this.mouseListener);
        this.addMouseMotionListener(this.mouseListener);
        this.addMouseWheelListener(this.mouseListener);
    }

    public void changeSelectedColor(Color color) {
        this.currentColor = color;
        for (var component : this.getComponents()) {
            var shape = (MyShape) component;
            if (shape.isSelected()) {
                shape.setColor(this.currentColor);
                shape.repaint();
            }
        }
    }

    public void deleteSelected() {
        var deleteList = new ArrayList<Component>();
        for (var component : this.getComponents()) {
            var shape = (MyShape) component;
            if (shape.isSelected()) {
                deleteList.add(component);
            }
        }
        for (var component : deleteList) {
            this.remove(component);
        }
        repaint();
    }

    public void clearAll(){
        for(var component : this.getComponents()){
            this.remove(component);
        }
        repaint();
    }

    public void setCreateMode(MyShape.ShapeType shape) {
        this.exitCreateMode();
        if (!this.createMode) {
            this.createMode = true;
            this.shape = shape;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        var g2d = (Graphics2D) g;
        g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
        if (confirmFlag) {
            g2d.setColor(TIP_COLOR);
            g2d.setStroke(STROKE);
            switch (this.shape) {
                case Circle:
                    var radius = this.origin.distance(this.mousePos);
                    var x = this.origin.getX() - radius - STROKE_WIDTH / 2;
                    var y = this.origin.getY() - radius - STROKE_WIDTH / 2;
                    g2d.drawArc((int) x, (int) y, (int) (radius * 2), (int) (radius * 2), 0, 360);
                    break;
                case Rectangle:
                    var width = Math.abs(this.origin.getX() - this.mousePos.getX());
                    var height = Math.abs(this.origin.getY() - this.mousePos.getY());
                    x = Math.min(this.origin.getX(), this.mousePos.getX());
                    y = Math.min(this.origin.getY(), this.mousePos.getY());
                    g2d.drawRect((int) x, (int) y, (int) width, (int) height);
                    break;
                case Line:
                    g2d.drawLine((int) this.origin.getX(), (int) this.origin.getY(), (int) this.mousePos.getX(),
                            (int) this.mousePos.getY());
                    break;
                default:
                    break;
            }
        }
    }

    private void manualDispatch(MouseEvent e) {
        for (var component : this.getComponents()) {
            var shape = (MyShape) component;
            shape.manualProcessMouseEvent(e);
            if (e.isConsumed()) {
                break;
            }
        }
    }

    private void onMousePressed(MouseEvent e) {
        if (!this.createMode) {
            this.manualDispatch(e);
        }
    }

    private void onMouseReleased(MouseEvent e) {
        if (this.createMode) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                this.exitCreateMode();
            } else if (e.getButton() == MouseEvent.BUTTON1) {
                if (this.confirmFlag || this.shape == MyShape.ShapeType.Text) {
                    this.mousePos = e.getPoint();
                    switch (this.shape) {
                        case Circle:
                            var radius = this.origin.distance(this.mousePos);
                            this.add(new MyCircle(this.origin, this.currentColor, (int) radius));
                            break;
                        case Rectangle:
                            var width = Math.abs(this.origin.getX() - this.mousePos.getX());
                            var height = Math.abs(this.origin.getY() - this.mousePos.getY());
                            var x = Math.min(this.origin.getX(), this.mousePos.getX());
                            var y = Math.min(this.origin.getY(), this.mousePos.getY());
                            this.add(new MyRectangle((int) x, (int) y, this.currentColor, (int) width, (int) height));
                            break;
                        case Line:
                            this.add(new MyLine(origin, mousePos, this.currentColor));
                            break;
                        default:
                            var content = JOptionPane.showInputDialog(this, "Enter text: ", "Enter text",
                                    JOptionPane.QUESTION_MESSAGE);
                            if (content != null) {
                                this.add(new MyText(mousePos, content, MyText.DEFAULT_FONT, this.currentColor));
                            }
                            break;
                    }
                    this.exitCreateMode();
                } else {
                    this.origin = e.getPoint();
                    confirmFlag = true;
                }
            }
        } else {
            this.manualDispatch(e);
        }
    }

    private void onMouseMoved(MouseEvent e) {
        if (this.confirmFlag) {
            this.mousePos = e.getPoint();
            repaint();
        }
        if (!this.createMode) {
            this.manualDispatch(e);
        }
    }

    private void onMouseDragged(MouseEvent e) {
        if (!this.createMode) {
            this.manualDispatch(e);
        }
    }

    private void onMouseExited(MouseEvent e) {
        if (!this.createMode) {
            this.manualDispatch(e);
        }
    }

    private void onMouseWheelMoved(MouseWheelEvent e) {
        if (!this.createMode) {
            this.manualDispatch(e);
        }
    }

    public void exitCreateMode() {
        this.createMode = false;
        this.confirmFlag = false;
        repaint();
    }
}
