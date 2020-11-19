package behavior;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.twelvemonkeys.image.ResampleOp;

import config.ConfigurationProvider;

public class DirectoryObserver {
	
	private ConfigurationProvider cp;
	
	public DirectoryObserver(ConfigurationProvider cp) {
		this.cp = cp;
	}
	
	public void observe() {
		
		ConfigurationProvider cp = new ConfigurationProvider();
		File directoryOriginal = new File (cp.getPath("path.original"));
		File directoryRefactor = new File (cp.getPath("path.refactor"));
		File[] currentImages = {};
		List<File> differenceAddImages = new ArrayList<>();
		List<File> differenceDeleteImages = new ArrayList<>();		
		List <String> extensions = new ArrayList<>(Arrays.asList(".jpg", ".jpeg", ".png"));
		
		while(true) {
			
			currentImages = directoryOriginal.listFiles(new FileFilter() {
				
				@Override
				public boolean accept(File pathname) {
					if(pathname.isFile() && cp.getExtensions().contains(pathname.getName().substring(pathname.getName().lastIndexOf(".")).toLowerCase()))
						return true;
					
					return false;
				}
			});
						
			if(currentImages.length != 0)
				Arrays.sort(currentImages);
								
			if(!Arrays.equals(currentImages, lastImages)) {								
				System.out.println("Directory - \"" + cp.getPath("path.original") + "\" has been changed");
					
				if(currentImages.length == 0) {					
					System.out.println("Directory - \"" + cp.getPath("path.original") + "\" is empty now, all files have been deleted");
					for(var f : refactorImages)
						f.delete();
					refactorImages = lastImages = currentImages;					
					continue;
				}
					
				System.out.println("FILES LIST:");
					
				for(var f : currentImages) {
					if(!Arrays.asList(lastImages).contains(f)) {
						differenceAddImages.add(f);
					}
					System.out.println(">>> " + f);
				}
				
				for(var f : lastImages) {					
					if(!Arrays.asList(currentImages).contains(f)) {
						differenceDeleteImages.add(f);
					}
				}
				
				if(!differenceAddImages.isEmpty()) {					
					System.out.println("To the directory - \"" + cp.getPath("path.original") + "\" has been added files: ");					
					for(var f : differenceAddImages) {
						System.out.println(">>> " + f);
						BufferedImage image = ImageIO.read(f);
						BufferedImageOp resampler = new ResampleOp(100, 100, ResampleOp.FILTER_LANCZOS);
						BufferedImage resampledImage = resampler.filter(image, null);
						ImageIO.write(resampledImage, "jpeg", new File (cp.getPath("path.refactor") + f.getName()));																
					}					
				}
				
				if(!differenceDeleteImages.isEmpty()) {					
					System.out.println("From the directory - \"" + cp.getPath("path.original") + "\" has been deleted files: ");					
					for(var f : differenceDeleteImages) {
						System.out.println(">>> " + f);
						new File (cp.getPath("path.refactor") + f.getName()).delete();												
					}									
				}
												
				lastImages = currentImages;
				differenceAddImages.clear();
				differenceDeleteImages.clear();				
			}
			
			refactorImages = directoryRefactor.listFiles(new FileFilter() {
				
				@Override
				public boolean accept(File pathname) {
					if(pathname.isFile() && extensions.contains(pathname.getName().substring(pathname.getName().lastIndexOf(".")).toLowerCase()))
						return true;
					
					return false;
				}
			});;	
			
			if(!Arrays.stream(refactorImages).map(r -> r.getName()).collect(Collectors.toList()).equals(Arrays.stream(currentImages).map(l -> l.getName()).collect(Collectors.toList()))) {
				
				for(var f : currentImages) {
					if(!Arrays.stream(refactorImages).map(rf -> rf.getName()).collect(Collectors.toList()).contains(f.getName())) {						
						BufferedImage image = ImageIO.read(f);
						BufferedImageOp resampler = new ResampleOp(100, 100, ResampleOp.FILTER_LANCZOS);
						BufferedImage resampledImage = resampler.filter(image, null);
						ImageIO.write(resampledImage, "jpeg", new File (cp.getPath("path.refactor") + f.getName()));							
					}
				}
								
				for(var f : refactorImages) {
					if(!Arrays.stream(currentImages).map(lf -> lf.getName()).collect(Collectors.toList()).contains(f.getName())) {
						f.delete();						
					}
				}
								
				refactorImages = directoryRefactor.listFiles(new FileFilter() {
					
					@Override
					public boolean accept(File pathname) {
						if(pathname.isFile() && extensions.contains(pathname.getName().substring(pathname.getName().lastIndexOf(".")).toLowerCase()))
							return true;
						
						return false;
					}
				});;				
			}			
			Thread.sleep(10000);			
		}		
	}
}
