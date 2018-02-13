package main;

public enum Command {
	
	Help("help", "-help"," To use a command, type \"-<example>\"."),
	Example("Example", "-example", " -path , -dele , -init , -fina , -sett, -close..."),
	Path("Path", "-path", "shows the current path of the processor."),
	Delete("Delete", "-delete", "removes the current note"),
	Note("Note", "-note", "Initializes a new txt document"),
	Save("Save", "-save", "saves the current document"),
	Close("Close", "-close", "Endss/exits the program."),
	Settings("Settings", "-settings", "Opens up current settings of the notepad.");
	
	private String name;
	private String pattern;
	private String description;
	
	Command(String name, String pattern, String description) {
		this.name = name;
		this.pattern = pattern;
		this.description = description;
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
	 * Determines if the given string is a command.
	 * @param str String to check
	 * @return true if the given string is a command pattern.
	 */
	public static boolean isCommand(String pattern) {
		if (pattern.toLowerCase().equals(Command.Delete.pattern) ||
			pattern.toLowerCase().equals(Command.Settings.pattern) ||
		    pattern.toLowerCase().equals(Command.Help.pattern) ||
		    pattern.toLowerCase().equals(Command.Example.pattern) ||
		    pattern.toLowerCase().equals(Command.Path.pattern) ||
		    pattern.toLowerCase().equals(Command.Save.pattern) ||
		    pattern.toLowerCase().equals(Command.Note.pattern)) {
			return true;
		} else {
			return false;
		}
	}
}
