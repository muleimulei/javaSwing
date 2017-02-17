package layeredPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import eventListener.Beeper;

public class LayeredPanelDemo1 extends JPanel implements ActionListener,MouseMotionListener{
	private String[] layerStrings={"Yellow(0)","Magenta(1)","Cyan(2)","Red(3)","Green(4)"};
	private Color[] layerColors = {Color.YELLOW,Color.MAGENTA,Color.CYAN,Color.RED,Color.GREEN};
	private JLayeredPane layeredPane;//分层窗格
	private JLabel dukeLabel;
	private JCheckBox onTop;
	private JComboBox layerList;
	
	//action command
	private static  String ON_TOP_COMMAND="ontop";
	private static  String LAYER_COMMAND="layer";
	
	//adjustment to put duke's toe at the cursor's tip
	private static final int XFUDGE=40;
	private static final int YFUDGE=57;
	
	public LayeredPanelDemo1(){
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		//create and load icon
		final ImageIcon icon = createImageIcon("dukeWaveRed.gif");
		//create and set up the layered pane
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(300, 310));
		layeredPane.setBorder(BorderFactory.createTitledBorder("Move the Mouse to Move Duke"));
		layeredPane.addMouseMotionListener(this);
		
		//this is the origin of the first label added
		Point origin = new Point(10, 20);
		//this is the offset for computing the origin for the next label
		int offset = 35;
		/*
		 * add several overlapping ,colored labels to the layered pane
		 * use absolute position/sizing
		 */
		for(int i=0;i<layerStrings.length;i++){
			JLabel label = createColoredLabel(layerStrings[i],layerColors[i],origin);
			label.setOpaque(true);
			layeredPane.add(label,new Integer(i));
			origin.x+=offset;
			origin.y+=offset;
		}
		//create and add the duke label to the layered pane
		dukeLabel = new JLabel(icon);
		
		if(icon!=null){
			dukeLabel.setBounds(100, 45, icon.getIconWidth(), icon.getIconHeight());
		}else{
			System.err.println("Duke icon not found;using black square instead");
			dukeLabel.setBounds(15, 225, 30, 30);
			dukeLabel.setOpaque(true);
			dukeLabel.setBackground(Color.BLACK);
		}
		layeredPane.add(dukeLabel,new Integer(1),0);
		//add control pane and layered pane to this jpanel
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(createControlPanel());
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(layeredPane);
	}

	private Component createControlPanel() {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
		layerList = new JComboBox(layerStrings);
		p.add(layerList);
		layerList.addActionListener(this);
		layerList.setActionCommand(LAYER_COMMAND);
		onTop = new JCheckBox("Top position in layer");
		onTop.addActionListener(this);
		onTop.setActionCommand(ON_TOP_COMMAND);
		p.add(onTop);
		p.setBorder(BorderFactory.createTitledBorder("Choose Duke's Layer and position"));
		return p;
	}

	private JLabel createColoredLabel(String text, Color color, Point p) {
		JLabel label = new JLabel(text);
		label.setVerticalAlignment(JLabel.TOP);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setLocation(p);
		label.setBackground(color);
		label.setBounds(p.x, p.y, 150, 150);
		return label;
	}

	//return an imageicon ,or null if the path was invalid
	private ImageIcon createImageIcon(String path) {
		URL imgurl = this.getClass().getResource(path);
		if(imgurl!=null){
			return new ImageIcon(imgurl);
		}else{
			System.err.println("Could't find file:"+path);
			return null;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		/*if(e.getX()>dukeLabel.getX()&&(e.getX()<dukeLabel.getWidth()+dukeLabel.getX())
				&&e.getY()>dukeLabel.getY()&&(e.getY()<dukeLabel.getY()+dukeLabel.getHeight())){
			dukeLabel.setLocation(e.getX(), e.getY());
		}*/
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		dukeLabel.setLocation(e.getX()-dukeLabel.getWidth()/2, e.getY()-dukeLabel.getHeight()/2);
	}

	@Override
	//handle user interaction with the check box and combo box
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(ON_TOP_COMMAND.equals(cmd)){
			if(onTop.isSelected()){
				layeredPane.moveToFront(dukeLabel);
			}else{
				layeredPane.moveToBack(dukeLabel);
			}
		}else if(LAYER_COMMAND.equals(cmd)){
			int position = onTop.isSelected()?0:1;
			//要设置深度的组件，新的深度值，指定深度的位置
			layeredPane.setLayer(dukeLabel, layerList.getSelectedIndex(), position);
		}
	}
	
	private static void createAndShowGUI() {
		//create and set up window
		JFrame frame = new JFrame("LayeredPanelDemo1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;
		
		//create and set up the content pane
		JComponent content = new LayeredPanelDemo1();
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
