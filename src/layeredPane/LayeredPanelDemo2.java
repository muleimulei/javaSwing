package layeredPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
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

public class LayeredPanelDemo2 extends JPanel implements MouseMotionListener,ActionListener{
	private String[] layerStrings={"Yellow(0)","Magenta(1)","Cyan(2)","Red(3)","Green(4)","Blue(5)"};
	private Color[] layerColors = {Color.YELLOW,Color.MAGENTA,Color.CYAN,Color.RED,Color.GREEN,Color.BLUE};
	private JLayeredPane layeredPane;//分层窗格
	private JLabel dukeLabel;
	private JCheckBox onTop;
	private JComboBox layerList;
	
	//action command
	private static  String ON_TOP_COMMAND="ontop";
	private static  String LAYER_COMMAND="layer";
	
	public LayeredPanelDemo2(){
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));;
		
		//create and load icon
		final ImageIcon icon = createImageIcon("dukeWaveRed.gif");
		//create and set up the layered pane
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(300	,310));
		layeredPane.setBorder(BorderFactory.createTitledBorder("Move the mouse to Move Duke"));
		layeredPane.addMouseMotionListener(this);
		
		//add several labels to the layered pane
		layeredPane.setLayout(new GridLayout(2, 3));
		for(int i =0;i<layerStrings.length;i++){
			JLabel label = createColoredLabel(layerStrings[i],layerColors[i]);
			layeredPane.add(label, new Integer(i));
		}
		//create and add the duke to the layered pane
		dukeLabel = new JLabel(icon);
		if(icon==null){
			System.err.println("Duke icon not found; using black rectangle instead");
			dukeLabel.setOpaque(true);
			dukeLabel.setBackground(Color.BLACK);
		}else{
			dukeLabel.setBounds(100, 45, icon.getIconWidth(), icon.getIconHeight());
		}
		
		layeredPane.add(dukeLabel,new Integer(2),0);
		//add control pane and layered pane to this jpanel
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(createControlPanel());
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(layeredPane);
		
	}
	private Component createControlPanel() {
		onTop = new JCheckBox("Top Position in Layer");
        onTop.setSelected(true);
        onTop.setActionCommand(ON_TOP_COMMAND);
        onTop.addActionListener(this);
 
        layerList = new JComboBox(layerStrings);
        layerList.setSelectedIndex(2);    //cyan layer
        layerList.setActionCommand(LAYER_COMMAND);
        layerList.addActionListener(this);
 
        JPanel controls = new JPanel();
        controls.add(layerList);
        controls.add(onTop);
        controls.setBorder(BorderFactory.createTitledBorder(
                                 "Choose Duke's Layer and Position"));
        return controls;
	}
	private JLabel createColoredLabel(String string, Color color) {
		JLabel label = new JLabel(string);
		label.setVerticalAlignment(JLabel.TOP);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setOpaque(true);
		label.setBackground(color);
		label.setForeground(Color.lightGray);
		label.setBorder(BorderFactory.createLineBorder(color, 1, true));
		label.setPreferredSize(new Dimension(140, 140));
		return label;
	}
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
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		dukeLabel.setLocation(e.getX()-dukeLabel.getWidth()/2,
                e.getY()-dukeLabel.getHeight()/2);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		 String cmd = e.getActionCommand();
	        if (ON_TOP_COMMAND.equals(cmd)) {
	            if (onTop.isSelected())
	                layeredPane.moveToFront(dukeLabel);
	            else
	                layeredPane.moveToBack(dukeLabel);
	 
	        } else if (LAYER_COMMAND.equals(cmd)) {
	            int position = onTop.isSelected() ? 0 : 1;
	            layeredPane.setLayer(dukeLabel,
	                                 layerList.getSelectedIndex(),
	                                 position);
	        }
	}

	private static void createAndShowGUI() {
		//create and set up window
		JFrame frame = new JFrame("LayeredPanelDemo2");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;
		
		//create and set up the content pane
		JComponent content = new LayeredPanelDemo2();
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
