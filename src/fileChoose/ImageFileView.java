package fileChoose;

import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileView;

/* ImageFileView.java is used by FileChooserDemo2.java. */
public class ImageFileView extends FileView{
	ImageIcon jpgIcon = Utils.createImageIcon("jpgIcon.gif");
	ImageIcon gifIcon = Utils.createImageIcon("gifIcon.gif");
	ImageIcon tiffIcon = Utils.createImageIcon("tiffIcon.gif");
	ImageIcon pngIcon = Utils.createImageIcon("pngIcon.gif");
	@Override
	public String getName(File f) {
		return f.getName();
	}
	@Override
	public String getDescription(File f) {
		return f.getAbsolutePath();
	}
	@Override
	public String getTypeDescription(File f) {
		String exten = Utils.getExtension(f);
		String type=null;
		if(exten!=null){
			if(exten.equals(Utils.jpeg)||exten.equals(Utils.jpg)){
				type="JPEG Image";
			}else if(exten.equals(Utils.gif)){
				type="GIF Image";
			}else if(exten.equals(Utils.tiff)||exten.equals(Utils.tif)){
				type="TIFF Image";
			}else if(exten.equals(Utils.png)){
				type="PNG Image";
			}
		}
		return type;
	}
	@Override
	public Icon getIcon(File f) {
		String extension = Utils.getExtension(f);
		Icon icon=null;
		if(extension!=null){
			if(extension.equals(Utils.jpeg)||extension.equals(Utils.jpg)){
				icon = jpgIcon;
			}else if(extension.equals(Utils.gif)){
				icon = gifIcon;
			}else if(extension.equals(Utils.tiff)||extension.equals(Utils.tiff)){
				icon = tiffIcon;
			}else if(extension.equals(Utils.png)){
				icon = pngIcon;
			}
		}
		return icon;
	}
	
	@Override
	public Boolean isTraversable(File f) {
		return null;
	}
}
