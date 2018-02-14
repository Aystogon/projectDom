package editor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class NoteConverter {
	
	public NoteConverter(String contents, String fileNameExt) {
		writeToFile(contents, fileNameExt);
	}
	/**
	 * attempts to save the first given string into a file with the given name
	 * which includes filename along with an extention.
	 * @param contents data to be added to the file.
	 * @param fileNameExt name of the file.
	 */
	private void writeToFile(String contents, String fileNameExt) {
		System.out.println(fileNameExt);
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(fileNameExt, true));
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
}
