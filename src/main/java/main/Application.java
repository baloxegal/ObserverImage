package main;

import behavior.DirectoryObserver;
import config.ConfigurationProvider;
import processors.ImageProcessor;

public class Application {

	public static void main(String[] args) {
		
		System.out.println("STARTING");
		
		DirectoryObserver dirObserver = new DirectoryObserver(new ConfigurationProvider());
		dirObserver.observe();
		
		ImageProcessor ip = new ImageProcessor(new ConfigurationProvider());
		ip.transform(f);
		
		System.out.println("FINISHING");

	}

}
