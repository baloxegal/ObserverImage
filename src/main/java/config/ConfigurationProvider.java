package config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigurationProvider {
	
	public String getPath(String key) {
		if(key.equals("path.original"))
			return "images/original/";
		else if(key.equals("path.refactor"))
			return "images/refactor/";
		return null;
	}
	
	public List <String> getExtensions() {
		List <String> extensions = new ArrayList<>(Arrays.asList(".jpg", ".jpeg", ".png"));
		return extensions;
	}	
}
