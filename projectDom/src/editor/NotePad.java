package editor;

import structs.CircularLinkedList;

public class NotePad {
	
	private static NotePad instance;
	private CircularLinkedList<Note> list;
	private Note currentNote;
	private StringBuilder token;
	private String regexNumbersMatch;
	private String regexLettersMatch;
	private boolean readiedShift;
	
	/* Deny outside instantiation. */
	private NotePad() { 
		currentNote = null;
		readiedShift = false;
		token = new StringBuilder();
		regexNumbersMatch = ".*[0-9].*";
		regexLettersMatch = ".*[a-zA-Z].*";
		list = new CircularLinkedList<Note>();
	}
	
	/**
	 * Returns the only instance of the notepad.
	 * @return The instance of the notepad.
	 */
	public static NotePad getInstance() {
		if (instance == null) {
			instance = new NotePad();
		}
		return instance;
		
	}
	/**
	 * Process the given character along with the state of the current token.
	 * @param charName name of the character~
	 */
	public void processInput(String charName) {
		String curCharName = charName.toLowerCase();
		if (isWhiteSpace(curCharName)) {
			if (Command.isCommand(token.toString())) {
				processCommand(token.toString());
				System.out.println("a command was executed...");
				
			} else {
				if (currentNote != null) {
					token.append(getWhitespaceType(curCharName));
					currentNote.addToken(token.delete(0, token.length()).toString());
					System.out.println(token.toString());
				}
			}
			token.delete(0, token.length());
		} else {
			if (readiedShift && isLetterOrDigit(curCharName) && !curCharName.equals("shift")) {
				
				token.append(Character.toUpperCase(curCharName.charAt(0)));
				readiedShift = false;
			} else if (curCharName.equals("backspace") && token.length() > 0) {
				token.deleteCharAt(token.length() - 1);
			} else if (curCharName.equals("minus")) {
				token.append('-');
			} else if (curCharName.equals("shift")) {
				readiedShift = true;
			} else if (isLetterOrDigit(curCharName)) {
				token.append(curCharName);
			}
		}
		System.out.println(token.toString());
	}
	public void processCommand(String command) {
		String curCommand = command.toLowerCase();
		if (Command.Help.toString().equals(curCommand)) {
			// TODO conduct help command
		} else if (Command.Example.toString().equals(curCommand)) {
			// TODO condut example command
		} else if (Command.Init.toString().equals(curCommand)) {
			// TODO conduct init command
			currentNote = new Note();
			System.out.println("new not was created");
		}
	}
	public Character getWhitespaceType(String charName) {
		switch(charName) {
			case "tab": 
			{
				return '\t';
			} 
			case "space":
			{
				return ' ';
			}
			case "enter":
			{
				return '\n';
			}
		}
		return null;
	}
	/**
	 * Determines if the given string represents either a letter or a digit.
	 * @param charName name of the supposed letter or digit.
	 * @return true if the given string represents a letter or a digit.
	 */
	private boolean isLetterOrDigit(String charName) {
		if (charName.matches(regexLettersMatch) || charName.matches(regexNumbersMatch)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Determines if the given string represents the name of a whitespace character.
	 * @param charName name of the whitespace character.
	 * @return true if the given charName represents a whitespace character.
	 */
	private boolean isWhiteSpace(String charName) {
		if (charName.equals("tab") || charName.equals("space") || charName.equals("enter")) {
			return true;
		} else {
			return false;
		}
	}
}
