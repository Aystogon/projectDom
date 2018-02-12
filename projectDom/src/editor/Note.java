package editor;

public class Note {
	
	private NoteData data;
	private StringBuilder contents;
	
	public Note() {
		data = new NoteData();
		contents = new StringBuilder();
	}
	
	public void addToken(String token) {
		data.add(token);
	}
}
