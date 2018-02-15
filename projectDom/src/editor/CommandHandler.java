package editor;

import java.util.LinkedList;
import java.util.Queue;

import structs.CircularLinkedList;

public class CommandHandler {
	
	private static Queue<String> commandDetails;
	
	private CommandHandler() { /* Deny instantiation */ }
	
	/**
	 * Ensures a single instance of command details is instantiated.
	 */
	private static void EnsureInstance() {
		if (commandDetails == null) {
			commandDetails = new LinkedList<String>();
		}
	}
	
	/**
	 * Processes the given string.
	 * @param command assumed command to operate on.
	 * @param list the list of notes to operate on.
	 * @param note the current note of the of the notepad.
	 * @return true if and only a new note needs to be created.
	 */
	public static boolean processCommand(String command, CircularLinkedList<Note> list, Note note) {
		EnsureInstance();
		
		System.out.println(command);
		
		String curCommand = command.toLowerCase();
		if (Command.Help.getPattern().equals(curCommand)) {
			
			/* Shows the very basic prompt to the user. */
			
			commandDetails.offer(Command.Help.getDescription());
			
		} else if (Command.Example.getPattern().equals(curCommand)) {
			
			/* Shows the commands the available commans to the user. */
			
			for (Command cmd : Command.values()) { commandDetails.offer(cmd.getDescription()); }
		} else if (Command.Start.getPattern().equals(curCommand) && isStartingEditingStopping()) {
			
			/* Tells notepad to start recording user input. */
			
			Command.Start.isInUse(true);
			return true;
			
		} else if (Command.Stop.getPattern().equals(curCommand) && (Command.Start.isInUse() || Command.Edit.isInUse())) {
			
			/* Tells notepad to stop recording user input. */
			Command.Stop.isInUse(true);
			
		} else if (Command.Edit.getPattern().equals(curCommand) && isStartingEditingStopping()) {
			
			/* Gets the current element for the user to edit. */
			Command.Edit.isInUse(true);
			Command.Save.isInUse(true);
			commandDetails.offer(note.getContents());
			
		} else if (Command.Next.getPattern().equals(curCommand)) {
			
			/* Changes the current element to the next most element, back to itself if list is empty. */
			
			list.shift();
			
		} else if (Command.Save.getPattern().equals(curCommand)) {
			
			/* Prompts note pad to save current element as a file.*/
			
			Command.Save.isInUse(true);
			
		} else if (Command.Path.getPattern().equals(curCommand)) {
			
			/* Gets the current directory and displays to the user. */
			
			commandDetails.offer(NoteIOHandler.getInstance().getCurrentDirectoy());
			
		} else if (Command.Current.getPattern().equals(curCommand) && !list.isEmpty()) {
			
			/* Gets the current element along with its stats. */
			commandDetails.offer(list.atCursor().getContents());
			commandDetails.offer(list.atCursor().getStats());
			
		} else if (Command.Delete.getPattern().equals(curCommand) && !list.isEmpty()) {
			
			/* Removes the current element from the list. */
			
			list.remove();
			
		} else if (Command.Files.getPattern().equals(curCommand)) {
			
			/* Shows the current contents located in the current directory. */
			
			commandDetails.offer(NoteIOHandler.getInstance().getFilesOfDirectory());
			
		} else if (Command.Close.getPattern().equals(curCommand)) {
			
			/* Exits the program. */
			
			System.exit(0);
		}
		
		return false;
	}
	
	/**
	 * Determines if the currented note is in a stages awaiting for some kind of input.
	 * The note is awaiting input if the user has started, stopped, or edited without finishing...
	 * @return true if the document is currently awaiting some kind of input.
	 */
	private static boolean isStartingEditingStopping() {
		if (Command.Start.isInUse() || Command.Stop.isInUse() || Command.Edit.isInUse()) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * Releases all information contained within the command queue.
	 * @return The string of commands.
	 */
	public static String CommandRelease() {
		EnsureInstance();
		
		StringBuilder sb = new StringBuilder();
		while (!commandDetails.isEmpty()) {
			sb.append("\n" + commandDetails.poll());
		}
		return sb.toString();
	}
}
