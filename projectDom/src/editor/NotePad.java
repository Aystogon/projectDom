package editor;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import main.AppData;
import structs.CircularLinkedList;

public class NotePad {
	
	private static NotePad instance;
	private CircularLinkedList<Note> list;
	private Note currentNote;
	private StringBuilder token;
	private String regexNumbersMatch;
	private String regexLettersMatch;
	private boolean isStarted;
	private boolean isStopped;
	private boolean isEditing;
	private boolean readiedShift;
	private boolean isAwaitingParam;
	private Queue<String> prompts;
	private NoteData allNote;
	
	/* Deny outside instantiation. */
	private NotePad() { 
		isStarted = false;
		isStopped = false;
		isEditing = false;
		isAwaitingParam = false;
		currentNote = null;
		readiedShift = false;
		allNote = new NoteData();
		prompts = new LinkedList<String>();
		token = new StringBuilder();
		regexNumbersMatch = ".*[0-9].*";
		regexLettersMatch = ".*[a-zA-Z].*";
		list = new CircularLinkedList<Note>();
		allNote.incrementTokenCount();
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
		allNote.incrementCharacterCount();
		if (isWhiteSpace(curCharName)) {
			if (isAwaitingParam && !list.isEmpty()) {
				NoteIOHandler.getInstance().writeToFile(list.atCursor().getContents(), token.toString());
				isAwaitingParam = false;
			} else if (Command.isCommand(token.toString())) {
				processCommand(token.toString());
			} 
			
			allNote.incrementTokenCount();
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
			prompts.offer(Command.Help.getDescription());
		} else if (Command.Example.getPattern().equals(curCommand)) {
			loadCommandsIntoQueue();
		} else if (Command.Start.getPattern().equals(curCommand)) {
			if (isStarted || isStopped || isEditing) { return; }
			isStarted = true;
			currentNote = new Note();
		} else if (Command.Stop.getPattern().equals(curCommand)) {
			if (isStarted == false) { return; }
			isStopped = true; // notepad is ready for actual text.
		} else if (Command.Edit.getPattern().equals(curCommand)) {
			if (isStarted || isStopped || isEditing) { return; }
			isStarted = true;
			isEditing = true;
			currentNote = list.atCursor();
			prompts.offer(currentNote.getContents());
		} else if (Command.Next.getPattern().equals(curCommand)) {
			list.shift();
		} else if (Command.Save.getPattern().equals(curCommand)) {
			isAwaitingParam = true;
		} else if (Command.Path.getPattern().equals(curCommand)) {
			prompts.offer(NoteIOHandler.getInstance().getCurrentDirectoy());
		} else if (Command.Current.getPattern().equals(curCommand)) {
			if (list.isEmpty()) { prompts.offer("Nothing is saved"); return; }
			prompts.offer(list.atCursor().getContents());
			prompts.offer(list.atCursor().getStats() + " >> LOCAL");
			prompts.offer(allNote.toString() + " >> GLOBAL");
		} else if (Command.Delete.getPattern().equals(curCommand)) {
			if (list.length() < 1) { return; }
			list.remove();
		} else if (Command.Files.getPattern().equals(curCommand)) {
			prompts.offer(NoteIOHandler.getInstance().getFilesOfDirectory());
		} else if (Command.Close.getPattern().equals(curCommand)) {
			System.exit(0);
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
		} else if (curCharName.equals("period")) {
			token.append('.');
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
		if (currentNote == null) { return; }
		
		if (isEditing) {
			saveEditNote(area);
			return;
		}
		
		String start = Command.Start.getPattern();
		String stop = Command.Stop.getPattern();
		int startingIndex = area.toLowerCase().lastIndexOf(start) + start.length();
		int endingIndex = area.toLowerCase().lastIndexOf(stop);
		
		if (startingIndex < 0 || endingIndex < 0) { return; }
		String actual = area.substring(startingIndex, endingIndex);
		currentNote.process(actual);
		currentNote.setContents(actual);
		list.add(currentNote);
	}
	/**
	 * Adjusts the contents of the current note the cursor is currently at.
	 * @param area the text to be saved.
	 */
	public void saveEditNote(String area) {
		String edit = Command.Edit.getPattern();
		String stop = Command.Stop.getPattern();
		int startingIndex = area.toLowerCase().lastIndexOf(edit) + edit.length();
		int endingIndex = area.toLowerCase().lastIndexOf(stop);
		
		if (startingIndex < 0 || endingIndex < 0) { return; }
		String actual = area.substring(startingIndex, endingIndex);
		currentNote.process(actual);
		currentNote.setContents(actual);
		isEditing = false;
	}
	/**
	 * Empty any information about the current state of the Notepad.
	 * @return Information about the current state of the notepad.
	 */
	public String emptyDetailsToUser() {
		StringBuilder sb = new StringBuilder();
		while (!prompts.isEmpty()) {
			String curr = prompts.poll();
			if (Command.isCommand(curr)) {
				sb.append("[" + curr + "]");
			} else  {
				sb.append((sb.length() > 0 ? "\n" : "") + curr);
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
	 * Adds all the known commands to the queue, for the user.
	 */
	private void loadCommandsIntoQueue() {
		for (int i = 1; i < Command.values().length; i++) {
			prompts.offer(Command.values()[i].getDescription());
		}
	}
	/**
	 * Returns true if the document is ready to be retrieved.
	 * @return true if the document is ready to be retrieved.
	 */
	public boolean isReady() {
		if (isStarted && isStopped) {
			isStarted = false;
			isStopped = false;
			System.out.println("MAKING A NEW NOTE!");
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
