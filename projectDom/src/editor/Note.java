package editor;

public class Note {
	
	private NoteData data;
	private StringBuilder contents;
	
	/**
	 * Default constructor for the note
	 */
	public Note() {
		data = new NoteData();
		contents = new StringBuilder();
	}
	/**
	 * Item/Token to be processed for statistics
	 * @param token token/item to be processed for statistics.
	 */
	public void process(String token) {
		data.process(token);
	}
	/**
	 * Sets the contents of the note to the contents of the given string.
	 * @param str contents to set the main component of the string 
	 */
	public void setContents(String str) {
		contents.delete(0, contents.length());
		contents.append(str);
	}
	/**
	 * Returns the contents held within main part of the note.
	 * @return the contents of the note.
	 */
	public String getContents() {
		return contents.toString();
	}
	public String getStats() {
		return data.toString();
	}
}
