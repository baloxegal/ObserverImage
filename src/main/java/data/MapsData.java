package data;

import java.io.File;
import java.io.FileFilter;

import config.ConfigurationProvider;

public class MapsData {
	
	private ConfigurationProvider cp = new ConfigurationProvider();
	
	public MapsData(ConfigurationProvider cp) {
		this.cp = cp;
	}
	
	public File getFile(String key) {
		File f = new File(cp.getPath(key));
		return f;
	}
	
	public File[] getMapData(String key) {
		File f = getFile(key);
		if(!f.exists()) {			
			System.out.println("Directory - \"" + cp.getPath(key) + "\" not found");			
			return null;
		}
		
		File[] images = f.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				if(pathname.isFile() && cp.getExtensions().contains(pathname.getName().substring(pathname.getName().lastIndexOf(".")).toLowerCase()))
					return true;
				
				return false;
			}
		});		
		return images;		
	}
}
