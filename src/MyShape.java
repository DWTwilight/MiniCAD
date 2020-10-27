import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public abstract class MyShape extends JComponent {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static final Color HIGHLIGHTED_COLOR = Color.GREEN;
    public static final Color SELECTED_COLOR = Color.RED;

    class ShapeMouseListener extends MouseAdapter{
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
    }

    private Color color;
    private boolean isSelected;
    private boolean isPressed;
    private boolean isInside;
    private ShapeMouseListener mouseListener;
    private Point moveOrigin;

    public MyShape(){
        super();
        this.color = Color.BLACK;
        this.isSelected = false;
        this.isInside = false;
        this.mouseListener = new ShapeMouseListener();
        this.addMouseListener(this.mouseListener);
        this.addMouseMotionListener(this.mouseListener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        var g2d = (Graphics2D) g;
		// 平滑绘制（反锯齿）
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if(this.isInside && !this.isPressed){
            g2d.setColor(HIGHLIGHTED_COLOR);
        }
        else if(this.isSelected){
            g2d.setColor(SELECTED_COLOR);
        }
        
        var s = this.getShape();
        g2d.setStroke(new BasicStroke(5.0f));
        g2d.draw(s);
        g2d.setColor(this.color);
        g2d.fill(s);
    }

    protected abstract Shape getShape();
    protected abstract boolean checkMousePos(MouseEvent e);

    protected void onMousePressed(MouseEvent e){
        if(this.isInside){
            this.toggle();
            this.isPressed = true;
            this.moveOrigin = e.getPoint();
        }
        repaint();
    }   
    
    protected void onMouseReleased(MouseEvent e){
        this.isPressed = false;
    }   

    protected void onMouseMoved(MouseEvent e){
        this.isInside = checkMousePos(e);
        repaint();
    }   

    protected void onMouseDragged(MouseEvent e){
        if(this.isSelected){
            
            System.out.println("Dragged!");
        }
        repaint();
    }   

    protected void toggle(){
        this.isSelected = isSelected ? false : true;
    }

    protected void moveBy(double dx, double dy){
        var p = this.getLocation();
        p.translate((int)dx, (int)dy);
        this.setLocation(p);
    }

    protected void setColor(Color color){
        this.color = color;
    }
}
