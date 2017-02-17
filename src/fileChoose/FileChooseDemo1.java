package fileChoose;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class FileChooseDemo1 extends JPanel implements ActionListener {
	private static final String newline="\n";
	JButton openbtn,savebtn;
	JTextArea log;
	JFileChooser fc;
	
	public FileChooseDemo1(){
		setLayout(new BorderLayout());
		
		//create the log first,because the action listener need to refer to it
		log = new JTextArea(5, 20);
		log.setMargin(new Insets(5, 5, 5, 5));
		log.setEditable(true);
		JScrollPane logscrollPane = new JScrollPane(log);
		
		//create a file choose
		fc = new JFileChooser();
		
		openbtn = new JButton("Open a file ***", createImageIcon("Open.gif"));
		openbtn.addActionListener(this);
		savebtn = new JButton("Save a file ***", createImageIcon("Save.gif"));
		savebtn.addActionListener(this);
		
		//for layout purpose,put the btn in a separate panel
		JPanel btnPanel = new JPanel(); //use flowlayout
		btnPanel.add(openbtn);
		btnPanel.add(savebtn);
		
		//add the btnPanel and log to this panel
		add(btnPanel,BorderLayout.PAGE_START);
		add(log,BorderLayout.CENTER);
	}
	
	private Icon createImageIcon(String path) {
		URL imgurl = this.getClass().getResource(path);
		if(imgurl!=null){
			return new ImageIcon(path);
		}else{
			System.err.println("could't find file"+path);
			return null;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//handle open btn action
		if(e.getSource()==openbtn){
			int retval = fc.showOpenDialog(this);
			if(retval==JFileChooser.APPROVE_OPTION){
				File file = fc.getSelectedFile();
				log.append("Opening: "+file.getName()+"."+newline);
			}else{
				log.append("Open command cancelled by use"+newline);
			}
		}else if(e.getSource()==savebtn){
			int retval = fc.showSaveDialog(this);
			if(retval==JFileChooser.APPROVE_OPTION){
				File file = fc.getSelectedFile();
				log.append("save :"+file.getName()+"."+newline);
			}
			log.setCaretPosition(log.getDocument().getLength());
		}
	}

	public static void createAndShowGUI()
	{
		//create and set up the window
		JFrame frame = new JFrame("FileChooseDemo1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(new FileChooseDemo1());
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
