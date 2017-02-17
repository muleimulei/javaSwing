package fileChoose;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
/*
 * FileChooserDemo2.java requires these files:
 *   ImageFileView.java
 *   ImageFilter.java
 *   ImagePreview.java
 *   Utils.java
 *   images/jpgIcon.gif (required by ImageFileView.java)
 *   images/gifIcon.gif (required by ImageFileView.java)
 *   images/tiffIcon.gif (required by ImageFileView.java)
 *   images/pngIcon.png (required by ImageFileView.java)
 */
public class FileChooseDemo2 extends JPanel implements ActionListener {
	private static final String newline="\n";
	private JTextArea log;
	private JFileChooser fc;
	
	public FileChooseDemo2(){
		super(new BorderLayout());
		//create the log first ,because the action listener
		//needs to refer to it
		log = new JTextArea(5, 20);
		log.setMargin(new Insets(5, 5, 5, 5));//设置字体与边框的距离
		log.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(log);
		
		JButton sendbtn = new JButton("Attach ***");
		sendbtn.addActionListener(this);
		
		add(sendbtn,BorderLayout.PAGE_START);
		add(logScrollPane,BorderLayout.CENTER);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//set up the file choose
		if(fc==null){
			fc = new JFileChooser();
			//add a custom file filter and disable the default
			//file filter
			fc.addChoosableFileFilter(new ImageFilter());//设置过滤器
			fc.setAcceptAllFileFilterUsed(false);//去掉显示所有文件
			
			//add custom icons for file types
			fc.setFileView(new ImageFileView());//自定义文件视图
			fc.setAccessory(new ImagePreview(fc));//设置附属组件

		}
		//show it
		int retval = fc.showDialog(this, "Attach");
		//process the result
		if(retval==JFileChooser.APPROVE_OPTION){
			File file = fc.getSelectedFile();
			log.append("Attaching file : "+file.getName()+"."+newline);
		}else{
			log.append("Attachment canneled by user ."+newline);
		}
		log.setCaretPosition(log.getDocument().getLength());
		//reset the filechooser for the next time it's shown
		fc.setSelectedFile(null);
	}
	
	public static void createAndShowGUI(){
		//create and set up the window
		JFrame frame = new JFrame("FileChooseDemo2");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(new FileChooseDemo2());
		
		//display the win
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
