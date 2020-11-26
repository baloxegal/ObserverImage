package data;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import config.ConfigurationProvider;

public class MapsData {
	
	private ConfigurationProvider cp;
	
	public MapsData(ConfigurationProvider cp) {
		this.cp = cp;
	}
	
	public ConfigurationProvider getConfigurationProvider() {
		return cp;
	}
	
	public File getFile(String key) {
		File f = new File(cp.getPath(key));
		return f;
	}
	
	public File[] getMapData(String key) {
		File f = getFile(key);
		if(!f.exists()) {			
			System.out.println("Directory - \"" + cp.getPath(key) + "\" not found");
			System.out.println("Directory - \"" + cp.getPath(key) + "\" has been created");
			f.mkdirs();
		}
		File[] objects = null;
		if(cp.getPath(key).contains("refactor")) {
			objects = f.listFiles();		
		}
		else {
			objects = f.listFiles(new FileFilter() {			
				@Override
				public boolean accept(File pathname) {
					if(pathname.isFile() && cp.getExtensions().contains(pathname.getName().substring(pathname.getName().lastIndexOf(".") + 1).toLowerCase()))
						return true;				
					return false;
				}
			});}		
		if(objects.length != 0)
			Arrays.sort(objects);		
		return objects;		
	}
}
