package jlist;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ListDemo extends JPanel implements FocusListener,ListSelectionListener{
	private JList list;
	private DefaultListModel listModel;
	
	private static final String hireString="Hire";
	private static final String fireString="Fire";
	private JButton fireButton;
	private JTextField employeeName;
	
	public ListDemo() {
		super(new BorderLayout());
		listModel = new DefaultListModel();
		listModel.addElement("Jane Doe");
		listModel.addElement("John smith");
		listModel.addElement("Kathy Green");
		
		//create the list and put it in a scroll pane
		list = new JList(listModel);
		//选择列表项
		//SINALE_SELECTION SINGLE_INTERVAL_SELECTION MULTIPLE_INTERVAL_SELECTION
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		list.setSelectedIndex(0);
		list.addListSelectionListener(this);
		list.setVisibleRowCount(5);
		JScrollPane pane = new JScrollPane(list);
		
		JButton hireBtn  = new JButton(hireString);
		HireListener hireListener = new HireListener(hireBtn);
		hireBtn.setActionCommand(hireString);
		hireBtn.addActionListener(hireListener);
		hireBtn.setEnabled(false);
		
		fireButton = new JButton(fireString);
		fireButton.setActionCommand(fireString);
		fireButton.addActionListener(new FireListener());
		
		
		employeeName = new JTextField(10);
		employeeName.addFocusListener(this);
		employeeName.addActionListener(hireListener);
		employeeName.getDocument().addDocumentListener(hireListener);
		
		
		//create a panel that uses boxlayout
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.add(fireButton);
		buttonPane.add(Box.createHorizontalStrut(5));
		buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
		buttonPane.add(Box.createHorizontalStrut(5));
		buttonPane.add(employeeName);
		buttonPane.add(hireBtn);
		buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		add(pane,BorderLayout.CENTER);
		add(buttonPane,BorderLayout.PAGE_END);
		
		
		
	}
	
	class FireListener implements ActionListener{
		@Override
		//this method can be called only if
		//there's a valid selection
		//so go ahead and remove whatever's selected
		public void actionPerformed(ActionEvent e) {
			int index = list.getSelectedIndex();
			
			System.out.println(listModel.getSize());
			System.out.println(index);
			listModel.remove(index);
			System.out.println(listModel.getSize());
			int size = listModel.getSize();
			if(size==0){//nobody's left ,disable firing
				fireButton.setEnabled(false);
			}else {//select an index
				if(index==listModel.getSize()){
					//remove item in last position
					index--;
				}
				list.setSelectedIndex(index);
				list.ensureIndexIsVisible(index);
			}
		}
	}
	//this listener is shared by the text field and hire btn
	class HireListener implements ActionListener,DocumentListener{
		private boolean alreadyEnable = false;
		private JButton btn;
		public HireListener(JButton btn) {
			this.btn = btn;
		}
		
		@Override
		public void insertUpdate(DocumentEvent e) {
			enableBtn();
		}

		private void enableBtn() {
			if(!alreadyEnable){
				btn.setEnabled(true);
			}
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			handleEmptyTextField(e);
		}

		private boolean handleEmptyTextField(DocumentEvent e) {
			if(e.getDocument().getLength()==0){
				btn.setEnabled(false);
				alreadyEnable=false;
				return true;
			}
			return false;
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			if(!alreadyEnable){
				btn.setEnabled(true);
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = employeeName.getText();
			//user didn't type in a unique name
			if(name.equals("")||alreadyInList(name)){
				Toolkit.getDefaultToolkit().beep();
				employeeName.requestFocusInWindow();//光标移到文本框
				employeeName.selectAll();//选中全部
				return;
			}
			int index = list.getSelectedIndex();//get selected index
			if(index==-1){//no selection,so insert at beginning
				index=0;
			}else{	//add after the selected item
				index++;
			}
			listModel.insertElementAt(employeeName.getText(),index);
			//if we just wanted to add to the end
			//listmodel.addElement(employeeName.getText())
			
			//reset the text field
			employeeName.requestFocusInWindow();
			employeeName.setText("");
			
			//select the new item and make it visible
			list.setSelectedIndex(index);
			list.ensureIndexIsVisible(index);
		}
		
		protected boolean alreadyInList(String name){
			return listModel.contains(name);
		}
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getValueIsAdjusting()==false){
			if(list.getSelectedIndex()==-1){
				fireButton.setEnabled(false);
			}else{
				fireButton.setEnabled(true);
			}
		}
	}

	private static void createAndShowGUI(){
		//create and set up the window
		JFrame frame = new JFrame("ListDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//create and set up the content pane
		JComponent content = new ListDemo();
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
	public void focusGained(FocusEvent e) {
		JTextField field = (JTextField)e.getSource();
		field.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 2));
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		JTextField field = (JTextField)e.getSource();
		field.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
		
	}
}
