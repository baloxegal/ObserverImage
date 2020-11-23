package behavior;

import java.io.File;
import java.util.Arrays;
import java.io.IOException;
import data.MapsData;
import data.Comparators;
import processors.ImageProcessor;
import config.ConfigurationProvider;

public class DirectoryObserver {
	
	private ConfigurationProvider cp;
	
	public DirectoryObserver(String typeFile) {
		this.cp = new ConfigurationProvider(typeFile);
	}
	
	public void observe() throws IOException, InterruptedException {
		MapsData md = new MapsData(cp);
		Comparators c = new Comparators(md, new ImageProcessor(cp));
		File[] refactorImages = md.getMapData("path.refactor");
		File[] lastImages = c.preCompare();
				
		while(true) {
			
			File[] currentImages = md.getMapData("path.original");
						
			if(currentImages.length != 0)
				Arrays.sort(currentImages);
								
			if(!Arrays.equals(currentImages, lastImages)) {								
				System.out.println("Directory - \"" + currentImages[0].getParent() + "\" has been changed");
					
				if(currentImages.length == 0) {					
					System.out.println("Directory - \"" + currentImages[0].getParent() + "\" is empty now, all files have been deleted");
					for(var f : refactorImages)
						f.delete();
					refactorImages = lastImages = currentImages;					
					continue;
				}
					
				System.out.println("FILES LIST:");
					
				for(var f : currentImages) {
					System.out.println(">>> " + f);
				}
				
				c.compare(currentImages, lastImages, refactorImages);
												
				lastImages = currentImages;				
			}
			
			refactorImages = md.getMapData("path.refactor");	
			
			c.compareRefact(currentImages, refactorImages);
			
			Thread.sleep(10000);			
		}		
	}
}
