import java.awt.*;
import java.awt.event.*;

public class MyText extends MyShape {
    private static final long serialVersionUID = 1L;
    public static final int MAX_FONT_SIZE = 72;
    public static final int MIN_FONT_SIZE = 5;
    public static final Font DEFAULT_FONT = new Font("宋体", Font.BOLD, 20);

    private Font currentFont;
    private String content;

    public MyText(Point origin, String content, Font font, Color color) {
        super();
        this.setLocation(origin);
        this.currentFont = font;
        this.content = content;
        this.setSize(this.getTextDimension());
        this.setColor(color);
    }

    @Override
    protected void paintComponent(Graphics g) {
        var g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (this.isInside() && !this.isPressed()) {
            g2d.setColor(HIGHLIGHTED_COLOR);
        } else if (this.isSelected()) {
            g2d.setColor(SELECTED_COLOR);
        } else {
            g2d.setColor(this.getColor());
        }
        g2d.setFont(this.currentFont);
        g2d.drawString(content, 0, this.getOffset());
    }

    @Override
    protected Shape getShape() {
        return null;
    }

    @Override
    protected boolean checkMousePos(MouseEvent e) {
        return e.getX() >= 0 && e.getX() <= this.getWidth() && e.getY() >= 0 && e.getY() <= this.getHeight();
    }

    @Override
    protected void onMouseWheelMoved(MouseWheelEvent e) {
        if(this.isSelected()){
            if(e.getWheelRotation() == 1){
                if(this.currentFont.getSize() > MIN_FONT_SIZE){
                    this.currentFont = new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize() - 1);
                }
                else{
                    return;
                }
            }
            else{
                if(this.currentFont.getSize() < MAX_FONT_SIZE){
                    this.currentFont = new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize() + 1);
                }
                else{
                    return;
                }
            }
            var newSize = this.getTextDimension();
            var dx = this.getWidth() - newSize.getWidth();
            var dy = this.getHeight() - newSize.getHeight();
            var pos = this.getLocation();
            pos.translate((int)dx / 2, (int)dy / 2);
            this.setLocation(pos);
            this.setSize(newSize);
            e.consume();
        }
    }

    private Dimension getTextDimension(){
        var metric = this.getFontMetrics(this.currentFont);
        var rec = metric.getStringBounds(this.content, null);
        return new Dimension((int)rec.getWidth() + 1, (int)rec.getHeight() + 1);
    }

    private int getOffset(){
        var metric = this.getFontMetrics(this.currentFont);
        return metric.getAscent();
    }
}
