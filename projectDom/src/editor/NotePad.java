package editor;

import main.Command;
import structs.CircularLinkedList;

public class NotePad {
	
	private static NotePad instance;
	private CircularLinkedList<Note> list;
	private Note currentNote;
	private Note commandNote;
	private StringBuilder token;
	private String regexNumbersMatch;
	private String regexLettersMatch;
	private boolean readiedShift;
	private boolean readiedDocument;
	
	/* Deny outside instantiation. */
	private NotePad() { 
		currentNote = new Note();
		readiedShift = false;
		readiedDocument = false;
		commandNote = new Note();
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
	 * @param charName name of the character~ represented by a string.
	 */
	public void processInput(String charName) {
		String curCharName = charName.toLowerCase();
		if (isWhiteSpace(curCharName)) {
			if (Command.isCommand(token.toString())) {
				processCommand(token.toString());
				currentNote.process(token.toString());
			}
			token.delete(0, token.length());
		} else {
			processNonWhitespaceInput(curCharName);
		}
		System.out.println(token.toString());
	}
	/**
	 * Signals the respective command is wanted.
	 * @param command assumed command pattern.
	 */
	public void processCommand(String command) {
		String curCommand = command.toLowerCase();
		if (Command.Help.getPattern().equals(curCommand)) {
			// TODO execute help command
		} else if (Command.Example.getPattern().equals(curCommand)) {
			// TODO execute example command
		} else if (Command.Note.getPattern().equals(curCommand)) {
			currentNote = new Note();
		} else if (Command.Save.getPattern().equals(curCommand)) {
			readiedDocument = true; // Signals that notepad is ready to accept input in entirety.
		}
	}

	/**
	 * Process the input for character names that are not associated with 
	 * whitespace characters. I.g. characters not including whitespace, 
	 * @param curCharName
	 */
	public void processNonWhitespaceInput(String curCharName) {
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
	/**
	 * returns the appropriate white space character the givens tring
	 * represents; tab, space, and enter.
	 * @param charName name of the assumed whitespace character.
	 * @return null if the given string does not represent a whitespace.
	 */
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
		return '\0';
	}
	/**
	 * Saves the note written by the user
	 * @param area
	 */
	public void saveNote(String area) {
		String init = Command.Note.getPattern();
		String fina = Command.Save.getPattern();
		int startingIndex = area.toLowerCase().lastIndexOf(init) + init.length();
		int endingIndex = area.toLowerCase().lastIndexOf(fina);
		String actual = area.substring(startingIndex, endingIndex);
		currentNote.setContents(actual);
		list.add(currentNote);
		readiedDocument = false;
	}
	/**
	 * Returns the current note the cursor is pointing towards.
	 * @return the current note the current cursor is pointing towards.
	 */
	public String getCurrentNote() {
		return list.atCursor().getContents();
	}
	/**
	 * Returns true if the document is ready to be retrieved.
	 * @return true if the document is ready to be retrieved.
	 */
	public boolean isReady() {
		return readiedDocument;
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
