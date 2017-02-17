package eventListener;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import combobox.ComboboxDemo1;

public class Beeper extends JPanel implements ActionListener{
	JButton btn;
	public Beeper(){
		super(new BorderLayout());
        btn = new JButton("Click Me");
        btn.setPreferredSize(new Dimension(200, 80));
        add(btn, BorderLayout.CENTER);
        btn.addActionListener(this);
	}
	
	
	
	
	private static void createAndShowGUI() {
		//create and set up window
		JFrame frame = new JFrame("Beeper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;
		
		//create and set up the content pane
		JComponent content = new Beeper();
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




	@Override
	public void actionPerformed(ActionEvent e) {
		Toolkit.getDefaultToolkit().beep();
	}


}
