package fileChoose;

import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;

/* Utils.java is used by FileChooserDemo2.java. */
public class Utils {
	public static final String jpeg="jpeg";
	public static final String jpg="jpg";
	public static final String gif="gif";
	public static final String tiff="tiff";
	public static final String tif="tif";
	public static final String png="png";
	
	/**
	 * get the extention of a file
	 */
	public static String getExtension(File f){
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf(".");
		if(i>0&&i<s.length()-1){
			ext = s.substring(i+1).toLowerCase();
		}
		return ext;
	}
	//return an imageicon, or null if the path was invalid
	protected static ImageIcon createImageIcon(String path) {
		URL imgurl = Utils.class.getResource(path);
		if(imgurl!=null){
			return new ImageIcon(imgurl);
		}else{
			System.err.println("could't find file"+path);
			return null;
		}
		
	}
}
