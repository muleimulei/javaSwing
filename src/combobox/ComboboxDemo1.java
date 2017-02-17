package combobox;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ComboboxDemo1 extends JPanel implements ActionListener{
	JLabel picture;
	public ComboboxDemo1() {
		super(new BorderLayout());
		String [] petStrings = {"Bird","Cat","Dog","Rabbit","Pig"};
		//create the combo box,select the item at index 4
		JComboBox petlist = new JComboBox(petStrings);
		petlist.setSelectedIndex(4);
		petlist.addActionListener(this);
		
		//set up the picture
		picture = new JLabel();
		picture.setFont(picture.getFont().deriveFont(Font.ITALIC));
		picture.setHorizontalAlignment(JLabel.CENTER);
		updateLabel(petStrings[petlist.getSelectedIndex()]);
		picture.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		
		//The prefered size is hard-coded to be the width of the
		// widest image and the height of the tallest image+the border
		//A real program would compute this
		picture.setPreferredSize(new Dimension(177, 122+10));
		//lay out the demo
		add(petlist,BorderLayout.PAGE_START);
		add(picture,BorderLayout.PAGE_END);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	}
	
	private void updateLabel(String string) {
		ImageIcon icon = createImageIcon("images/"+string+".gif");
		picture.setIcon(icon);
		picture.setToolTipText("A drawing of a"+string.toLowerCase());
		if(icon!=null){
			picture.setText(null);
		}else{
			picture.setText("Image not found");
		}
	}

	//return an imageicon,or null if the path was invalid
	private ImageIcon createImageIcon(String string) {
		URL imgurl = this.getClass().getResource(string);
		if(imgurl!=null){
			return new ImageIcon(imgurl);
		}else{
			System.err.println("could't find file"+string);
			return null;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox)e.getSource();
		String petname = (String)cb.getSelectedItem();
		updateLabel(petname);
	}
	
	private static void createAndShowGUI() {
		//create and set up window
		JFrame frame = new JFrame("ComboBox1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;
		
		//create and set up the content pane
		JComponent content = new ComboboxDemo1();
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
