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
		this.cp = new ConfigurationProvider(typeFile);
		md = new MapsData(cp);
		c = new Comparators(md);
	}
	
	public void observe() throws IOException, InterruptedException {
		File[] lastImages = c.preCompare(md.getMapData("path.refactor"));				
		while(true) {			
			File[] currentImages = md.getMapData("path.original");			
			c.compare(currentImages, lastImages);
			lastImages = currentImages;
			c.compareRefact(currentImages, md.getMapData("path.refactor"));
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
