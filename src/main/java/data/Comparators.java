package data;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import processors.ImageProcessor;
import config.ConfigurationProvider;

public class Comparators {
	
	private MapsData md = new MapsData(new ConfigurationProvider());
	private ImageProcessor ip = new ImageProcessor(new ConfigurationProvider());
	
	public Comparators(MapsData md, ImageProcessor ip) {
		this.md = md;
		this.ip = ip;
	}

	public File[] preCompare() throws IOException {
		
		File[] lastImages = md.getMapData("path.original");
		File[] refactorImages = md.getMapData("path.refactor");
		
		if(lastImages.length == 0 && refactorImages.length != 0) {
			for(var f : refactorImages) {
				f.delete();
			}		
		}
		
		if(lastImages.length != 0) {
		
			Arrays.sort(lastImages);
			Arrays.sort(refactorImages);
			
			System.out.println("Directory - \"" + lastImages[0].getParent() + "\" contains");
			System.out.println("FILES LIST:");
			
			for(var f : lastImages) {														
				System.out.println(">>> " + f);				
			}
						
			if(!Arrays.stream(refactorImages).map(r -> r.getName()).collect(Collectors.toList()).equals(Arrays.stream(lastImages).map(l -> l.getName()).collect(Collectors.toList()))) {
				
				for(var f : lastImages) {
					if(!Arrays.stream(refactorImages).map(rf -> rf.getName()).collect(Collectors.toList()).contains(f.getName())) {						
						ip.transform(f);				
					}
				}
								
				for(var f : refactorImages) {
					if(!Arrays.stream(lastImages).map(lf -> lf.getName()).collect(Collectors.toList()).contains(f.getName())) {
						f.delete();						
					}
				}						
			}			
		}
		return lastImages;
	}	
}
