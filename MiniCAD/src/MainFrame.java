import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.Dimension;
import javax.swing.JSplitPane;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Panel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.io.*;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	public static final String DESCRIPTION = "CAD Files(*.cad)";
	public static final String POSFIX = ".cad";
	private JPanel contentPane;
	private JSplitPane splitPane;
	private MyPanel shapePanel;
	private Panel pnlCurrentColor;
	private JFileChooser fileChooser;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				return f.getName().endsWith(POSFIX);
			}

			@Override
			public String getDescription() {
				return DESCRIPTION;
			}
		});

		setResizable(false);
		setMaximumSize(new Dimension(960, 540));
		setMinimumSize(new Dimension(960, 540));
		setTitle("MiniCAD");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 405);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		splitPane = new JSplitPane();
		splitPane.setEnabled(false);
		splitPane.setResizeWeight(0.15);
		contentPane.add(splitPane, BorderLayout.CENTER);

		shapePanel = new MyPanel();
		splitPane.setRightComponent(shapePanel);

		JPanel toolPanel = new JPanel();
		splitPane.setLeftComponent(toolPanel);
		toolPanel.setLayout(null);

		JButton btnCircle = new JButton("Circle");
		btnCircle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnCircle_onClick();
			}
		});
		btnCircle.setFont(new Font("Arial", Font.PLAIN, 20));
		btnCircle.setBounds(10, 10, 121, 48);
		toolPanel.add(btnCircle);

		JButton btnRectangle = new JButton("Rectangle");
		btnRectangle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnRectangle_onClick();
			}
		});
		btnRectangle.setFont(new Font("Arial", Font.PLAIN, 18));
		btnRectangle.setBounds(10, 68, 121, 48);
		toolPanel.add(btnRectangle);

		JButton btnLine = new JButton("Line");
		btnLine.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnLine_onClick();
			}
		});
		btnLine.setFont(new Font("Arial", Font.PLAIN, 20));
		btnLine.setBounds(10, 126, 121, 48);
		toolPanel.add(btnLine);

		JButton btnText = new JButton("Text");
		btnText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnText_onClick();
			}
		});
		btnText.setFont(new Font("Arial", Font.PLAIN, 20));
		btnText.setBounds(10, 184, 121, 48);
		toolPanel.add(btnText);

		JButton btnColorRed = new JButton("");
		btnColorRed.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnColor_onClick(Color.RED);
			}
		});
		btnColorRed.setForeground(Color.WHITE);
		btnColorRed.setBackground(Color.RED);
		btnColorRed.setBounds(11, 458, 30, 30);
		toolPanel.add(btnColorRed);

		JButton btnColorOrange = new JButton("");
		btnColorOrange.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnColor_onClick(Color.ORANGE);
			}
		});
		btnColorOrange.setForeground(Color.WHITE);
		btnColorOrange.setBackground(Color.ORANGE);
		btnColorOrange.setBounds(41, 458, 30, 30);
		toolPanel.add(btnColorOrange);

		JButton btnColorGreen = new JButton("");
		btnColorGreen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnColor_onClick(Color.GREEN);
			}
		});
		btnColorGreen.setForeground(Color.WHITE);
		btnColorGreen.setBackground(Color.GREEN);
		btnColorGreen.setBounds(71, 458, 30, 30);
		toolPanel.add(btnColorGreen);

		JButton btnColorCyan = new JButton("");
		btnColorCyan.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnColor_onClick(Color.CYAN);
			}
		});
		btnColorCyan.setForeground(Color.WHITE);
		btnColorCyan.setBackground(Color.CYAN);
		btnColorCyan.setBounds(101, 458, 30, 30);
		toolPanel.add(btnColorCyan);

		JButton btnColorPink = new JButton("");
		btnColorPink.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnColor_onClick(Color.PINK);
			}
		});
		btnColorPink.setForeground(Color.WHITE);
		btnColorPink.setBackground(Color.PINK);
		btnColorPink.setBounds(11, 428, 30, 30);
		toolPanel.add(btnColorPink);

		JButton btnColorYellow = new JButton("");
		btnColorYellow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnColor_onClick(Color.YELLOW);
			}
		});
		btnColorYellow.setForeground(Color.WHITE);
		btnColorYellow.setBackground(Color.YELLOW);
		btnColorYellow.setBounds(41, 428, 30, 30);
		toolPanel.add(btnColorYellow);

		JButton btnColorMagenta = new JButton("");
		btnColorMagenta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnColor_onClick(Color.MAGENTA);
			}
		});
		btnColorMagenta.setForeground(Color.WHITE);
		btnColorMagenta.setBackground(Color.MAGENTA);
		btnColorMagenta.setBounds(71, 428, 30, 30);
		toolPanel.add(btnColorMagenta);

		JButton btnColorBlue = new JButton("");
		btnColorBlue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnColor_onClick(Color.BLUE);
			}
		});
		btnColorBlue.setForeground(Color.WHITE);
		btnColorBlue.setBackground(Color.BLUE);
		btnColorBlue.setBounds(101, 428, 30, 30);
		toolPanel.add(btnColorBlue);

		JButton btnColorLightGray = new JButton("");
		btnColorLightGray.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnColor_onClick(Color.LIGHT_GRAY);
			}
		});
		btnColorLightGray.setForeground(Color.WHITE);
		btnColorLightGray.setBackground(Color.LIGHT_GRAY);
		btnColorLightGray.setBounds(11, 398, 30, 30);
		toolPanel.add(btnColorLightGray);

		JButton btnColorGray = new JButton("");
		btnColorGray.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnColor_onClick(Color.GRAY);
			}
		});
		btnColorGray.setForeground(Color.WHITE);
		btnColorGray.setBackground(Color.GRAY);
		btnColorGray.setBounds(41, 398, 30, 30);
		toolPanel.add(btnColorGray);

		JButton btnColorDarkGray = new JButton("");
		btnColorDarkGray.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnColor_onClick(Color.DARK_GRAY);
			}
		});
		btnColorDarkGray.setForeground(Color.WHITE);
		btnColorDarkGray.setBackground(Color.DARK_GRAY);
		btnColorDarkGray.setBounds(71, 398, 30, 30);
		toolPanel.add(btnColorDarkGray);

		JButton btnColorBlack = new JButton("");
		btnColorBlack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnColor_onClick(Color.BLACK);
			}
		});
		btnColorBlack.setForeground(Color.WHITE);
		btnColorBlack.setBackground(Color.BLACK);
		btnColorBlack.setBounds(101, 398, 30, 30);
		toolPanel.add(btnColorBlack);

		pnlCurrentColor = new Panel();
		pnlCurrentColor.setBackground(Color.BLACK);
		pnlCurrentColor.setBounds(101, 362, 30, 30);
		toolPanel.add(pnlCurrentColor);

		JLabel lblColor = new JLabel("Current Color :");
		lblColor.setFont(new Font("Arial", Font.PLAIN, 12));
		lblColor.setBounds(10, 364, 91, 24);
		toolPanel.add(lblColor);

		JButton btnOpen = new JButton("Open");
		btnOpen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnOpen_onClick();
			}
		});
		btnOpen.setBackground(Color.CYAN);
		btnOpen.setFont(new Font("Arial", Font.PLAIN, 9));
		btnOpen.setBounds(8, 257, 59, 24);
		toolPanel.add(btnOpen);

		JButton btnSave = new JButton("Save");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnSave_onClick();
			}
		});
		btnSave.setBackground(Color.GREEN);
		btnSave.setFont(new Font("Arial", Font.PLAIN, 10));
		btnSave.setBounds(72, 257, 59, 24);
		toolPanel.add(btnSave);

		JButton btnDelete = new JButton("Delete Selected");
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnDelete_onClick();
			}
		});
		btnDelete.setBackground(Color.RED);
		btnDelete.setFont(new Font("Arial", Font.PLAIN, 10));
		btnDelete.setBounds(10, 291, 119, 23);
		toolPanel.add(btnDelete);

		var btnClear = new JButton("Clear All");
		btnClear.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				btnClear_onClick();
			}
		});
		btnClear.setBackground(Color.YELLOW);
		btnClear.setFont(new Font("Arial", Font.PLAIN, 10));
		btnClear.setBounds(10, 325, 119, 23);
		toolPanel.add(btnClear);
	}

	public Color getCurrentColor() {
		return this.pnlCurrentColor.getBackground();
	}

	private void btnCircle_onClick() {
		this.shapePanel.setCreateMode(MyShape.ShapeType.Circle);
	}

	private void btnRectangle_onClick() {
		this.shapePanel.setCreateMode(MyShape.ShapeType.Rectangle);
	}

	private void btnLine_onClick() {
		this.shapePanel.setCreateMode(MyShape.ShapeType.Line);
	}

	private void btnText_onClick() {
		this.shapePanel.setCreateMode(MyShape.ShapeType.Text);
	}

	private void btnColor_onClick(Color color) {
		this.pnlCurrentColor.setBackground(color);
		this.shapePanel.changeSelectedColor(color);
	}

	private void btnOpen_onClick() {
		var res = this.fileChooser.showOpenDialog(this);
		if (res == JFileChooser.APPROVE_OPTION) {
			this.openFromFile(this.fileChooser.getSelectedFile());
		}
	}

	private void btnSave_onClick() {
		var res = this.fileChooser.showSaveDialog(this);
		if (res == JFileChooser.APPROVE_OPTION) {
			this.saveToFile(this.fileChooser.getSelectedFile());
		}
	}

	private void btnDelete_onClick() {
		this.shapePanel.deleteSelected();
	}

	private void btnClear_onClick(){
		this.shapePanel.clearAll();
	}

	private void saveToFile(File file) {
		try {
			if(!file.getName().endsWith(POSFIX)){
				file = new File(file.getAbsolutePath() + POSFIX);
			}
			var oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(this.shapePanel);
			oos.flush();
			oos.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void openFromFile(File file) {
		try {
			var ois = new ObjectInputStream(new FileInputStream(file));
			this.shapePanel = (MyPanel) ois.readObject();
			this.splitPane.remove(splitPane.getRightComponent());
			this.splitPane.setRightComponent(this.shapePanel);
			ois.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
