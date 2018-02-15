package editor;

public class NoteData {
	
	private int characterCount;
	private int tokenCount;
	private int maxWPM;
	private int maxCPM;
	
	public NoteData() {
		characterCount = 0;
		tokenCount = 0;
		maxWPM = 0;
		maxCPM = 0;
	}
	
	/**
	 * processes the given string into information.
	 * @param token string to be processed.
	 */
	public void process(String text) {
		reset();
		for (int i = 0; i < text.length(); i++) {
			characterCount++;
			if (text.charAt(i) == ' ' || text.charAt(i) == '\t' || text.charAt(i) == '\n') {
				tokenCount++;
			}
		}
		tokenCount--;
	}
	public void reset() {
		characterCount = 0;
		tokenCount = 0;
		maxWPM = 0;
		maxCPM = 0;
	}
	public void incrementCharacterCount() {
		characterCount++;
	}
	public void incrementTokenCount() {
		tokenCount++;
	}
	public int getCharacterCount() {
		return characterCount;
	}
	public int getTokenCount() {
		return tokenCount;
	}
	public int getWordsPerMinute() {
		return maxWPM;
	}
	public int getCharactersPerMinute() {
		return maxCPM;
	}
	@Override
	public String toString() {
		return "\n[Approximate Characters: " + characterCount + "][Approximate Tokens: " + tokenCount + "]";
	}
}
