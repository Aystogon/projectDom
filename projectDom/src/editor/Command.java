package editor;

public enum Command {
	
	Help("help", "-help"," To use a command, type \"-<example>\"."),
	Example("example", "-example",  " -save , -delete , -new , -settings , -etc..."),
	Save("Save", "-save", "Saves the current document to the home."),
	Delete("Delete", "-delete", "Deletes the current document."),
	Stats("Status", "-stats", "Shows the current stats of the document."),
	Path("Path", "-path", "Shows the current path of the output"),
	New("New", "-new", "Creates a new document, deleting the current one."),
	Init("Init", "-init", "Initializes a new headered document"),
	Settings("Settings", "-setting", "Opens up current settings of the notepad.");
	
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
		    pattern.toLowerCase().equals(Command.Save.pattern) ||
		    pattern.toLowerCase().equals(Command.Stats.pattern) ||
		    pattern.toLowerCase().equals(Command.New.pattern) ||
		    pattern.toLowerCase().equals(Command.Init.pattern)) {
			return true;
		} else {
			return false;
		}
	}
}
