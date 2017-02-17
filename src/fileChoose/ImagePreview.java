package fileChoose;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
/* ImagePreview.java by FileChooserDemo2.java. */
public class ImagePreview extends JComponent implements PropertyChangeListener{
	ImageIcon thumbnail = null;
	File file =null;
	
	public ImagePreview(JFileChooser fc) {
		setPreferredSize(new Dimension(100, 50));
		fc.addPropertyChangeListener(this);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		boolean update = false;
		String prop = e.getPropertyName();
		//if the directory changed, don't show an image
		if(JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(prop)){
			file = null;
			update = true;
		//if a file became selected,find out which one
		}else if(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(prop)){
			file =(File) e.getNewValue();
			update=true;
		}
		
		//update the preview accordingly
		if(update){
			thumbnail=null;
			if(isShowing()){
				loadImage();
				repaint();
			}
		}
	}

	private void loadImage() {
		if(file==null){
			thumbnail=null;
			return;
		}
		ImageIcon icon = new ImageIcon(file.getPath());
		if(icon!=null){
			if(icon.getIconWidth()>90){
				thumbnail = new ImageIcon(icon.getImage().getScaledInstance(90, -1, Image.SCALE_DEFAULT));
			}else{
				thumbnail=icon;
			}
		}
	}
	
	@Override
	public void paint(Graphics g) {
		if(thumbnail==null){
			loadImage();
		}
		 if (thumbnail != null) {
	            int x = getWidth()/2 - thumbnail.getIconWidth()/2;
	            int y = getHeight()/2 - thumbnail.getIconHeight()/2;
	            if (y < 0) {
	                y = 0;
	            }
	            if (x < 5) {
	                x = 5;
	            }
	            thumbnail.paintIcon(this, g, x, y);
	        }
	}

}
