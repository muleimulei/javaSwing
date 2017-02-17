package combobox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ComboboxDemo2 extends JPanel implements ActionListener{
	static JFrame frame;
	JLabel result;
	String currentPattern;
	public ComboboxDemo2() {
		setLayout(new GridLayout(0,1));
		
		 String[] patternExamples = {
                 "dd MMMMM yyyy",
                 "dd.MM.yy",
                 "MM/dd/yy",
                 "yyyy.MM.dd G 'at' hh:mm:ss z",
                 "EEE, MMM d, ''yy",
                 "h:mm a",
                 "H:mm:ss:SSS",
                 "K:mm a,z",
                 "yyyy.MMMMM.dd GGG hh:mm aaa"
                 };
		 currentPattern = patternExamples[0];
		 //set up the ui for selecting a pattern
		 JLabel l1 = new JLabel("Enter the pattern string or");
		 JLabel l2 = new JLabel("select one from the list: ");
		 
		 JComboBox patternlist = new JComboBox(patternExamples);
		 patternlist.setEditable(true);
		 patternlist.addActionListener(this);
		 
		 //create the UI for desplaying result
		 JLabel resultlabel = new JLabel("Current Date/Time",JLabel.LEFT);
		 result = new JLabel("");
		 result.setForeground(Color.BLACK);
		 result.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		 
		 //lay out everything
		 JPanel patternPanel = new JPanel();
		 patternPanel.setLayout(new BoxLayout(patternPanel, BoxLayout.PAGE_AXIS));
		 patternPanel.add(l1);
		 patternPanel.add(l2);
		 patternlist.setAlignmentX(Component.LEFT_ALIGNMENT);
		 patternPanel.add(patternlist);
		 
		 JPanel reultPanel = new JPanel(new GridLayout(0, 1));
		 reultPanel.add(resultlabel);
		 reultPanel.add(result);
		 
		// patternPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		 reultPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		 
		 add(patternPanel);
		 add(Box.createRigidArea(new Dimension(0, 10)));
		 add(reultPanel);
		 
		 setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		 reformat();
		 
	
	}
	
	private void reformat() {
		Date today = new Date();
		SimpleDateFormat format = new SimpleDateFormat(currentPattern);
		String datestr = format.format(today);
		result.setForeground(Color.BLACK);
		result.setText(datestr);
		
	}


	
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox)e.getSource();
		String newselect = (String)cb.getSelectedItem();
		currentPattern = newselect;
		reformat();
		
	}
	
	private static void createAndShowGUI() {
		//create and set up window
		JFrame frame = new JFrame("ComboBox2");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;
		
		//create and set up the content pane
		JComponent content = new ComboboxDemo2();
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
