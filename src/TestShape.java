import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.AttributeSet.ColorAttribute;
import java.awt.color.*;
import java.awt.event.MouseEvent;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.BasicStroke;

public class TestShape extends JComponent {
	private Color color;
	private MyMouseListener mouseListener;

	class MyMouseListener extends MouseAdapter {
		public MyMouseListener() {
			super();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// onMouseClicked();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			// super.mouseEntered(e);
			onMouseEntered();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			onMouseExited();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			onMouseMoved();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseDragged(e);
		}
	}

	public TestShape() {
		super();
		this.color = Color.BLACK;
		this.mouseListener = new MyMouseListener();

		this.addMouseListener(this.mouseListener);
		this.addMouseMotionListener(this.mouseListener);
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		//super.paintComponent(g);
		int width = this.getWidth();
		int height = this.getHeight();
		Graphics2D g2d = (Graphics2D) g;

		// 平滑绘制（反锯齿）
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// 算出一个2:1的最大矩形
		// Rectangle r = new Rectangle(0,0, 100, 50);
		int w = width;
		int h = width / 2;
		if (h > height) {
			h = height;
			w = height * 2;
		}
		Rectangle r = new Rectangle((width - w) / 2, (height - h) / 2, w, h);

		// 里面两个并排圆， 外层轮廓为曲线
		// 左半区
		Rectangle r1 = new Rectangle(r.x, r.y, r.width / 2, r.height);
		// 右半区
		Rectangle r2 = new Rectangle(r.x + r.width / 2, r.y, r.width / 2, r.height);

		// 绘制外部轮廓线
		Shape arc1 = new Arc2D.Double(r1, 90, 180, Arc2D.OPEN);
		Shape arc2 = new Arc2D.Double(r2, 270, 180, Arc2D.OPEN);

		Path2D outline = new Path2D.Double(); // 外轮廓，使用拼装路径
		outline.append(arc1.getPathIterator(null), false);
		outline.append(arc2.getPathIterator(null), true); // 右半圆弧
		outline.closePath();
		g2d.setStroke(new BasicStroke(10.0f));
		g2d.setPaint(Color.RED);
		g2d.draw(outline);
		g2d.setPaint(this.color);
		g2d.fill(outline);
	}

	@Override
	protected void processMouseEvent(MouseEvent e) {
		// TODO Auto-generated method stub
		super.processMouseEvent(e);
		System.out.println("Mouse Event: " + e.getID());
	}

	@Override
	protected void processMouseMotionEvent(MouseEvent e) {
		// TODO Auto-generated method stub
		super.processMouseMotionEvent(e);
		//System.out.println("Mouse Event: " + e.getID());
	}

	private void onMouseClicked() {
		this.color = Color.RED;
		this.repaint();
	}

	private void onMouseEntered() {
		this.color = Color.GREEN;
		this.repaint();
		System.out.println("Entered");
	}

	private void onMouseExited() {
		this.color = Color.BLACK;
		this.repaint();
	}

	private void onMouseMoved() {
		System.out.println("Moved!");
	}
}
