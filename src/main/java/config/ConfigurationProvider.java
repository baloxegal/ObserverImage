package config;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ConfigurationProvider {
	
	private String typeFile;
	
	public ConfigurationProvider(String typeFile) {
		if(this.getTypeFileList().contains(typeFile))	
			this.typeFile = typeFile;
	}
	
	public String getTypeFile() {
		return typeFile;
	}
	
	public List<String> getTypeFileList() {
		return new ArrayList<>(Arrays.asList("image", "music"));		
	}
	
	public String getPath(String key) {
		if(typeFile.equals("image")) {		
			if(key.equals("path.original"))
				return "images/original/";
			else if(key.equals("path.refactor"))
				return "images/refactor/";			
		}
		else if(typeFile.equals("music")) {		
			if(key.equals("path.original"))
				return "music/original/";
			else if(key.equals("path.refactor"))
				return "music/refactor/";
		}
		return null;
	}
	
	public List <String> getExtensions() {
		if(typeFile.equals("image")){
			return new ArrayList<>(Arrays.asList("jpeg", "jpg", "png"));
		}
		else if(typeFile.equals("music")){
			return new ArrayList<>(Arrays.asList("mp3", "wav"));
		}
		return null;
	}	
}
