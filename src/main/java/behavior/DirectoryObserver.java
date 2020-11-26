package behavior;

import java.io.File;
import data.MapsData;
import data.Comparators;
import config.ConfigurationProvider;
import java.io.IOException;

public class DirectoryObserver implements Runnable {
	
	private ConfigurationProvider cp;
	private MapsData md;
	private Comparators c;
	
	public DirectoryObserver(String typeFile) {
		cp = new ConfigurationProvider(typeFile);
		md = new MapsData(cp);
		c = new Comparators(md);
	}
	
	public ConfigurationProvider getConfigurationProvider(){
		return cp;
	}
	
	public void observe() throws IOException, InterruptedException {
		File[] lastObjects = c.preCompare(md.getMapData("path.refactor"));				
		while(true) {			
			File[] currentObjects = md.getMapData("path.original");			
			c.compare(currentObjects, lastObjects);
			lastObjects = currentObjects;
			c.compareRefact(currentObjects, md.getMapData("path.refactor"));
			Thread.yield();
			Thread.sleep(500);			
		}		
	}

	@Override
	public void run() {
		try {
			this.observe();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
}
