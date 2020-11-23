package data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import processors.ImageProcessor;

public class Comparators {
	
	private MapsData md;
	private ImageProcessor ip;
	
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
	
	public void compare(File[] currentImages, File[] lastImages, File[] refactorImages) throws IOException {
		List<File> differenceAddImages = new ArrayList<>();
		List<File> differenceDeleteImages = new ArrayList<>();		
		for(var f : currentImages) {
			if(!Arrays.asList(lastImages).contains(f)) {
				differenceAddImages.add(f);
			}
		}
		
		for(var f : lastImages) {					
			if(!Arrays.asList(currentImages).contains(f)) {
				differenceDeleteImages.add(f);
			}
		}
		
		if(!differenceAddImages.isEmpty()) {					
			System.out.println("To the directory - \"" + currentImages[0].getParent() + "\" has been added files: ");					
			for(var f : differenceAddImages) {
				System.out.println(">>> " + f);
				ip.transform(f);														
			}					
		}
		
		if(!differenceDeleteImages.isEmpty()) {					
			System.out.println("From the directory - \"" + currentImages[0].getParent() + "\" has been deleted files: ");					
			for(var f : differenceDeleteImages) {
				System.out.println(">>> " + f);
				md.getFile(refactorImages[0].getParent() + f.getName()).delete();										
			}									
		}
										
		lastImages = currentImages;
	}
	
	public void compareRefact(File[] currentImages, File[] refactorImages) throws IOException {
		if(!Arrays.stream(refactorImages).map(r -> r.getName()).collect(Collectors.toList()).equals(Arrays.stream(currentImages).map(l -> l.getName()).collect(Collectors.toList()))) {
			
			for(var f : currentImages) {
				if(!Arrays.stream(refactorImages).map(rf -> rf.getName()).collect(Collectors.toList()).contains(f.getName())) {						
					ip.transform(f);					
				}
			}
							
			for(var f : refactorImages) {
				if(!Arrays.stream(currentImages).map(lf -> lf.getName()).collect(Collectors.toList()).contains(f.getName())) {
					f.delete();						
				}
			}						
		}			
	}
}
