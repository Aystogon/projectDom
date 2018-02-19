package texteditor;

import java.util.LinkedList;
import java.util.Queue;

import commandeditor.Command;
import commandeditor.CommandHandler;
import structs.CircularLinkedList;

public class NotePad {
	
	private static NotePad instance;
	private CircularLinkedList<Note> list;
	private Note currentNote;
	private StringBuilder token;
	private Queue<String> prompts;
	private NoteData globalNote;
	
	/* Deny outside instantiation. */
	private NotePad() {
		globalNote = new NoteData();
		prompts = new LinkedList<String>();
		token = new StringBuilder();
		list = new CircularLinkedList<Note>();
		globalNote.incrementTokenCount();
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
		globalNote.incrementCharacterCount();
		
		if (isWhiteSpace(curCharName)) {
			// Saves the current item to a file.
			if (Command.Save.isInUse() && !Command.Edit.isInUse() && !list.isEmpty()) {
				NoteIOHandler.getInstance().writeToFile(list.atCursor().getContents(), token.toString());
				Command.Save.isInUse(false);
			// Executes a command using the current token.
			} else if (Command.isCommand(token.toString())) {
				if (CommandHandler.processCommand(token.toString(), list, currentNote)) {
					currentNote = new Note();
				}
			}
			// Clear token for the next token
			globalNote.incrementTokenCount();
			token.delete(0, token.length());
		} else {
			CharProcessor.ProcessCharStringRep(curCharName, token);
		}
		prompts.offer(CommandHandler.CommandRelease());
	}
	/**
	 * Saves the note written by the user
	 * @param area note to be saved.
	 */
	public void saveNote(String area) {
		if (currentNote == null) { return; }
		
		if (Command.Edit.isInUse()) {
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
		
		if (startingIndex < 0 || endingIndex < 0) { Command.Edit.isInUse(false); return; }
		String actual = area.substring(startingIndex, endingIndex);

		currentNote.process(actual);
		currentNote.setContents(actual);
		Command.Edit.isInUse(false);
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
	 * Returns true if the document is ready to be retrieved.
	 * @return true if the document is ready to be retrieved.
	 */
	public boolean isReady() {
		if ((Command.Start.isInUse() || Command.Edit.isInUse()) && Command.Stop.isInUse()) {
			Command.Start.isInUse(false);
			Command.Stop.isInUse(false);
			System.out.println("MAKING A NEW NOTE!");
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
