package colorChoose;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorChooser1 extends JPanel implements ChangeListener{
	protected JColorChooser tcc;
	protected JLabel banner;
	
	public ColorChooser1() {
		super(new BorderLayout());
		//set up the banner at the top of the window
		banner = new JLabel("Welcome to the Tutorial Zone",JLabel.CENTER);
		banner.setForeground(Color.YELLOW);
		banner.setBackground(Color.BLUE);
		banner.setOpaque(true);
		banner.setFont(new Font("SansSerif", Font.BOLD, 24));
		banner.setPreferredSize(new Dimension(100, 65));
		
		JPanel bannerPanel = new JPanel(new BorderLayout());
		bannerPanel.add(banner,BorderLayout.CENTER);
		bannerPanel.setBorder(BorderFactory.createTitledBorder("Banner"));
		
		//set up color chooser for setting text color
		tcc = new JColorChooser(bannerPanel.getForeground());
		tcc.getSelectionModel().addChangeListener(this);//颜色选择器使用ColorSelectionmodel实例来包含并管理当前的选择
		tcc.setBorder(BorderFactory.createTitledBorder("Choose Text Color"));
		
		add(bannerPanel,BorderLayout
				.CENTER);
		add(tcc,BorderLayout.PAGE_END);
		
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		Color c = tcc.getColor();
		banner.setForeground(c);
	}
	private static void createAndShowGUI() {
		//create and set up the window
		JFrame frame = new JFrame("ColorChooserDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;
		
		//create and set up the content pane
		JComponent content = new ColorChooser1();
		content.setOpaque(true);
		frame.setContentPane(content);
		
		//display the window
		frame.pack();
		frame.setVisible(true);
		
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				createAndShowGUI();
			}

			
		});
	}

}
