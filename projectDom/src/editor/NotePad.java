package editor;

import java.util.LinkedList;
import java.util.Queue;

import main.AppData;
import main.Command;
import structs.CircularLinkedList;

public class NotePad {
	
	private static NotePad instance;
	private CircularLinkedList<Note> list;
	private Note currentNote;
	private StringBuilder token;
	private String regexNumbersMatch;
	private String regexLettersMatch;
	private boolean readiedShift;
	private boolean readied;
	private boolean readiedToSave;
	private Queue<String> prompts;
	
	/* Deny outside instantiation. */
	private NotePad() { 
		currentNote = null;
		readiedShift = false;
		readiedToSave = false;
		readied = false;
		prompts = new LinkedList<String>();
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
				if (currentNote != null) {
					currentNote.process(token.toString());
				}
			}
			token.delete(0, token.length());
		} else {
			processNonWhitespaceInput(curCharName);
		}
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
			readiedToSave = true;
			readied = true; // Signals that notepad is ready to accept input in entirety.
		} else if (Command.Next.getPattern().equals(curCommand)) {
			list.shift();
			System.out.println(list.atCursor().getContents() + " is at the cursor! " + list.length() + " - " + list.toString());
		} else if (Command.Path.getPattern().equals(curCommand)) {
			readied = true;
			prompts.offer(AppData.getInstance().getCurrentDirectoy());
		} else if (Command.Current.getPattern().equals(curCommand)) {
			if (list.isEmpty() || currentNote == null) { return; }
			readied = true;
			prompts.offer(list.atCursor().getContents());
			System.out.println(list.atCursor().getContents());
		} else if (Command.Delete.getPattern().equals(curCommand)) {
			if (list.length() == 1) { return; }
			list.remove();
		} else if (Command.Paths.getPattern().equals(curCommand)) {
			// TODO execute the paths command.
		}
	}

	/**
	 * Process the input for character names that are not associated with 
	 * whitespace characters. I.g. characters not including whitespace, 
	 * @param curCharName string which represents a letter or digit.
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
	 * @param area note to be saved.
	 */
	public void saveNote(String area) {
		if (!readiedToSave) { return; }
		String init = Command.Note.getPattern();
		String fina = Command.Save.getPattern();
		int startingIndex = area.toLowerCase().lastIndexOf(init) + init.length();
		int endingIndex = area.toLowerCase().lastIndexOf(fina);
		
		if (startingIndex < 0 || endingIndex < 0) { return; }
		System.out.println(startingIndex + " .... " + endingIndex);
		String actual = area.substring(startingIndex, endingIndex);
		if (currentNote == null) { return; }
		currentNote.setContents(actual);
		list.add(currentNote);
		readiedToSave = false;
	}
	/**
	 * Emptys any information about the current state of the Notepad.
	 * @return Information about the current state of the notepad.
	 */
	public String emptyDetailsToUser() {
		StringBuilder sb = new StringBuilder();
		while (!prompts.isEmpty()) {
			String curr = prompts.poll();
			if (Command.isCommand(curr)) {
				sb.append("[" + curr + "]");
			} else  {
				sb.append("\n" + curr);
			}
		}
		return sb.toString();
	}
	/**
	 * Returns the current note the cursor is pointing towards.
	 * @return the current note the current cursor is pointing towards.
	 */
	public String getCurrentNote() {
		if (list.length() == 0) {
			return "";
		} else {
			return list.atCursor().getContents();
		}
	}
	/**
	 * Returns true if the document is ready to be retrieved.
	 * @return true if the document is ready to be retrieved.
	 */
	public boolean isReady() {
		if (readied) {
			readied = false;
			return true;
		} else {
			return false;
		}
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
