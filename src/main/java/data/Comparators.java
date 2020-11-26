package data;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import processors.FileProcessor;
import java.io.IOException;

public class Comparators {
	
	private MapsData md;
	
	public Comparators(MapsData md) {
		this.md = md;		
	}

	public File[] preCompare(File[] refactorObjects) throws IOException {		
		File[] lastObjects = md.getMapData("path.original");				
		if(lastObjects.length == 0 && refactorObjects.length != 0) {
			for(var f : refactorObjects) {
				f.delete();
			}		
		}		
		if(lastObjects.length != 0) {								
			System.out.println("Directory - \"" + lastObjects[0].getParent() + "\" contains");
			System.out.println("FILES LIST:");			
			for(var f : lastObjects) {														
				System.out.println(">>> " + f);				
			}						
			if(!Arrays.stream(refactorObjects).map(r -> r.getName()).collect(Collectors.toList()).equals(Arrays.stream(lastObjects).map(l -> l.getName()).collect(Collectors.toList()))) {
				for(var f : lastObjects) {
					if(!Arrays.stream(refactorObjects).map(rf -> rf.getName()).collect(Collectors.toList()).contains(f.getName())) {						
						new FileProcessor(md.getConfigurationProvider()).transform(f);				
					}
				}								
				for(var f : refactorObjects) {
					if(!Arrays.stream(lastObjects).map(lf -> lf.getName()).collect(Collectors.toList()).contains(f.getName())) {
						f.delete();						
					}
				}
			}			
		}
		return lastObjects;
	}
	
	public void compare(File[] currentObjects, File[] lastObjects) throws IOException {		
		if(Arrays.equals(currentObjects, lastObjects))
			return;		
		System.out.println("Directory - \"" + currentObjects[0].getParent() + "\" has been changed");				
		if(currentObjects.length == 0) {
			System.out.println("Directory - \"" + currentObjects[0].getParent() + "\" is empty now, all files have been deleted");			
			return;
		}				
		System.out.println("FILES LIST:");
		for(var f : currentObjects) {
			System.out.println(">>> " + f);
		}			
		List<File> differenceAddObjects = new ArrayList<>();
		List<File> differenceDeleteObjects = new ArrayList<>();		
		for(var f : currentObjects) {
			if(!Arrays.asList(lastObjects).contains(f)) {
				differenceAddObjects.add(f);
			}
		}		
		for(var f : lastObjects) {					
			if(!Arrays.asList(currentObjects).contains(f)) {
				differenceDeleteObjects.add(f);
			}
		}		
		if(!differenceAddObjects.isEmpty()) {					
			System.out.println("To the directory - \"" + currentObjects[0].getParent() + "\" has been added files: ");					
			for(var f : differenceAddObjects) {
				System.out.println(">>> " + f);
				new FileProcessor(md.getConfigurationProvider()).transform(f);														
			}					
		}		
		if(!differenceDeleteObjects.isEmpty()) {					
			System.out.println("From the directory - \"" + currentObjects[0].getParent() + "\" has been deleted files: ");					
			for(var f : differenceDeleteObjects) {
				System.out.println(">>> " + f);												
			}									
		}
	}
	
	public void compareRefact(File[] currentObjects, File[] refactorObjects) throws IOException {
		if(!Arrays.stream(refactorObjects).map(r -> r.getName()).collect(Collectors.toList()).equals(Arrays.stream(currentObjects).map(l -> l.getName()).collect(Collectors.toList()))) {
			for(var f : currentObjects) {
				if(!Arrays.stream(refactorObjects).map(rf -> rf.getName()).collect(Collectors.toList()).contains(f.getName())) {						
					new FileProcessor(md.getConfigurationProvider()).transform(f);					
				}
			}							
			for(var f : refactorObjects) {
				if(!Arrays.stream(currentObjects).map(lf -> lf.getName()).collect(Collectors.toList()).contains(f.getName())) {
					f.delete();						
				}
			}						
		}			
	}
}
