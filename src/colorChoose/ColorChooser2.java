package colorChoose;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorChooser2 extends JPanel implements ActionListener,ChangeListener {
	public JLabel banner;
	public JColorChooser tcc;
	
	public ColorChooser2(){
		super(new BorderLayout());
		//set up banner to use as custom preview pannel
		banner = new JLabel("Welcome to the Tutorial Zone",JLabel.CENTER);
		banner.setForeground(Color.YELLOW);
		banner.setBackground(Color.BLUE);
		banner.setOpaque(true);
		banner.setFont(new Font("SansSerif",Font.BOLD,20));
		banner.setPreferredSize(new Dimension(100, 65));
		
		JPanel bannerPanel = new JPanel(new BorderLayout());
		bannerPanel.add(banner,BorderLayout.CENTER);
		bannerPanel.setBorder(BorderFactory.createTitledBorder("Banner"));
		
		//set up color chooser for setting background color
		JPanel panel = new JPanel();
		JButton bcc = new JButton("Choose background Color");//use flowlayout
		bcc.addActionListener(this);
		panel.add(bcc);
		panel.setBorder(BorderFactory.createTitledBorder("Choose Background Color"));
		
		//set up color chooser for setting text color
		tcc = new JColorChooser();
		tcc.getSelectionModel().addChangeListener(this);
		tcc.setBorder(BorderFactory.createTitledBorder("Choose Text Color"));
		
		//remove the preview panel
		tcc.setPreviewPanel(new JPanel());//删除预览面板
		//override the chooser panel with our own
		AbstractColorChooserPanel panels[]={new Crayonpanel()};
		tcc.setChooserPanels(panels);//设置自定义选择器
		tcc.setColor(bannerPanel.getForeground());
		
		add(bannerPanel,BorderLayout.PAGE_START);
		add(panel,BorderLayout.CENTER);
		add(tcc,BorderLayout.PAGE_END);
		
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		Color c = tcc.getColor();
		banner.setForeground(c);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Color c = JColorChooser.showDialog(this, "Choose BackGround Color", Color.BLUE);
		if(c!=null){
			banner.setBackground(c);
		}
	}
	public static void createAndShowGUI(){
		//create and set up window
		JFrame frame = new JFrame("ColorChooseDemo2");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//create and set up the content panel
		JComponent content =  new ColorChooser2();
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
