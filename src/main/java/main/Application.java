package main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import behavior.DirectoryObserver;
import java.io.IOException;

public class Application {

	public static void main(String[] args) throws IOException, InterruptedException {		
		System.out.println("START CHECKING!!!");
		
//		Thread observer_1 = new Thread(new DirectoryObserver("image"));
//		Thread observer_2 = new Thread(new DirectoryObserver("music"));
//		observer_1.start();
//		observer_2.start();
		
		ExecutorService executor = Executors.newCachedThreadPool();		
		for(var typeFile : new DirectoryObserver(null).getConfigurationProvider().getTypeFileList()) {
			executor.execute(new DirectoryObserver(typeFile));			
		}	
	}
}
