package progressBar;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

public class ProgressDemo1 extends JPanel implements ActionListener,PropertyChangeListener{
	private JProgressBar bar;
	private JButton startbtn;
	private JTextArea taskoutput;
	private Task task;
	
	class Task extends SwingWorker<Void, Void>{
		/*
		 * main task , execute in background thread
		 */
		@Override
		protected Void doInBackground() throws Exception {
			Random random = new Random();
			int progress=0;
			setProgress(0);
			while(progress<100){
				//sleep for up to one second
				try{
					Thread.sleep(random.nextInt(1000));
				}catch (InterruptedException e){
					
				}
				progress+=random.nextInt(10);
				setProgress(Math.min(progress, 100));
			}
			return null;
		}
		@Override
		protected void done() {
			Toolkit.getDefaultToolkit().beep();
			startbtn.setEnabled(true);
			setCursor(null); //turn off the wait cursor
			taskoutput.append("Done£¡\n");
		}
	}
	
	public ProgressDemo1() {
		super(new BorderLayout());
		//create the demo's UI
		startbtn = new JButton("Start");
		startbtn.setActionCommand("start");
		startbtn.addActionListener(this);
		
		bar = new JProgressBar(0, 100);
		bar.setValue(0);
		bar.setStringPainted(true);
		
		taskoutput = new JTextArea(5, 20);
		taskoutput.setMargin(new Insets(5, 5, 5, 5));
		taskoutput.setEditable(false);
		
		JPanel panel = new JPanel();
		panel.add(startbtn);
		panel.add(bar);
		
		add(panel,BorderLayout.PAGE_START);
		add(new JScrollPane(taskoutput),BorderLayout.CENTER);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	}
	
	/**
	 * invoke when task's progress property changes
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		
	}

	/*
	 * invoke when the user presses the start btn
	 */
	public void actionPerformed(ActionEvent e) {
		startbtn.setEnabled(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		/*
		 * instance of javax.swing.swingwoker are not reusuable,so
		 * we create new instance as needed
		 */
		task = new Task();
		task.addPropertyChangeListener(this);
		task.execute();
	}
	
	

}
