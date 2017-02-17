package fileChoose;

import java.io.File;

import javax.swing.filechooser.FileFilter;
/* ImageFilter.java is used by FileChooserDemo2.java. */
public class ImageFilter extends FileFilter {
	

	@Override
	//accept all directories and all gif ,jpg,tiff,or png files
	public boolean accept(File f) {
		if(f.isDirectory()){
			return true;
		}
		String exten = Utils.getExtension(f);
		if(exten!=null){
			if(exten.equals(Utils.tif)||
				exten.equals(Utils.tiff)||
				exten.equals(Utils.jpeg)||
				exten.equals(Utils.jpg)||
				exten.equals(Utils.png)||
				exten.equals(Utils.gif)){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}

	@Override
	public String getDescription() {
		// TODO 自动生成的方法存根
		return null;
	}

}
