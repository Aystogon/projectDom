package editor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class NoteIOHandler {
	
	private static NoteIOHandler instance;
	private String currentDirectory;
	private String homeDirectory;
	
	private NoteIOHandler() {
		currentDirectory = System.getProperty("user.dir");
		homeDirectory = System.getProperty("user.home");
	}
	/**
	 * Returns the instance of the note input and output handler!
	 * @return the instance of the input and output handler.
	 */
	public static NoteIOHandler getInstance() {
		if (instance == null) {
			instance = new NoteIOHandler();
		}
		return instance;
	}
	/**
	 * attempts to save the first given string into a file with the given name
	 * which includes filename along with an extention.
	 * @param contents data to be added to the file.
	 * @param fileNameExt name of the file.
	 */
	public void writeToFile(String contents, String fileNameExt) {
		System.out.println(fileNameExt);
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(fileNameExt, false));
			bw.write(contents);
			bw.flush();
		} catch (Exception ex) {
			System.err.println("Buffer writer creation exception.");
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException ioe) {
					System.err.println("Buffer writer close exception.");
				}
			}
		}
	}
	public String getFilesOfDirectory() {
		StringBuilder sb = new StringBuilder();
		File c = new File(currentDirectory);
		File[] filesList = c.listFiles();
        for(File f : filesList){
            if(f.isDirectory())
            	sb.append(f.getName() + '\n');
            if(f.isFile()){
            	sb.append(f.getName() + '\n');
            }
        }
        return sb.toString();
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
