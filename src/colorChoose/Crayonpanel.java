package colorChoose;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.border.Border;
import javax.swing.colorchooser.AbstractColorChooserPanel;

public class Crayonpanel extends AbstractColorChooserPanel implements ActionListener {
	JToggleButton redCrayon;
	JToggleButton yellowCrayon;
	JToggleButton greenCrayon;
	JToggleButton blueCrayon;
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Color newColor = null;
		String command = ((JToggleButton)e.getSource()).getActionCommand();
		if("green".equals(command)){
			newColor = Color.GREEN;
		}else if("red".equals(command)){
			newColor = Color.RED;
		}else if("yellow".equals(command)){
			newColor = Color.YELLOW;
		}else if("blue".equals(command)){
			newColor = Color.BLUE;
		}
		getColorSelectionModel().setSelectedColor(newColor);
	}

	
	//invoke automatically when model's state change
	public void updateChooser() {
		Color c = getColorFromModel();
		if(Color.RED.equals(c)){
			//redCrayon.setSelected(true);
		}else if(Color.YELLOW.equals(c)){
			yellowCrayon.setSelected(true);
		}else if(Color.green.equals(c)){
			greenCrayon.setSelected(true);
		}else if(Color.blue.equals(c)){
			blueCrayon.setSelected(true);
		}
	}
	
	protected void builderChooser(){
		
	}
	
	private JToggleButton createCrayon(String string, Border border) {
		JToggleButton btn = new JToggleButton();
		btn.setActionCommand(string);
		btn.addActionListener(this);
		
		ImageIcon icon = getImage(string);
		if(icon==null){
			btn.setText("Image not found ,this is the "+string+"button");
			btn.setFont(btn.getFont().deriveFont(Font.ITALIC));
			btn.setHorizontalAlignment(JButton.HORIZONTAL);
			btn.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
			
		}else{
			btn.setIcon(icon);
			btn.setBorder(border);
			btn.setToolTipText("The "+string+"crayon");
		}
		return btn;
	}

	
	private ImageIcon getImage(String string) {
		URL url  = this.getClass().getResource("./images/"+string+".gif");
		if(url!=null){
			return new ImageIcon(url);
		}else{
			System.err.println("could find file");
			return null;
		}
	}

	@Override
	public String getDisplayName() {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public Icon getSmallDisplayIcon() {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public Icon getLargeDisplayIcon() {
		// TODO 自动生成的方法存根
		return null;
	}


	@Override
	//创建由选择器组成的GUI
	
	protected void buildChooser() {
		setLayout(new GridLayout(0, 1));
		ButtonGroup boxofCrayons = new ButtonGroup();
		Border border = BorderFactory.createLineBorder(Color.RED, 3, true);
		
		redCrayon = createCrayon("red",border);
		boxofCrayons.add(redCrayon);
		add(redCrayon);
		
		yellowCrayon = createCrayon("yellow",border);
		boxofCrayons.add(yellowCrayon);
		add(yellowCrayon);
		
		greenCrayon = createCrayon("green",border);
		boxofCrayons.add(greenCrayon);
		add(greenCrayon);
		
		blueCrayon = createCrayon("blue",border);
		boxofCrayons.add(blueCrayon);
		add(blueCrayon);
	}

	

}
