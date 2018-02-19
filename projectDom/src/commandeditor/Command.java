package commandeditor;

public enum Command {
	
	Help("help", "-help"," To use a command, type \"-example\""),
	Example("Example", "-example", " example commands: -path , -start , -delete , -stop"
			+ " , -current, -next, -edit, -stop, -save, -close, -settings ..."),
	Path("Path", "-path", "-path, Shows the current path of the program."),
	Files("Files", "-files", "-files, shows the files in the current directory"),
	Delete("Delete", "-delete", "-delete, Deletes the current item."),
	Current("Current", "-current", "-current, Shows the current item."),
	Next("Next", "-next", "-next, Shifts the cursor to the next item, goes to first item if at the end."),
	Start("Note", "-start", "-start, Starts tracking input for the user"),
	Edit("Edit", "-edit", "-edit, Gets the current item to be edited."),
	Stop("Stop", "-stop", "-stop, Stops tracking input from the uset"),
	Save("Save", "-save", "-save, Saves the contents of the current item to a file. "
			+ "\n It is important that the very next token is a file name with a desired extention"
			+ "\n there must have been a previously \"stopped\" note loaded into memory already"
			+ "\n (i.g. to save a java file named main, type: \"-save main.java\""),
	Close("Close", "-close", "-close, Ends/exits the program."),
	Settings("Settings", "-settings", "-settings, opens up current settings of the notepad.");
	
	private String name;
	private String pattern;
	private String description;
	private boolean isBeingUsed;
	
	Command(String name, String pattern, String description) {
		this.name = name;
		this.pattern = pattern;
		this.description = description;
		this.isBeingUsed = false;
	}
	/**
	 * Returns the name of the command.
	 * @return The name of the command.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Returns the command pattern to activate the command.
	 * @return The pattern to activate the command.
	 */
	public String getPattern() {
		return pattern;
	}
	/**
	 * Returns the command description.
	 * @return The description of the command.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Returns true if the command is in use, else false.
	 * @return true if and only if the command is in use.
	 */
	public boolean isInUse() {
		return isBeingUsed;
	}
	public void isInUse(boolean val) {
		isBeingUsed = val;
	}
	/**
	 * Determines if the given string is a command.
	 * @param str String to check
	 * @return true if the given string is a command pattern.
	 */
	public static boolean isCommand(String pat) {
		String pattern = pat.toLowerCase();
		if (pattern.toLowerCase().equals(Command.Delete.pattern) ||
			pattern.toLowerCase().equals(Command.Settings.pattern) ||
		    pattern.toLowerCase().equals(Command.Help.pattern) ||
		    pattern.toLowerCase().equals(Command.Example.pattern) ||
		    pattern.toLowerCase().equals(Command.Files.pattern) ||
		    pattern.toLowerCase().equals(Command.Path.pattern) ||
		    pattern.toLowerCase().equals(Command.Next.pattern) ||
		    pattern.toLowerCase().equals(Command.Current.pattern) ||
		    pattern.toLowerCase().equals(Command.Stop.pattern) ||
		    pattern.toLowerCase().equals(Command.Edit.pattern) ||
		    pattern.toLowerCase().equals(Command.Close.pattern) ||
		    pattern.toLowerCase().equals(Command.Save.pattern) ||
		    pattern.toLowerCase().equals(Command.Start.pattern)) {
			System.out.println(pattern + " <<< is the pattern!");
			return true;
		} else {
			return false;
		}
	}
}
