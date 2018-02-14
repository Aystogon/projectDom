package main;

import java.awt.Dimension;
import java.awt.Toolkit;

public class AppData {
	
	private static AppData instance;
	
	private int windowWidth;
	private int windowHeight;
	
	/**
	 * Initializes information for the application.
	 */
	private AppData() { 
		Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
		windowWidth = ((int)ss.getWidth()/4)*3;
		windowHeight = ((int)ss.getHeight()/4)*3;
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
}
