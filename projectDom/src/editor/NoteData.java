package editor;

public class NoteData {
	
	private int characterCount;
	private int tokenCount;
	private int maxWPM;
	private int maxCPM;
	private int missTypes;
	
	private String regexContainsNums;
	private String regexContainsLets;
	
	public NoteData() {
		regexContainsNums = ".*[0-9].*";
		regexContainsLets = ".*[a-zA-Z].*";
		
		characterCount = 0;
		tokenCount = 0;
		missTypes = 0;
		maxWPM = 0;
		maxCPM = 0;
	}
	
	/**
	 * processes the given string into information.
	 * @param token string to be processed.
	 */
	public void process(String token) {
		for (int i = 0; i < token.length(); i++) {
			if (Character.isLetter(token.charAt(i))) {
				characterCount = characterCount + 1;
			}
		}
		// A potential miss type from the user.
		if (token.matches(regexContainsLets) && token.matches(regexContainsNums)) {
			missTypes = missTypes + 1;
		}
		
		tokenCount = tokenCount + token.length();
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
}
