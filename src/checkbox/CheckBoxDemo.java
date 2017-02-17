package checkbox;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class CheckBoxDemo extends JPanel implements ItemListener{
	StringBuffer choices;
	JLabel pictureLable;
	JCheckBox chinbutton;
	JCheckBox glassesButton;
	JCheckBox hairButton;
	JCheckBox teethButton;
	
	public CheckBoxDemo(){
		super(new BorderLayout());
		//Create the check boxes
		chinbutton = new JCheckBox("Chin");
		chinbutton.setMnemonic(KeyEvent.VK_C);
		chinbutton.setSelected(true);
		
		glassesButton = new JCheckBox("Glasses");
		glassesButton.setMnemonic(KeyEvent.VK_G);
		glassesButton.setSelected(true);
		
		hairButton = new JCheckBox("Hair");
		hairButton.setMnemonic(KeyEvent.VK_H);
		hairButton.setSelected(true);
		
		teethButton = new JCheckBox("Teeth");
		teethButton.setMnemonic(KeyEvent.VK_T);
		teethButton.setSelected(true);
		
		//Register a listener for the check boxes
		chinbutton.addItemListener(this);
		glassesButton.addItemListener(this);
		hairButton.addItemListener(this);
		teethButton.addItemListener(this);
		
		//indicate what's on the geek
		choices = new StringBuffer("cght");
		//set up the picture label
		pictureLable = new JLabel();
		pictureLable.setFont(pictureLable.getFont().deriveFont(Font.ITALIC));
		updatePicture();
		//put the checkb boxes in a column in a panel
		JPanel checkPanel = new JPanel(new GridLayout(0, 1));
		checkPanel.add(chinbutton);
		checkPanel.add(glassesButton);
		checkPanel.add(hairButton);
		checkPanel.add(teethButton);
		
		add(checkPanel,BorderLayout.LINE_END);
		add(pictureLable,BorderLayout.CENTER);
		
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
	}
	private void updatePicture() {
		//get the icon corresponding to the image
		ImageIcon icon = createImage("./geek/geek-"
										+choices.toString()+".gif");
		pictureLable.setIcon(icon);
		pictureLable.setToolTipText(choices.toString());
		if(icon==null){
			pictureLable.setText("Missing Image");
		}else{
			pictureLable.setText(null);
		}
	}
	private ImageIcon createImage(String string) {
		URL imgurl = this.getClass().getResource(string);
		if(imgurl!=null){
			return new ImageIcon(imgurl);
		}else{
			System.err.println("Could't find file"+string);
			return null;
		}
	}
	@Override
	//listen to the check boxes
	public void itemStateChanged(ItemEvent e) {
		int index = 0;
		char c='-';
		Object source = e.getItemSelectable();//获的程序的产生程序
		if(source==chinbutton){
			index = 0;
			c='c';
		}else if(source==glassesButton){
			index=1;
			c='g';
		}else if(source==hairButton){
			index=2;
			c='h';
		}else if(source==teethButton){
			index=3;
			c='t';
		}
		/*
		 * now that we know which button was pushed,find out
		 * whether it was selected or deselected
		 */
		if(e.getStateChange()==ItemEvent.DESELECTED){
			c='-';
		}
		//apply the change to the string
		choices.setCharAt(index, c);
		updatePicture();
	}
	private static void createAndShowGUI(){
		JFrame frame = new JFrame("CheckBoxDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//create and set up the content pane
		JComponent content = new CheckBoxDemo();
		content.setOpaque(true);
		frame.setContentPane(content);
		
		//display the window
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				
				@Override
				public void run() {
					createAndShowGUI();
					
				}
			});
		} catch (InvocationTargetException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
