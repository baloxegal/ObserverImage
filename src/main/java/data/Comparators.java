package data;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;
import java.util.stream.Collectors;
import processors.FileProcessor;

public class Comparators {
	
	private MapsData md;
	
	public Comparators(MapsData md) {
		this.md = md;		
	}

	public File[] preCompare(File[] refactorImages) throws IOException {		
		File[] lastImages = md.getMapData("path.original");				
		if(lastImages.length == 0 && refactorImages.length != 0) {
			for(var f : refactorImages) {
				f.delete();
			}		
		}		
		if(lastImages.length != 0) {								
			System.out.println("Directory - \"" + lastImages[0].getParent() + "\" contains");
			System.out.println("FILES LIST:");			
			for(var f : lastImages) {														
				System.out.println(">>> " + f);				
			}						
			if(!Arrays.stream(refactorImages).map(r -> r.getName()).collect(Collectors.toList()).equals(Arrays.stream(lastImages).map(l -> l.getName()).collect(Collectors.toList()))) {
				for(var f : lastImages) {
					if(!Arrays.stream(refactorImages).map(rf -> rf.getName()).collect(Collectors.toList()).contains(f.getName())) {						
						new FileProcessor(md.getConfigurationProvider()).transform(f);				
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
	
	public void compare(File[] currentImages, File[] lastImages) throws IOException {		
		if(Arrays.equals(currentImages, lastImages))
			return;		
		System.out.println("Directory - \"" + currentImages[0].getParent() + "\" has been changed");				
		if(currentImages.length == 0) {
			System.out.println("Directory - \"" + currentImages[0].getParent() + "\" is empty now, all files have been deleted");			
			return;
		}				
		System.out.println("FILES LIST:");
		for(var f : currentImages) {
			System.out.println(">>> " + f);
		}			
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
				new FileProcessor(md.getConfigurationProvider()).transform(f);														
			}					
		}		
		if(!differenceDeleteImages.isEmpty()) {					
			System.out.println("From the directory - \"" + currentImages[0].getParent() + "\" has been deleted files: ");					
			for(var f : differenceDeleteImages) {
				System.out.println(">>> " + f);												
			}									
		}
	}
	
	public void compareRefact(File[] currentImages, File[] refactorImages) throws IOException {
		if(!Arrays.stream(refactorImages).map(r -> r.getName()).collect(Collectors.toList()).equals(Arrays.stream(currentImages).map(l -> l.getName()).collect(Collectors.toList()))) {
			for(var f : currentImages) {
				if(!Arrays.stream(refactorImages).map(rf -> rf.getName()).collect(Collectors.toList()).contains(f.getName())) {						
					new FileProcessor(md.getConfigurationProvider()).transform(f);					
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
