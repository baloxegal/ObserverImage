package main;

import behavior.DirectoryObserver;
import java.io.IOException;

public class Application {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		System.out.println("STARTING!!!");
		
		DirectoryObserver dirObserver = new DirectoryObserver("image");
		dirObserver.observe();
						
		System.out.println("FINISHING!!!");

	}

}
