package main;

import java.awt.Dimension;
import java.awt.Toolkit;

public class AppData {
	
	private static AppData instance;
	
	private int windowWidth;
	private int windowHeight;
	private String currentDirectory;
	private String homeDirectory;
	
	/**
	 * Initializes information for the application.
	 */
	private AppData() { 
		Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
		windowWidth = ((int)ss.getWidth()/4)*3;
		windowHeight = ((int)ss.getHeight()/4)*3;
		currentDirectory = System.getProperty("user.dir");
		homeDirectory = System.getProperty("user.home");
		
		System.out.println(currentDirectory + " " + homeDirectory);
	}
	/**
	 * Returns the single instance of AppData with system information.
	 * @return The App data instance.
	 */
	public static AppData getInstance() {
		if (instance == null) {
			instance = new AppData();
		}
		return instance;
	}
	
	
	public int getWindowWidth() {
		return windowWidth;
	}
	public int getWindowHeight() {
		return windowHeight;
	}
	public String getCurrentDirectoy() {
		return currentDirectory;
	}
	public String getHomeDirectory() {
		return homeDirectory;
	}
	public String getDesktopDirectory() {
		return homeDirectory + "/Desktop";
	}
}
