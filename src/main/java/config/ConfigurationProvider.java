package config;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ConfigurationProvider {
	
	private String typeFile;
	
	public ConfigurationProvider(String typFile) {
		this.typeFile = typFile;
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
		return key;
	}
	
	public List <String> getExtensions() {
		if(typeFile.equals("image")){
			return new ArrayList<>(Arrays.asList(".jpg", ".jpeg", ".png"));
		}
		else if(typeFile.equals("music")){
			return new ArrayList<>(Arrays.asList(".mp3", ".wav"));
		}
		return null;
	}	
}
